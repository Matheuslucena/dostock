import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import {
  Card,
  CardContent,
  Container,
  Snackbar,
  Typography,
} from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
import productApi from "../api/productApi";
import ProductForm from "../components/ProductForm";
import MuiAlert from "@material-ui/lab/Alert";

const useStyles = makeStyles((theme) => ({
  title: {
    padding: theme.spacing(2),
  },
  content: {
    flexGrow: 1,
    height: "100vh",
    overflow: "auto",
  },
  appBarSpacer: theme.mixins.toolbar,
}));

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

export default function ProductNew() {
  const classes = useStyles();
  const history = useHistory();
  const [alert, setAlert] = useState({
    open: false,
    msg: "",
  });

  const saveProduct = async (product) => {
    await productApi.save(product);
    setAlert({ open: true, msg: "Product Saved!" });
    history.push("/");
  };

  return (
    <main className={classes.content}>
      <div className={classes.appBarSpacer} />
      <Container maxWidth="lg" className={classes.container}>
        <Card>
          <Typography variant="h5" className={classes.title}>
            New Product
          </Typography>
          <CardContent>
            <ProductForm onSave={saveProduct} />
          </CardContent>
        </Card>
      </Container>
      <Snackbar
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
        open={alert.open}
        onClose={() => setAlert({ ...alert, open: false })}
      >
        <Alert
          onClose={() => setAlert({ ...alert, open: false })}
          severity="success"
        >
          {alert.msg}
        </Alert>
      </Snackbar>
    </main>
  );
}
