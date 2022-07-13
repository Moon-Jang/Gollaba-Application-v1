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
import BottomNavigation from "@mui/material/BottomNavigation";
import BottomNavigationAction from "@mui/material/BottomNavigationAction";
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";

import { Icon } from "@mui/material";
import { padding } from "@mui/system";

import ButtonAppBar from "../components/buttonAppBar";
import FooterNav from "../components/footerNav";

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
            alignItems: "left",
            justifyContent: "center",
          }}
        >
          <div className="header">
            {" "}
            <ButtonAppBar titletext={"Voting"} />{" "}
          </div>
          <div className="body" flex="1">
            <Box
              sx={{
                marginTop: 0,
                display: "flex",
                flexDirection: "column",
                alignItems: "left",
                justifyContent: "center",
                borderRadius: "16px",
                backgroundColor: "rgb(244,244,244)",
                height: 180,
                overflow: "hidden",
              }}
            >
              <Typography sx={{ marginTop: 1.4, ml: 2.5, flex: 0.5 }}>
                Voting 1
              </Typography>
              <Box
                sx={{
                  flex: 0.5,
                  display: "flex",
                  flexDirection: "rows",
                  backgroundColor: "pink",
                  borderRadius: "16px",
                }}
              ></Box>
            </Box>

            <Box
              sx={{
                marginTop: 5,
                display: "flex",
                flexDirection: "column",
                alignItems: "left",
                justifyContent: "center",
                borderRadius: "16px",
                backgroundColor: "rgb(244,244,244)",
                height: 180,
              }}
            >
              <Typography sx={{ marginTop: 1.5, ml: 2.5, flex: 0.2 }}>
                Which is the funnist looking?
              </Typography>
              <Typography sx={{ marginTop: 1.5, ml: 2.5, flex: 0.2 }}>
                Smith
              </Typography>
              <Box
                sx={{
                  flex: 0.5,
                  display: "flex",
                  flexDirection: "rows",
                  backgroundColor: "pink",
                  borderRadius: "16px",
                }}
              ></Box>
            </Box>

            <Box
              sx={{
                marginTop: 0,
                display: "flex",
                flexDirection: "column",
                alignItems: "left",
                justifyContent: "center",
                borderRadius: "16px",
                backgroundColor: "rgb(244,244,244)",
                height: 180,
              }}
            >
              <Typography sx={{ marginTop: 1.4, ml: 2.5, flex: 0.5 }}>
                Voting 1
              </Typography>
              <Box
                sx={{
                  flex: 0.5,
                  display: "flex",
                  flexDirection: "rows",
                  backgroundColor: "pink",
                  borderRadius: "16px",
                }}
              ></Box>
            </Box>

            <Box
              sx={{
                marginTop: 0,
                display: "flex",
                flexDirection: "column",
                alignItems: "left",
                justifyContent: "center",
                borderRadius: "16px",
                backgroundColor: "rgb(244,244,244)",
                height: 180,
              }}
            >
              <Typography sx={{ marginTop: 1.4, ml: 2.5, flex: 0.5 }}>
                Voting 1
              </Typography>
              <Box
                sx={{
                  flex: 0.5,
                  display: "flex",
                  flexDirection: "rows",
                  backgroundColor: "pink",
                  borderRadius: "16px",
                }}
              ></Box>
            </Box>

            <Box
              sx={{
                marginTop: 0,
                display: "flex",
                flexDirection: "column",
                alignItems: "left",
                justifyContent: "center",
                borderRadius: "16px",
                backgroundColor: "rgb(244,244,244)",
                height: 180,
              }}
            >
              <Typography sx={{ marginTop: 1.4, ml: 2.5, flex: 0.5 }}>
                Voting 1
              </Typography>
              <Box
                sx={{
                  flex: 0.5,
                  display: "flex",
                  flexDirection: "rows",
                  backgroundColor: "pink",
                  borderRadius: "16px",
                }}
              ></Box>
            </Box>
          </div>

          <div className="footer">
            <FooterNav />
          </div>
        </Box>
      </Container>
    </ThemeProvider>
  );
}

const itemData = [
  {
    img: "https://images.unsplash.com/photo-1551963831-b3b1ca40c98e",
    title: "Breakfast",
  },
  {
    img: "https://images.unsplash.com/photo-1551782450-a2132b4ba21d",
    title: "Burger",
  },
  {
    img: "https://images.unsplash.com/photo-1522770179533-24471fcdba45",
    title: "Camera",
  },
];
