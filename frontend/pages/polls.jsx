import React, { useState, useEffect } from "react";
import CssBaseline from "@mui/material/CssBaseline";
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
import PollsMap from "../components/polls/mapPoll";
import { theme } from "../src/theme";
import ApiGateway from "../apis/ApiGateway";
/*
const theme = createTheme({
  typography: {
    fontFamily: "'Jua', sans-serif",
    //fontFamily: "GmarketSansMedium",
  },
  palette: {
    primary: {
      main: "#808080",
    },
  },
});
*/

const PollTheme = createTheme(theme);

export default function Polls() {
  const [polls, setPolls] = useState([]);
  const [ref, inView] = useInView();
  const [isLoading, setIsLoading] = useState(false);
  const [offset, setOffset] = useState(0);
  const [totalCount, setTotalCount] = useState(0);

  let response;
  const limit = 15;

  const getData = async () => {
    if (totalCount !== 0 && offset * 15 >= totalCount) return;
    setIsLoading(true);
    response = await ApiGateway.getPolls(offset, limit);
    setPolls([...polls, ...response.polls]);
    setTotalCount(response.totalCount);
    setIsLoading(false);
  };

  useEffect(() => {
    getData();
  }, [offset]);

  useEffect(() => {
    if (inView && !isLoading) {
      setOffset((prevState) => prevState + 1);
    }
  }, [inView, isLoading]);

  if (polls !== undefined)
    return (
      <ThemeProvider theme={theme}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <Box
            sx={{
              marginTop: 7,
              marginBottom: 10,
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
              <PollsMap data={polls} />
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
