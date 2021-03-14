import React, {useState} from "react";
import PropTypes from "prop-types";
import {makeStyles} from "@material-ui/core/styles";
import {useHistory} from "react-router-dom";
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
import {
    AddCircleOutline as AddCircleOutlineIcon,
    ArrowDownward as ArrowDownwardIcon,
    ArrowUpward as ArrowUpwardIcon,
    Edit as EditIcon,
} from "@material-ui/icons";
import InventoryIncreaseModal from "./InventoryIncreaseModal";
import InventoryDecreaseModal from "./InventoryDecreaseModal";

const useStyles = makeStyles((theme) => ({
  pageActions: {
    marginBottom: theme.spacing(4),
    display: "flex",
    alignItems: "center",
  },
  cardMedia: {
    height: "135px",
  },
  cardContent: {
    padding: 0,
  },
  productName: {
    padding: "0 .3rem .3rem",
  },
  productCode: {
    padding: "0 .3rem",
  },
  iconButtonMargin: {
    padding: theme.spacing(1),
  },
  editButton: {
    padding: theme.spacing(1),
    marginLeft: "auto!important",
  },
  cardActions: {
    paddingTop: 0,
  },
  newProduct: {
    marginLeft: "auto",
  },
}));

export default function ProductList(props) {
  const classes = useStyles();
  const { products } = props;
  const history = useHistory();
  const [openInventoryIncrease, setOpenInventoryIncrease] = useState(false);
  const [openInventoryDecrease, setOpenInventoryDecrease] = useState(false);
  const [productSelected, setProductSelected] = useState({});
  const [filter, setFilter] = useState("all");

  const handleNewProduct = () => {
    history.push("/product/new");
  };

  const handleEditProduct = (id) => {
    history.push(`/product/${id}/edit`);
  };

  const handleInventoryIncreaseClick = (product) => {
    setOpenInventoryIncrease(true);
    setProductSelected(product);
  };

  const handleInventoryDecreaseClick = (product) => {
    setOpenInventoryDecrease(true);
    setProductSelected(product);
  };

  const cardList = () => {
    return (
      <Grid container spacing={1}>
        {products
          .filter((p) => {
            if (filter === "all") return true;
            if (filter == "low_inventory") return p.minimumLevel > p.quantity;
          })
          .map((product) => {
            return (
              <Grid key={product.id} item lg={2}>
                <Card variant="outlined">
                  <CardMedia
                    className={classes.cardMedia}
                    image="/placeholder-image.jpg"
                    title={product.name}
                  />
                  <CardContent className={classes.cardContent}>
                    <Typography
                      variant="caption"
                      className={classes.productCode}
                    >
                      #{product.code}
                    </Typography>
                    <Typography
                      variant="subtitle2"
                      className={classes.productName}
                    >
                      {product.name}
                    </Typography>
                  </CardContent>
                  <CardActions className={classes.cardActions}>
                    <Tooltip title="Add Product">
                      <IconButton
                        color="primary"
                        aria-label="add product"
                        className={classes.iconButtonMargin}
                        onClick={() => handleInventoryIncreaseClick(product)}
                      >
                        <ArrowUpwardIcon />
                      </IconButton>
                    </Tooltip>
                    <Tooltip title="Remove Product">
                      <IconButton
                        color="secondary"
                        aria-label="remove product"
                        className={classes.iconButtonMargin}
                        onClick={() => handleInventoryDecreaseClick(product)}
                      >
                        <ArrowDownwardIcon />
                      </IconButton>
                    </Tooltip>
                    <Tooltip title="Edit Product">
                      <IconButton
                        color="default"
                        aria-label="edit product"
                        className={classes.editButton}
                        onClick={() => handleEditProduct(product.id)}
                      >
                        <EditIcon />
                      </IconButton>
                    </Tooltip>
                  </CardActions>
                </Card>
              </Grid>
            );
          })}
      </Grid>
    );
  };

  return (
    <React.Fragment>
      <div className={classes.pageActions}>
        <ButtonGroup variant="contained" color="primary">
          <Button onClick={() => setFilter("all")}>All</Button>
          <Button onClick={() => setFilter("low_inventory")}>
            Low Invetory
          </Button>
        </ButtonGroup>
        <Button
          variant="contained"
          color="primary"
          className={classes.newProduct}
          startIcon={<AddCircleOutlineIcon />}
          onClick={handleNewProduct}
        >
          New Product
        </Button>
      </div>
      {cardList()}
      <InventoryIncreaseModal
        open={openInventoryIncrease}
        onClose={() => setOpenInventoryIncrease(false)}
        product={productSelected}
      />
      <InventoryDecreaseModal
        open={openInventoryDecrease}
        onClose={() => setOpenInventoryDecrease(false)}
        product={productSelected}
      />
    </React.Fragment>
  );
}

ProductList.propTypes = {
  products: PropTypes.array,
};
