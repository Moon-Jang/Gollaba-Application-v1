import React, { useState, useEffect, useRef } from "react";

import CssBaseline from "@mui/material/CssBaseline";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import { createTheme, ThemeProvider } from "@mui/material/styles";

import ButtonAppBar from "../../components/buttonAppBar";
import FooterNav from "../../components/footerNav";
import axios from "axios";
import Description from "../../components/voting/description";
import MapOption from "../../components/voting/mapOption";
import CreateBtn from "../../components/voting/createBtn";
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
  const {pollId} = router.query;
  console.log(pollId)
  const [selected, setSelected] = useState([]);
  const [polls, setPolls] = useState([]);

  const getData = async () => {
    try {
      response = await axios.get(
        "https://dev.api.gollaba.net/v1/polls/" + pollId
      );
      setPolls(response.data);
      console.log(">>>", response.data);
    } catch (e) {
      response = e.response;
      alert(response.data.error.message);
    } finally {
      return response;
    }
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
            <ButtonAppBar titletext={"Voting"} />
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
            <MapOption data={polls} voted={voted} setVoted={setVoted} />
          </Box>

          <CreateBtn pollId={pollId} voted={voted} />

          <Box className="footer">
            <FooterNav />
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
