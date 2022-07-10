import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import Divider from "@mui/material/Divider";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import ButtonGroup from "@mui/material/ButtonGroup";
import ProTip from "../src/ProTip";
import Link from "../src/Link";
import Checkbox from "@mui/material/Checkbox";
import Grid from "@mui/material/Grid";
import { createTheme, ThemeProvider } from "@mui/material/styles";

import PollOutlinedIcon from "@mui/icons-material/PollOutlined";
import ThumbUpOutlinedIcon from "@mui/icons-material/ThumbUpOutlined";
import AddOutlinedIcon from "@mui/icons-material/AddOutlined";
import AccountCircleOutlinedIcon from "@mui/icons-material/AccountCircleOutlined";

import { Icon } from "@mui/material";

const theme = createTheme({
  palette: {
    primary: {
      main: "#808080",
    },
  },
});

export default function Voting() {
  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <ButtonGroup
            variant="text"
            aria-label="text button group"
            display="flex"
          >
            <Button sx={{ display: "flex", flexDirection: "column" }}>
              <PollOutlinedIcon fontSize="medium" />
              Polls
            </Button>
            <Button sx={{ display: "flex", flexDirection: "column" }}>
              <ThumbUpOutlinedIcon fontsize="large" />
              Voting
            </Button>
            <Button sx={{ display: "flex", flexDirection: "column" }}>
              <AddOutlinedIcon fontsize="large" />
              New
            </Button>
            <Button sx={{ display: "flex", flexDirection: "column" }}>
              <AccountCircleOutlinedIcon fontsize="large" />
              Account
            </Button>
          </ButtonGroup>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
