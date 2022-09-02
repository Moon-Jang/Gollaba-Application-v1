import * as React from "react";
import Box from "@mui/material/Box";
import { Checkbox, TextField } from "@mui/material";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

const label = { inputProps: { "aria-label": "Checkbox demo" } };
export default function InfoBox(props) {
  const data = props.data;
  return (
    <Box
      className="outerContainer"
      sx={{
        maxWidth: "100%",
        height: "30px",
        // minHeight: 180,
        mt: -2,
        mb: 2,
        borderRadius: "5px",
        padding: 0.5,

        letterSpacing: 1.2,
        display: "flex",
        flexDirection: "column",
        textAlign: "right",
      }}
    >
      투표 참여인원 : {data}명
    </Box>
  );
}
