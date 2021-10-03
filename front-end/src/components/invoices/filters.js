import InputLabel from "@mui/material/InputLabel";
import Typography from "@mui/material/Typography";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import Grid from "@mui/material/Grid";

const STATUSES = [
  { label: "ALL" },
  { label: "PENDING", value: "PENDING" },
  { label: "APPROVED", value: "APPROVED" }
];

const Filters = ({ onStatusChange }) => {
  return (
    <Grid container spacing={2} sx={{ pb: "40px" }} columns={12}>
      <Grid item md={8} lg={9} sm={12} xs={12} sx={{ alignSelf: "center" }}>
        <Typography variant={"h5"}>
          YOU CAN SORT AND FILTER INVOICES BY STATUS
        </Typography>
      </Grid>
      <Grid item md={4} lg={3} sm={12} xs={12}>
        <InputLabel>Filter By Invoice Status</InputLabel>
        <Select
          sx={{ width: "100%" }}
          defaultValue={0}
          onChange={event =>
            onStatusChange(STATUSES[event.target.value || 0].value)
          }
        >
          {" "}
          {STATUSES.map((status, index) => (
            <MenuItem key={status.label} value={index}>
              {status.label}
            </MenuItem>
          ))}
        </Select>
      </Grid>
    </Grid>
  );
};
export default Filters;
