import React, { useState, useEffect } from "react";
import CssBaseline from "@mui/material/CssBaseline";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { createTheme, ThemeProvider } from "@mui/material/styles";

export default function OngoingPollsPoll(props) {
  return (
    <Box
      sx={{
        display: "flex",
        height: "150px",
        width: "150px",
        backgroundColor: "rgb(230,230,230)",
        mr: 2,
        borderRadius: 3,
        flexShrink: 0,
        padding: 1,
        alignItems: "flex-end",
      }}
    >
      {props.data.title}
    </Box>
  );
}
