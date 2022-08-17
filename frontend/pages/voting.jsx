import React, { useState, useEffect } from "react";

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
import BottomNavigation from "@mui/material/BottomNavigation";
import BottomNavigationAction from "@mui/material/BottomNavigationAction";
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";

import ButtonAppBar from "../components/buttonAppBar";
import FooterNav from "../components/footerNav";
import axios from "axios";
import Description from "../components/voting/description";
import MapOption from "../components/voting/mapOption";
import CreateBtn from "../components/voting/createBtn";
import { useRouter } from "next/router";

const theme = createTheme({
  palette: {
    primary: {
      main: "#808080",
    },
  },
  typography: {
    fontFamily: "'Jua', sans-serif",
    //fontFamily: "GmarketSansMedium",
  },
});

export default function Voting() {
  const router = useRouter();
  let response;
  const params = new URLSearchParams(window.location.search);
  let pollId = params.get("pollId");

  const [polls, setPolls] = useState([]);

  const getData = async () => {
    try {
      response = await axios.get(
        "https://dev.api.gollaba.net/v1/polls/" + pollId
      );
      setPolls(response.data);
      console.log("polls", polls);
    } catch (e) {
      response = e.response;
      alert(response.data.error.message);
    } finally {
      return response;
    }
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 7,
            marginBottom: 7,
            display: "flex",
            flexDirection: "column",
            alignItems: "left",
            justifyContent: "center",
          }}
        >
          <div className="header">
            <ButtonAppBar titletext={"Voting"} />
          </div>

          <div className="body" flex="1">
            <Description data={polls} />
            <MapOption data={polls} />
          </div>
          <CreateBtn />
          <div className="footer">
            <FooterNav />
          </div>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
