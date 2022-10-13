import React, { useState } from "react"
import Router from "next/router"
import Button from "@mui/material/Button"
import CssBaseline from "@mui/material/CssBaseline"
import Divider from "@mui/material/Divider"
import TextField from "@mui/material/TextField"
import Container from "@mui/material/Container"
import Box from "@mui/material/Box"
import Link from "../src/Link"
import { createTheme, ThemeProvider } from "@mui/material/styles"
import axios from "axios"
import { useCookies } from "react-cookie"
import CommonValidator from "../utils/CommonValidator"
import LogoImage from "../public/Gollaba_logo_nuki_v1.png"

const theme = createTheme({
    palette: {
        primary: {
            main: "#808080",
        },
    },
})

export default function Login() {
    const [cookies, setCookies, removeCookies] = useCookies(null)
    const [isErrorId, setIsErrorId] = useState(false)
    const [helperTextId, setHelperTextId] = useState("")
    const [isErrorPassword, setIsErrorPassword] = useState(false)
    const [helperTextPassword, setHelperTextPassword] = useState("")

    const handleChangeId = event => {
        if (event.target.name === "id" && !CommonValidator.validate("id", event.target.value)) {
            setIsErrorId(true)
            setHelperTextId("ID는 8~32자의 숫자, 문자로 구성되어야 합니다.")
            return
        }
        setIsErrorId(false)
        setHelperTextId("")
    }

    const handleChangePassword = event => {
        if (event.target.name === "password" && !CommonValidator.validate("password", event.target.value)) {
            setIsErrorPassword(true)
            setHelperTextPassword("비밀번호는 8~24자의 숫자, 문자, 특수문자가 모두 포함되어야 합니다.")
            return
        }
        setIsErrorPassword(false)
        setHelperTextPassword("")
    }

    const handleSubmit = async event => {
        event.preventDefault()
        if (isErrorId || isErrorPassword) {
            alert("Id와 비밀번호가 조건에 맞는지 확인하세요.")
            return
        }
        const input = new FormData(event.currentTarget)
        const payload = {
            id: input.get("id"),
            password: input.get("password"),
        }
        let response
        try {
            response = await axios.post("https://dev.api.gollaba.net/v1/login", payload)
        } catch (e) {
            response = e.response
            alert(response.data.error.message)
            return
        } finally {
            if (response.status === 400) return
            setCookies("accessToken", response.data.accessToken)
            setCookies("refreshToken", response.data.refreshToken)
            Router.push("/account")
        }
    }
    console.log(LogoImage)
    console.log(LogoImage.src)

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
                        <Link href="/">
                            <img
                                src={LogoImage.src}
                                style={{ width: 200, height: "auto", marginLeft: 8, marginTop: 30 }}
                            />
                        </Link>
                    </div>

                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 2 }}>
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
                            sx={{ mt: 8, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
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
    )
}
// 어디서 넘어왔는지를 쿼리스트링으로 담고 있으면 좋겠다. 지금은 푸터내브에서만 들어가지만, 추후에 투표 수정을 통해서도 들어갈 수 있다.
// 로그인 페이지에서 로그인 완료했을 때 이전 행선지로 바로 넘어갈 수 있도록.
