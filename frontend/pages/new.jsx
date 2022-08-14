import React, { useState } from "react";
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

import { Icon, Paper, Step, StepContent, StepLabel, Stepper } from "@mui/material";
//import {makeStyles} from "@mui/styles";
import { makeStyles, styled } from '@mui/styles';
import ButtonAppBar from "../components/buttonAppBar";
import FooterNav from "../components/footerNav";
import TitleAndCheckbox from "../components/new/titleAndCheckbox";

import AddAndDelete from "../components/new/addAndDelete";
import Option from "../components/new/option";
import Options from "../components/new/options";
import CreateBtn from "../components/new/createBtn";
import VerticalLinearStepper from "../components/new/VerticalLinearStepper";

const theme = createTheme({
  palette: {
    primary: {
      main: "#808080",
    },
  },
});

const handleSubmit = async (event) => {
  event.preventDefault();
  console.log("hello");
};

const initPollingItems = [
  {
    description: "",
  },
  {
    description: "",
  },
];

const newPageStyles = makeStyles((theme) => ({
  container: {
    display: "flex",
    flexDirection: "column",
    height: "100vh"
  },
  main: {
    width: "100%",
    height: "100%",
    // display: "flex",
    marginTop: "16px",
    // flexDirection: "column",
    // justifyContent: "center", 
  },
  header: {
    height: "56px",
    width: "100%",
  },
  footer: {
    height: "68px",
    width: "100%",
  }
})) 


const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

export default function NewPage() {
  if (typeof window !== "object") return <></>;
  
  const classes = newPageStyles();
  
  return (
    <ThemeProvider theme={theme}>
      <Container className={classes.container} component="div" maxWidth="sm">
        <CssBaseline />
        <div className={classes.header}>
          <ButtonAppBar titletext={"New"} />
        </div>
        <div className={classes.main}>
          <VerticalLinearStepper />
        </div>
        <div className={classes.footer}>
          <FooterNav />
        </div>
      </Container>
    </ThemeProvider>
  );
}

// export default function Polls() {
//   const [title, setTitle] = useState("");
  
//   const [responseType, setResponseType] = useState("SINGLE");
//   const [isBallot, setIsBallot] = useState(false);
//   const [pollingItems, setPollingItems] = useState([...initPollingItems]);

//   return (
//     <ThemeProvider theme={theme}>
//       <Container component="main" maxWidth="xs">
//         <CssBaseline />
//         <Box
//           sx={{
//             marginTop: 7,
//             marginBottom: 7,
//             display: "flex",
//             flexDirection: "column",
//             alignItems: "left",
//             justifyContent: "center",
//           }}
//         >
//           <div className="header">
//             <ButtonAppBar titletext={"New"} />
//           </div>

//           <Box
//             className="body"
//             flex="1"
//             component="form"
//             onSubmit={handleSubmit}
//           >
//             <TitleAndCheckbox
//               setTitle={setTitle}
//               responseType={responseType}
//               setResponseType={setResponseType}
//               isBallot={isBallot}
//               setIsBallot={setIsBallot}
//             />

//             <Options
//               pollingItems={pollingItems}
//               setPollingItems={setPollingItems}
//             />
//             <AddAndDelete
//               pollingItems={pollingItems}
//               setPollingItems={setPollingItems}
//             />
//           </Box>

//           <CreateBtn
//             title={title}
//             responseType={responseType}
//             isBallot={isBallot}
//             pollingItems={pollingItems}
//           />
//           <div className="footer">
//             <FooterNav />
//           </div>
//         </Box>
//       </Container>
//     </ThemeProvider>
//   );
// }
