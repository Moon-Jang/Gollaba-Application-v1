import React, { useState } from "react";
import Router, { userRouter } from "next/router";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import Divider from "@mui/material/Divider";
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Link from "../src/Link";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import axios from "axios";
import { CookiesProvider, useCookies } from "react-cookie";
import CommonValidator from "../utils/CommonValidator";

const theme = createTheme({
  palette: {
    primary: {
      main: "#808080",
    },
  },
});

export default function Login() {
  const [cookies, setCookies, removeCookies] = useCookies(null);
  const [isErrorId, setIsErrorId] = useState(false);
  const [helperTextId, setHelperTextId] = useState("");
  const [isErrorPassword, setIsErrorPassword] = useState(false);
  const [helperTextPassword, setHelperTextPassword] = useState("");

  const handleChangeId = (event) => {
    if (
      event.target.name === "id" &&
      !CommonValidator.validate("id", event.target.value)
    ) {
      setIsErrorId(true);
      setHelperTextId("ID는 8~32자의 숫자, 문자로 구성되어야 합니다.");
      return;
    }
    setIsErrorId(false);
    setHelperTextId("");
  };

  const handleChangePassword = (event) => {
    if (
      event.target.name === "password" &&
      !CommonValidator.validate("password", event.target.value)
    ) {
      setIsErrorPassword(true);
      setHelperTextPassword(
        "비밀번호는 8~24자의 숫자, 문자, 특수문자가 모두 포함되어야 합니다."
      );
      return;
    }
    setIsErrorPassword(false);
    setHelperTextPassword("");
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (isErrorId || isErrorPassword) {
      alert("Id와 비밀번호가 조건에 맞는지 확인하세요.");
      return;
    }
    const input = new FormData(event.currentTarget);
    const payload = {
      id: input.get("id"),
      password: input.get("password"),
    };
    let response;
    try {
      response = await axios.post(
        "https://dev.api.gollaba.net/v1/login",
        payload
      );
    } catch (e) {
      response = e.response;
      alert(response.data.error.message);
      return;
    } finally {
      if (response.status === 400) return;
      setCookies("accessToken", response.data.accessToken);
      setCookies("refreshToken", response.data.refreshToken);
      Router.push("/polls");
    }
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 5,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <div className="Header">
            <Typography
              component="h1"
              variant="h3"
              sx={{ mt: 2, mb: 7.5, fontWeight: "bold" }}
            >
              Login
            </Typography>
          </div>

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
              id="id"
              label="아이디"
              name="id"
              variant="standard"
              helperText={helperTextId}
              error={isErrorId ? true : false}
              onChange={handleChangeId}
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
              helperText={helperTextPassword}
              error={isErrorPassword ? true : false}
              onChange={handleChangePassword}
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
                sx={{ fontStyle: "italic", mt: 0 }}
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
