import React, { useState, useEffect, useRef } from "react";

import CssBaseline from "@mui/material/CssBaseline";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import { createTheme, ThemeProvider } from "@mui/material/styles";

import ButtonAppBar from "../../components/buttonAppBar";
import FooterNav from "../../components/footerNav";
import axios from "axios";
import Description from "../../components/result/description";
import MapOption from "../../components/result/mapOption";
import InfoBox from "../../components/result/infoBox";
import { useRouter } from "next/router";
import ApiGateway from "../../apis/ApiGateway";

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
  const { pollId } = router.query;
  console.log(pollId);
  const [selected, setSelected] = useState([]);
  const [polls, setPolls] = useState([]);

  const getData = async () => {
    response = await ApiGateway.getPoll(pollId);
    setPolls(response);
  };

  useEffect(() => {
    if (pollId) {
      getData();
    }
  }, [pollId]);

  const [voted, setVoted] = useState([]);

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
            height: "83vh",
          }}
        >
          <Box className="header">
            <ButtonAppBar titletext={"Result"} />
          </Box>
          <Box
            className="body"
            flex="1"
            sx={{
              display: "flex",
              flexDirection: "column",
            }}
          >
            <Description data={polls} />
            <InfoBox data={polls.totalVoteCount} />
            <Box display={"flex"} flexDirection={"column"} flex={"1"}>
              <Box
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  flex: 1,
                  //justifyContent: "center",
                }}
              >
                <MapOption data={polls} voted={voted} />
              </Box>
            </Box>
          </Box>

          <Box className="footer">
            <FooterNav />
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
