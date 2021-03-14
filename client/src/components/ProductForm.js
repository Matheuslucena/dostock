import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import PropTypes from "prop-types";
import {isEmpty} from "lodash";
import {Button, Grid, TextField,} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import FolderFormModal from "./FolderFormModal";
import folderApi from "../api/folderApi";

const useStyles = makeStyles((theme) => ({
  rowMargin: {
    marginTop: theme.spacing(1),
  },
  actions: {
    marginTop: theme.spacing(6),
  },
  actionBtn: {
    marginLeft: theme.spacing(2),
  },
}));

export default function ProductForm(props) {
  const classes = useStyles();
  const history = useHistory();
  const [folders, setFolders] = useState([]);
  const [product, setProduct] = useState({
    name: "",
    code: "",
    minimumLevel: 0,
    batchRequired: false,
    observation: "",
  });
  const [openNewFolder, setOpenNewFolder] = useState(false);

  const getFolders = async () => {
    const resp = await folderApi.list();
    setFolders(resp.data);
  };

  useEffect(async () => {
    await getFolders();
  }, []);

  useEffect(() => {
    if (isEmpty(props.product)) {
      return;
    }
    setProduct({
      ...props.product,
      minimumLevel:
        props.product.minimumLevel == null ? 0 : props.product.minimumLevel,
    });
  }, [props.product]);

  const handleCancel = () => {
    history.push("/");
  };

  const handleCloseNewFolder = () => {
    setOpenNewFolder(false);
  };

  const save = () => {
    props.onSave(product);
  };

  const onSaveNewFolder = async (folder) => {
    await getFolders();
    setFolder(folder.id);
  };

  return (
    <React.Fragment>
      <Grid container spacing={2}>
        <Grid item lg={4}>
          <TextField
            label="Name"
            variant="outlined"
            size="small"
            fullWidth
            value={product.name}
            onChange={(e) => setProduct({ ...product, name: e.target.value })}
          />
        </Grid>
        <Grid item lg={2}>
          <TextField
            label="Code"
            variant="outlined"
            size="small"
            fullWidth
            value={product.code}
            onChange={(e) => setProduct({ ...product, code: e.target.value })}
          />
        </Grid>
      </Grid>
      <Grid container spacing={2} className={classes.rowMargin}>
        <Grid item lg={2}>
          <TextField
            label="Minimun inventory"
            variant="outlined"
            size="small"
            fullWidth
            value={product.minimumLevel}
            onChange={(e) =>
              setProduct({ ...product, minimumLevel: e.target.value })
            }
          />
        </Grid>
        {/* <Grid item lg={2}>
          <FormControlLabel
            control={<Checkbox name="checkedB" color="primary" />}
            label="Batch fill required"
            checked={product.batchRequired}
            onChange={(e) =>
              setProduct({ ...product, batchRequired: e.target.checked })
            }
          />
        </Grid> */}
      </Grid>
      {/* <Grid container spacing={2} alignItems="center">
        <Grid item lg={3}>
          <FormControl variant="outlined" fullWidth size="small">
            <InputLabel id="demo-simple-select-outlined-label">
              Folder
            </InputLabel>
            <Select
              labelId="demo-simple-select-outlined-label"
              id="demo-simple-select-outlined"
              label="Folder"
              value={folder}
              onChange={(e) => {
                setFolder(e.target.value);
              }}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              {folderSelectOpts()}
            </Select>
          </FormControl>
        </Grid>
        <Grid item lg={2}>
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
      </Grid> */}
      <Grid container spacing={2} alignItems="center">
        <Grid item lg={7}>
          <TextField
            label="Observation"
            variant="outlined"
            size="small"
            fullWidth
            value={product.observation}
            onChange={(e) =>
              setProduct({ ...product, observation: e.target.value })
            }
          />
        </Grid>
      </Grid>
      <Grid container className={classes.actions}>
        <Button size="small" color="primary" onClick={handleCancel}>
          Cancel
        </Button>
        {/* {!product.id && (
          <Button
            variant="outlined"
            className={classes.actionBtn}
            onClick={save}
          >
            Save & Add New
          </Button>
        )} */}
        <Button variant="outlined" className={classes.actionBtn} onClick={save}>
          Save
        </Button>
      </Grid>
      <FolderFormModal
        open={openNewFolder}
        onClose={handleCloseNewFolder}
        onSaveFolder={(folder) => {
          onSaveNewFolder(folder);
        }}
      />
    </React.Fragment>
  );
}

ProductForm.propTypes = {
  onSave: PropTypes.func,
  product: PropTypes.object,
};
