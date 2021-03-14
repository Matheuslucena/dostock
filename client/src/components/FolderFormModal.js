import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import {
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@material-ui/core";
import { green } from "@material-ui/core/colors";
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

export default function FolderFormModal(props) {
  const classes = useStyles();
  const { open, onClose, onSaveFolder } = props;
  const [folders, setFolders] = useState([]);
  const [parentFolder, setParentFolder] = useState("");
  const [name, setName] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(async () => {
    if (!open) {
      return;
    }
    const resp = await folderApi.list();
    setFolders(resp.data);
  }, [open]);

  const handleClose = () => {
    setParentFolder("");
    setName("");
    onClose();
  };

  const handleSave = async () => {
    try {
      setLoading(true);
      let parentFolderSave = null;
      if (parentFolder) {
        parentFolderSave = folders.find((f) => f.id === parentFolder);
      }
      const resp = await folderApi.save({
        name,
        parentFolder: parentFolderSave,
      });
      onSaveFolder(resp.data);
      handleClose();
      setLoading(false);
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
    <div>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="form-dialog-title"
        fullWidth
        maxWidth="xs"
        disableBackdropClick={loading}
        disableEscapeKeyDown={loading}
      >
        <DialogTitle id="form-dialog-title">New Folder</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Enter the name of the new folder.
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Folder Name"
            type="text"
            fullWidth
            variant="outlined"
            size="small"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <FormControl
            variant="outlined"
            fullWidth
            size="small"
            className={classes.inputMargin}
          >
            <InputLabel id="demo-simple-select-outlined-label">
              Parent Folder
            </InputLabel>
            <Select
              labelId="demo-simple-select-outlined-label"
              id="demo-simple-select-outlined"
              label="Parent Folder"
              value={parentFolder}
              onChange={(e) => setParentFolder(e.target.value)}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              {folderSelectOpts()}
            </Select>
          </FormControl>
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
      </Dialog>
    </div>
  );
}

FolderFormModal.propTypes = {
  open: PropTypes.bool,
  onClose: PropTypes.func,
  onSaveFolder: PropTypes.func,
};
