import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";
import {makeStyles} from "@material-ui/core/styles";
import {green} from "@material-ui/core/colors";
import {
    Button,
    CircularProgress,
    DialogActions,
    DialogContent,
    FormControl,
    Grid,
    IconButton,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Tooltip,
} from "@material-ui/core";
import {CreateNewFolder as CreateNewFolderIcon} from "@material-ui/icons";
import FolderFormModal from "./FolderFormModal";
import folderApi from "../api/folderApi";

const useStyles = makeStyles((theme) => ({
  wrapper: {
    position: "relative",
  },
  inputMargin: {
    marginTop: theme.spacing(1),
  },
  buttonProgress: {
    color: green[500],
    position: "absolute",
    top: "50%",
    left: "50%",
    marginTop: -12,
    marginLeft: -12,
  },
}));

export default function InventoryForm(props) {
  const classes = useStyles();
  const { handleClose, onSave } = props;
  const [folders, setFolders] = useState([]);
  const [openNewFolder, setOpenNewFolder] = useState(false);
  const [loading, setLoading] = useState(false);
  const [inventory, setInventory] = useState({
    quantity: 1,
    folder: { id: "" },
    observation: "",
  });

  const getFolders = async () => {
    const resp = await folderApi.list();
    setFolders(resp.data);
  };

  useEffect(async () => {
    await getFolders();
  }, []);

  const handleCloseNewFolder = () => {
    setOpenNewFolder(false);
  };

  const onSaveNewFolder = async (folder) => {
    await getFolders();
    setInventory({ ...inventory, folder: folder.id });
  };

  const handleSave = async () => {
    try {
      setLoading(true);
      onSave(inventory);
    } catch (e) {
      setLoading(false);
    }
  };

  const folderSelectOpts = () => {
    return folders.map((folder) => {
      return (
        <MenuItem key={folder.id} value={folder.id}>
          {folder.name}
        </MenuItem>
      );
    });
  };

  return (
    <React.Fragment>
      <DialogContent>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={4}>
            <TextField
              autoFocus
              margin="dense"
              id="quantity"
              label="Quantity"
              type="number"
              fullWidth
              variant="outlined"
              size="small"
              value={inventory.quantity}
              onChange={(e) =>
                setInventory({ ...inventory, quantity: e.target.value })
              }
            />
          </Grid>
        </Grid>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={10}>
            <FormControl variant="outlined" fullWidth size="small">
              <InputLabel id="demo-simple-select-outlined-label">
                Folder
              </InputLabel>
              <Select
                labelId="demo-simple-select-outlined-label"
                id="demo-simple-select-outlined"
                label="Folder"
                value={inventory.folder.id}
                onChange={(e) => {
                  setInventory({
                    ...inventory,
                    folder: { id: e.target.value },
                  });
                }}
              >
                <MenuItem value="">
                  <em>None</em>
                </MenuItem>
                {folderSelectOpts()}
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={2}>
            <Tooltip title="New Folder">
              <IconButton
                color="primary"
                aria-label="new folder"
                onClick={() => setOpenNewFolder(true)}
              >
                <CreateNewFolderIcon />
              </IconButton>
            </Tooltip>
          </Grid>
        </Grid>
        <TextField
          autoFocus
          margin="dense"
          id="observation"
          label="Observation"
          type="text"
          fullWidth
          variant="outlined"
          size="small"
          value={inventory.observation}
          onChange={(e) =>
            setInventory({ ...inventory, observation: e.target.value })
          }
        />
        <FolderFormModal
          open={openNewFolder}
          onClose={handleCloseNewFolder}
          onSaveFolder={(folder) => {
            onSaveNewFolder(folder);
          }}
        />
      </DialogContent>
      <DialogActions>
        <div className={classes.wrapper}>
          <Button onClick={handleClose} color="primary" disabled={loading}>
            Cancel
          </Button>
          <Button onClick={handleSave} color="primary" disabled={loading}>
            Save
          </Button>
          {loading && (
            <CircularProgress size={24} className={classes.buttonProgress} />
          )}
        </div>
      </DialogActions>
    </React.Fragment>
  );
}

InventoryForm.propTypes = {
  handleClose: PropTypes.func,
  product: PropTypes.object,
  onSave: PropTypes.func,
};
