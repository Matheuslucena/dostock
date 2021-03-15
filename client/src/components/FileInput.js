import React, { useState, useRef, useEffect } from "react";
import PropTypes from "prop-types";
import {
  Button,
  ButtonGroup,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Grid,
  IconButton,
  Tooltip,
  Typography,
} from "@material-ui/core";
import { Delete as DeleteIcon } from "@material-ui/icons";
import { makeStyles } from "@material-ui/core/styles";
import { useDropArea } from "react-use";

const useStyles = makeStyles((theme) => ({
  wrapper: {
    textAlign: "center",
    borderRadius: theme.shape.borderRadius,
    borderColor: theme.palette.grey["300"],
    border: "2px dashed",
    padding: theme.spacing(2),
    height: "135px",
    display: "flex",
    flexDirection: "column",
    justifyContent: "space-around",
    backgroundColor: theme.palette.grey["100"],
  },
  fileInput: {
    display: "none",
  },
  previewWrapper: {
    textAlign: "center",
    padding: theme.spacing(2),
    height: "165px",
    display: "flex",
    flexDirection: "column",
    justifyContent: "space-around",
    backgroundColor: theme.palette.grey["100"],
    borderRadius: theme.shape.borderRadius,
  },
  preview: {
    height: "135px",
  },
  remove: {
    marginTop: theme.spacing(1),
  },
  center: {
    display: "flex",
    justifyContent: "center",
  },
}));

export default function FileInput(props) {
  const { onChange } = props;
  const classes = useStyles();
  const inputEl = useRef(null);
  const [file, setFile] = useState(null);
  const [bond, state] = useDropArea({
    onFiles: (files) => setFile(files[0]),
  });

  const hadleChooseImageClick = () => {
    inputEl.current.click();
  };

  const handleChangeFileInput = (e) => {
    setFile(e.target.files[0]);
  };

  useEffect(() => {
    onChange(file);
  }, [file]);

  const showInputOrImage = () => {
    if (file === null) {
      return (
        <div className={classes.wrapper} {...bond}>
          <Typography>Drag a Image here</Typography>
          <Typography variant="subtitle2">or</Typography>
          <Button
            size="small"
            variant="outlined"
            color="primary"
            onClick={hadleChooseImageClick}
          >
            Choose a image to upload
          </Button>
          <input
            ref={inputEl}
            type="file"
            className={classes.fileInput}
            accept="image/*"
            onChange={handleChangeFileInput}
          ></input>
        </div>
      );
    } else {
      const src = URL.createObjectURL(file);
      return (
        <div>
          <div className={classes.previewWrapper}>
            <img src={src} className={classes.preview} />
          </div>
          <div className={classes.center}>
            <Button
              className={classes.remove}
              size="small"
              variant="outlined"
              color="secondary"
              onClick={hadleChooseImageClick}
              startIcon={<DeleteIcon />}
            >
              Delete Image
            </Button>
          </div>
        </div>
      );
    }
  };

  return <div>{showInputOrImage()}</div>;
}

FileInput.propTypes = {
  onChange: PropTypes.func,
};
