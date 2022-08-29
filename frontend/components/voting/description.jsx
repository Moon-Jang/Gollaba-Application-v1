import * as React from "react";
import Box from "@mui/material/Box";
import { Checkbox, TextField } from "@mui/material";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

const label = { inputProps: { "aria-label": "Checkbox demo" } };
export default function Description(props) {
  const data = props.data;
  console.log(data);
  return (
    <Box
      className="outerContainer"
      sx={{
        maxWidth: "100%",
        height: "180px",
        // minHeight: 180,
        mt: 1.5,
        mb: 2,
        borderRadius: "5px",
        padding: 0.5,
        boxShadow: 2,
        letterSpacing: 1.2,
        display: "flex",
        //flex: 1,
        borderColor: "grey.500",
        flexDirection: "column",
      }}
    >
      <Box
        className="dateAndProfile"
        sx={{
          maxWidth: "100%",
          flex: 1,
          borderRadius: "5px",
          flexDirection: "row",
          display: "flex",
          flexDirection: "row",
        }}
      >
        <Box
          className="date"
          sx={{
            display: "flex",
            flex: 2,
            fontSize: 12,
            height: "100%",
            justifyContent: "left",
            pl: 1,
            alignItems: "center",
          }}
        >
          ~{("" + data.endedAt).substring(0, 10)}
        </Box>
        <Box
          className="profile"
          sx={{
            display: "flex",
            flex: 1,
            height: "100%",
            fontSize: 12,
            justifyContent: "right",
            pr: 1,
            alignItems: "center",
          }}
        >
          <AccountCircleIcon fontSize="12" sx={{ mr: 0.2 }} />
          {data.creatorName}
        </Box>
      </Box>
      <Box
        className="title"
        sx={{
          maxWidth: "100%",
          flex: 5,
          borderRadius: "5px",
          flexDirection: "row",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          fontSize: 30,
        }}
      >
        {data.title}
      </Box>
      <Box
        className="details"
        sx={{
          maxWidth: "100%",
          flex: 1,
          pr: 1,
          justifyContent: "right",
          flexDirection: "row",
          display: "flex",
          color: "#808080",
        }}
      >
        {data.isBallot ? "기명투표" : "익명투표"}{" "}
        {data.responseType === "SINGLE" ? "단일투표" : "복수투표"}
      </Box>
    </Box>
  );
}
