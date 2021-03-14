import React, {useEffect, useState} from "react";
import {makeStyles} from "@material-ui/core/styles";
import {TreeItem, TreeView} from "@material-ui/lab";
import {Container, Drawer, Toolbar} from "@material-ui/core";
import {ChevronRight as ChevronRightIcon, ExpandMore as ExpandMoreIcon,} from "@material-ui/icons";
import productApi from "../api/productApi";
import ProductList from "../components/ProductList.js";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerContainer: {
    overflow: "auto",
  },
  content: {
    flexGrow: 1,
    height: "100vh",
    overflow: "auto",
  },
  appBarSpacer: theme.mixins.toolbar,
}));

export default function Product() {
  const classes = useStyles();
  const [folders, setFolders] = useState([]);
  const [products, setProducts] = useState([]);
  const [folderSelected, setFolderSelected] = useState("allProducts");

  async function getFolders() {
    const resp = await productApi.foldersList();
    setFolders(resp.data);
  }

  async function getProducts() {
    const resp = await productApi.list();
    setProducts(resp.data);
  }

  useEffect(async () => {
    await getFolders();
    await getProducts();
  }, []);

  const handleSelectFolder = async (value) => {
    setFolderSelected(value);
    if (value === "all-products") {
      await getProducts();
    } else {
      const resp = await productApi.search({ folderId: value });
      setProducts(resp.data);
    }
  };

  const renderFolderTree = (parentFolder) => {
    return parentFolder.map((current) => {
      const { folder, subFolders } = current;
      return (
        <TreeItem key={folder.id} nodeId={folder.id + ""} label={folder.name}>
          {Array.isArray(subFolders) ? renderFolderTree(subFolders) : null}
        </TreeItem>
      );
    });
  };

  return (
    <React.Fragment>
      <Drawer
        variant="permanent"
        className={classes.drawer}
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <Toolbar />
        <div className={classes.drawerContainer}>
          <TreeView
            selected={folderSelected}
            className={classes.root}
            defaultCollapseIcon={<ExpandMoreIcon />}
            defaultExpandIcon={<ChevronRightIcon />}
            onNodeSelect={(e, value) => handleSelectFolder(value)}
          >
            <TreeItem nodeId="all-products" label="All Products"/>
            {renderFolderTree(folders)}
          </TreeView>
        </div>
      </Drawer>
      <main className={classes.content}>
        <div className={classes.appBarSpacer} />
        <Container maxWidth="lg" className={classes.container}>
          <ProductList products={products} />
        </Container>
      </main>
    </React.Fragment>
  );
}
