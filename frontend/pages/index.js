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
import ProTip from "../src/ProTip";
import Link from "../src/Link";
import Checkbox from "@mui/material/Checkbox";
import Grid from "@mui/material/Grid";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import CameraAltRoundedIcon from "@mui/icons-material/CameraAltRounded";

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="https://mui.com/">
        Your Website
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const theme = createTheme({
  palette: {
    primary: {
      main: "#808080",
    },
  },
});

export default function Index() {
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
    });
  };

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
          <Typography
            component="h1"
            variant="h3"
            sx={{ mt: 2.5, mb: 7.5, fontWeight: "bold" }}
          >
            Login
          </Typography>

          <Avatar
            src="../public/camera_icon.png"
            sx={{ width: 85, height: 85 }}
          />

          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 6 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="Id"
              label="아이디"
              name="ID"
              variant="standard"
              autoFocus
            />
            <TextField
              required
              fullWidth
              name="password"
              variant="standard"
              label="비밀번호"
              type="password"
              id="password"
              autoComplete="current-password"
            />

            <Button
              color="primary"
              type="submit"
              variant="outlined"
              fullWidth
              style={{ verticalAlign: "middle", color: "#000000" }}
              sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
            >
              Login
            </Button>
            <Link href="/signup">
              <Button
                type="submit"
                variant="outlined"
                fullWidth
                style={{ verticalAlign: "middle", color: "#000000" }}
                sx={{ mt: 1.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
              >
                Sign up
              </Button>
            </Link>
            <Divider color="primary" sx={{ fontStyle: "italic", mt: 1, mb: 2 }}>
              Or
            </Divider>
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
              }}
            >
              <Link
                href="#"
                underline="always"
                level="body1"
                variant="plain"
                sx={{ fontStyle: "italic", mt: 1 }}
              >
                Forgot your password?
              </Link>
            </Box>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
