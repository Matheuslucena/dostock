import React, {useEffect, useState} from "react";
import {useHistory, useParams} from "react-router-dom";
import {Card, CardContent, Container, Snackbar, Typography,} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
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

export default function ProductEdit() {
  const history = useHistory();
  const classes = useStyles();
  const { id } = useParams();
  const [product, setProduct] = useState({
    name: "",
    code: "",
    minimumLevel: 0,
    batchRequired: false,
    observation: "",
  });
  const [alert, setAlert] = useState({
    open: false,
    msg: "",
  });

  useEffect(async () => {
    const resp = await productApi.get(id);
    setProduct(resp.data);
  }, []);

  const updateProduct = async (productSave) => {
    await productApi.update(productSave);
    setAlert({ open: true, msg: "Product Saved!" });
    history.push("/");
  };

  return (
    <main className={classes.content}>
      <div className={classes.appBarSpacer} />
      <Container maxWidth="lg" className={classes.container}>
        <Card>
          <Typography variant="h5" className={classes.title}>
            Edit Product
          </Typography>
          <CardContent>
            <ProductForm onSave={updateProduct} product={product} />
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
