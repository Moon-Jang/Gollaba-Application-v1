import * as React from "react";
import Box from "@mui/material/Box";
import { Checkbox, TextField } from "@mui/material";

const label = { inputProps: { "aria-label": "Checkbox demo" } };
export default function Option(props) {
  const optionChange = (e) => {
    let obj = props.entireItems;
    obj[props.pollingItems.sequence].description = e.target.value;
    props.setPollingItems([...obj]);
  };

  return (
    <Box
      className="outerContainer"
      sx={{
        maxWidth: "100%",
        height: 60,
        mt: 2,
        mb: 2,
        borderRadius: "10px",
        padding: 1,
        backgroundColor: "rgb(230,230,230)",
        flexDirection: "row",
        display: "flex",
      }}
    >
      <Box
        className="numbering"
        sx={{
          display: "flex",
          flex: 1,
          justifyContent: "center",
          alignItems: "center",
          fontSize: 30,
          letterSpacing: -5,
        }}
      >
        {props.pollingItems.sequence + 1}
      </Box>
      <Box
        className="optionName"
        sx={{
          display: "flex",
          flex: 10,
          justifyContent: "center",
          alignItems: "center",
          fontSize: 40,
          letterSpacing: -5,
        }}
      >
        <TextField label="선택지" variant="standard" onChange={optionChange} />
      </Box>
    </Box>
  );
}
