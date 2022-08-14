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
import { createTheme, ThemeProvider } from "@mui/material/styles";
import BottomNavigation from "@mui/material/BottomNavigation";
import BottomNavigationAction from "@mui/material/BottomNavigationAction";
import { Icon } from "@mui/material";
import { useInView } from "react-intersection-observer";

import axios from "axios";
import ButtonAppBar from "../components/buttonAppBar";
import FooterNav from "../components/footerNav";
import MapPoll from "../components/polls/mapPoll";

const theme = createTheme({
  palette: {
    primary: {
      main: "#808080",
    },
  },
});

export default function Polls() {
  let response;
  const [polls, setPolls] = useState([]);
  const [ref, inView] = useInView();
  const [isLoading, setIsLoading] = useState(false);
  const [offset, setOffset] = useState(0);

  const getData = async () => {
    setIsLoading(true);
    try {
      response = await axios.get(
        `https://dev.api.gollaba.net/v1/polls?limit=15&offset=${offset * 15}`
      );
      let arr = [...polls, ...response.data.polls];
      setPolls(arr);
      setIsLoading(false);
    } catch (e) {
      response = e.response;
      alert(response.data.error.message);
    } finally {
      return response;
    }
  };

  useEffect(() => {
    getData();
  }, [offset]);

  useEffect(() => {
    if (inView && !isLoading) {
      setOffset((prevState) => prevState + 1);
    }
  }, [inView, isLoading]);

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
            <ButtonAppBar titletext={"Polls"} />
          </div>

          <div className="body" flex="1">
            <MapPoll data={polls} />
            <Box ref={ref} />
          </div>

          <div className="footer">
            <FooterNav />
          </div>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
