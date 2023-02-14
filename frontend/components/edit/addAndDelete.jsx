import React, { useState } from "react";
import Box from "@mui/material/Box";
import { Checkbox, TextField, IconButton } from "@mui/material";
import AddBoxOutlinedIcon from "@mui/icons-material/AddBoxOutlined";
import DisabledByDefaultOutlinedIcon from "@mui/icons-material/DisabledByDefaultOutlined";

const label = { inputProps: { "aria-label": "Checkbox demo" } };
export default function AddAndDelete({
  setPolls,
  pollingItems,
  setPollingItems,
}) {
  const addBtnClicked = (event) => {
    event.preventDefault();
    const pollNumber = pollingItems.length;
    pollingItems.push({ sequence: pollNumber, description: "" });
    setPollingItems([...pollingItems]);
  };

  const deleteBtnClicked = (event) => {
    event.preventDefault();
    if (pollingItems.length === 2) {
      alert("선택지는 최소 2개가 있어야 합니다!");
      return;
    }
    pollingItems.pop();
    setPollingItems([...pollingItems]);
  };

  return (
    <Box
      className="outerContainer"
      sx={{
        maxWidth: "100%",
        height: 80,
        mt: 2,
        mb: 2,
        borderRadius: "15px",
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
          justifyContent: "left",
          alignItems: "center",
          fontSize: 40,
          letterSpacing: -5,
        }}
      >
        <IconButton onClick={deleteBtnClicked}>
          <DisabledByDefaultOutlinedIcon sx={{ fontSize: 70 }} />
        </IconButton>
      </Box>
      <Box
        className="optionName"
        sx={{
          display: "flex",
          flex: 1,
          justifyContent: "right",
          alignItems: "center",
          fontSize: 40,
          letterSpacing: -5,
        }}
      >
        <IconButton onClick={addBtnClicked}>
          <AddBoxOutlinedIcon sx={{ fontSize: 70 }} />
        </IconButton>
      </Box>
    </Box>
  );
}
