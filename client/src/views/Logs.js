import React, {useState} from "react";
import "date-fns";
import DateFnsUtils from "@date-io/date-fns";
import {makeStyles} from "@material-ui/core/styles";
import {DataGrid} from "@material-ui/data-grid";
import {KeyboardDatePicker, MuiPickersUtilsProvider,} from "@material-ui/pickers";
import {
  Button,
  Card,
  CardContent,
  Container,
  FormControl,
  FormControlLabel,
  FormLabel,
  Grid,
  Radio,
  RadioGroup,
  Typography,
} from "@material-ui/core";
import logApi from "../api/logApi";

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
  center: {
    textAlign: "center",
  },
  marginLeft: {
    marginLeft: theme.spacing(3),
  },
  to: {
    margin: "0 1rem",
  },
}));

const columns = [
  { field: "product", headerName: "Product", width: 270 },
  { field: "folder", headerName: "Folder", width: 230 },
  { field: "quantity", headerName: "Quantity", width: 130 },
  { field: "observation", headerName: "Observation", width: 230 },
  { field: "logType", headerName: "Log Type", width: 150 }
];

export default function Logs() {
  const classes = useStyles();
  const [search, setSearch] = useState({
    dateInitial: new Date(),
    dateFinal: new Date(),
    logType: "INCREASE",
  });
  const [logs, setLogs] = useState([]);

  const handleSearch = async () => {
    const resp = await logApi.search(search);
    const logsList = resp.data.map((log) => {
      return {
        id: log.id,
        product: log.product.name,
        folder: log.folder ? log.folder.name : "-",
        quantity: log.quantity,
        observation: log.observation,
        logType: log.logType,
      };
    });
    setLogs(logsList);
  };

  return (
    <main className={classes.content}>
      <div className={classes.appBarSpacer} />
      <Container maxWidth="lg" className={classes.container}>
        <Card>
          <Typography variant="h5" className={classes.title}>
            Logs
          </Typography>
          <CardContent>
            <Grid container alignItems="center">
              <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <Grid item lg={2}>
                  <KeyboardDatePicker
                    variant="inline"
                    size="small"
                    inputVariant="outlined"
                    disableToolbar
                    format="MM/dd/yyyy"
                    margin="normal"
                    id="date-picker-inline"
                    label="Start Date"
                    value={search.dateInitial}
                    onChange={(date) => {
                      setSearch({ ...search, dateInitial: date });
                    }}
                    KeyboardButtonProps={{
                      "aria-label": "change date",
                    }}
                  />
                </Grid>
                <div className={classes.to}>
                  <Typography className={classes.center}>to</Typography>
                </div>
                <Grid item lg={2}>
                  <KeyboardDatePicker
                    disableToolbar
                    size="small"
                    inputVariant="outlined"
                    variant="inline"
                    format="MM/dd/yyyy"
                    margin="normal"
                    id="date-picker-inline"
                    label="End Date"
                    value={search.dateFinal}
                    onChange={(date) => {
                      setSearch({ ...search, dateFinal: date });
                    }}
                    KeyboardButtonProps={{
                      "aria-label": "change date",
                    }}
                  />
                </Grid>
              </MuiPickersUtilsProvider>
              <Grid item>
                <FormControl
                  component="fieldset"
                  className={classes.marginLeft}
                >
                  <FormLabel component="legend">Log Type</FormLabel>
                  <RadioGroup
                    row
                    aria-label="logType"
                    name="logType"
                    value={search.logType}
                    onChange={(e) =>
                      setSearch({ ...search, logType: e.target.value })
                    }
                  >
                    <FormControlLabel
                      value="INCREASE"
                      control={<Radio size="small" />}
                      label="Increase"
                    />
                    <FormControlLabel
                      value="DECREASE"
                      control={<Radio size="small" />}
                      label="Decrease"
                    />
                  </RadioGroup>
                </FormControl>
              </Grid>
              <Grid item>
                <Button
                  variant="contained"
                  color="primary"
                  className={classes.marginLeft}
                  onClick={handleSearch}
                >
                  Search
                </Button>
              </Grid>
            </Grid>
            <div style={{ height: 400, width: "100%" }}>
              <DataGrid density="standard" sortingOrder="asc" rows={logs} columns={columns} pageSize={15} />
            </div>
          </CardContent>
        </Card>
      </Container>
    </main>
  );
}
