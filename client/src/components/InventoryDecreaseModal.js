import React, {useState} from "react";
import PropTypes from "prop-types";
import {Dialog, DialogTitle} from "@material-ui/core";
import InventoryForm from "./InventoryForm";
import productApi from "../api/productApi";

export default function InventoryDecreaseModal(props) {
  const { open, onClose, product } = props;
  const [loading, setLoading] = useState(false);

  const handleClose = () => {
    onClose();
  };

  const onSave = async (inventory) => {
    setLoading(true);
    await productApi.decrease(product.id, inventory);
    setLoading(false);
    handleClose();
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
        <DialogTitle id="form-dialog-title">Invetory Out</DialogTitle>
        <InventoryForm
          handleClose={handleClose}
          onSave={(inventory) => onSave(inventory)}
          product={product}
        />
      </Dialog>
    </div>
  );
}

InventoryDecreaseModal.propTypes = {
  open: PropTypes.bool,
  onClose: PropTypes.func,
  product: PropTypes.object,
};
