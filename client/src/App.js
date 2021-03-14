import "./App.css";
import {fade, makeStyles} from "@material-ui/core/styles";
import {AppBar, Button, InputBase, Toolbar, Typography,} from "@material-ui/core";
import SearchIcon from "@material-ui/icons/Search";
import CssBaseline from "@material-ui/core/CssBaseline";
import {BrowserRouter as Router, Link, Route, Switch} from "react-router-dom";
import Product from "./views/Product";
import ProductNew from "./views/ProductNew";
import ProductEdit from "./views/ProductEdit";
import Logs from "./views/Logs";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
  },
  toolbar: {
    minHeight: "50px",
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
  },
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
  search: {
    position: "relative",
    borderRadius: theme.shape.borderRadius,
    backgroundColor: fade(theme.palette.common.white, 0.15),
    "&:hover": {
      backgroundColor: fade(theme.palette.common.white, 0.25),
    },
    marginLeft: 0,
    width: "100%",
    [theme.breakpoints.up("sm")]: {
      marginLeft: theme.spacing(2),
      width: "auto",
    },
  },
  searchIcon: {
    padding: theme.spacing(0, 2),
    height: "100%",
    position: "absolute",
    pointerEvents: "none",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  inputRoot: {
    color: "inherit",
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
    transition: theme.transitions.create("width"),
    width: "100%",
    [theme.breakpoints.up("sm")]: {
      width: "12ch",
      "&:focus": {
        width: "20ch",
      },
    },
  },
  links: {
    [theme.breakpoints.up("sm")]: {
      marginLeft: theme.spacing(18),
      width: "auto",
    },
  },
}));

function App() {
  const classes = useStyles();

  return (
    <Router>
      <div className={classes.root}>
        <CssBaseline />
        <AppBar position="fixed" className={classes.appBar}>
          <Toolbar className={classes.toolbar}>
            <Typography variant="h6">DoStock</Typography>
            <div className={classes.links}>
              <Button color="inherit" component={Link} to={"/"}>
                Products
              </Button>
              <Button color="inherit" component={Link} to={"/logs"}>
                Logs
              </Button>
            </div>
            <div className={classes.search}>
              <div className={classes.searchIcon}>
                <SearchIcon />
              </div>
              <InputBase
                placeholder="Search product..."
                classes={{
                  root: classes.inputRoot,
                  input: classes.inputInput,
                }}
                inputProps={{ "aria-label": "search" }}
              />
            </div>
          </Toolbar>
        </AppBar>
        <Switch>
          <Route path="/" exact component={Product} />
          <Route path="/configuration" component={Product} />
          <Route path="/product/new" component={ProductNew} />
          <Route path="/product/:id/edit" exact component={ProductEdit} />
          <Route path="/logs" exact component={Logs} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
