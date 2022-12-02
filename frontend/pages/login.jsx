import React, { useState } from "react"
import Router from "next/router"
import Button from "@mui/material/Button"
import CssBaseline from "@mui/material/CssBaseline"
import Divider from "@mui/material/Divider"
import TextField from "@mui/material/TextField"
import Container from "@mui/material/Container"
import Box from "@mui/material/Box"
import Link from "../src/Link"
import { createTheme, rgbToHex, ThemeProvider } from "@mui/material/styles"
import axios from "axios"
import { useCookies } from "react-cookie"
import CommonValidator from "../utils/CommonValidator"
import LogoImage from "../public/Gollaba_logo_nuki_v1.png"
import NaverLogoImage from "../public/naver_logo.png"
import KakaoLogoImage from "../public/kakao_logo.png"

import FacebookIcon from "@mui/icons-material/Facebook"
import socialBtn from "../components/login/btn"
import { Typography } from "@mui/material"
import { BrowserRouter, Switch, Route, useLocation } from "react-router-dom"
import { useEffect } from "react"

const theme = createTheme({
    palette: {
        primary: {
            main: "#808080",
        },
    },
})

export default function Login() {
    const [host, setHost] = useState("")

    useEffect(() => {
        setHost(window.location.protocol + "//" + window.location.host)
    }, [])

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 0,
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "center",
                        height: "100vh",
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

                    <Box noValidate>
                        <Link
                            href={`https://dev.api.gollaba.net/oauth2/authorize/facebook?redirect_uri=${host}/temp/oauth-callback`}
                            underline="none"
                        >
                            <Button
                                color="primary"
                                variant="outlined"
                                fullWidth
                                style={{ verticalAlign: "middle", color: "#1878F2", textTransform: "none" }}
                                sx={{
                                    mt: 8,
                                    mb: 1,
                                    boxShadow: 0,
                                    border: 0.1,
                                    borderColor: "lightgray",
                                    borderRadius: 0.5,
                                    //backgroundColor: "#3B5998",
                                    height: 55,
                                    ":hover": { bgcolor: "#E5E8EB", borderColor: "#E5E8EB" },
                                }}
                            >
                                <Box sx={{ display: "flex", flex: 1 }}>
                                    <FacebookIcon />
                                </Box>
                                <Box sx={{ display: "flex", flex: 15, justifyContent: "center" }}>
                                    <Typography color="#737881" sx={{ fontWeight: "Bold" }}>
                                        Facebook 로그인
                                    </Typography>
                                </Box>
                            </Button>
                        </Link>
                        <Link
                            href={`https://dev.api.gollaba.net/oauth2/authorize/naver?redirect_uri=${host}/temp/oauth-callback`}
                            underline="none"
                        >
                            <Button
                                color="primary"
                                variant="outlined"
                                fullWidth
                                style={{ verticalAlign: "middle", color: "#1878F2", textTransform: "none" }}
                                sx={{
                                    mt: 1,
                                    mb: 1,
                                    boxShadow: 0,
                                    border: 0.1,
                                    borderColor: "lightgray",
                                    borderRadius: 0.5,
                                    //backgroundColor: "#3B5998",
                                    height: 55,
                                    ":hover": { bgcolor: "#E5E8EB", borderColor: "#E5E8EB" },
                                }}
                            >
                                <Box sx={{ display: "flex", flex: 1 }}>
                                    <img
                                        src={NaverLogoImage.src}
                                        style={{ width: 21, height: "auto", marginLeft: 2 }}
                                    />
                                </Box>
                                <Box sx={{ display: "flex", flex: 15, justifyContent: "center" }}>
                                    <Typography color="#737881" sx={{ fontWeight: "Bold" }}>
                                        Naver 로그인
                                    </Typography>
                                </Box>
                            </Button>
                        </Link>
                        <Link
                            href={`https://dev.api.gollaba.net/oauth2/authorize/kakao?redirect_uri=${host}/temp/oauth-callback`}
                            underline="none"
                        >
                            <Button
                                color="primary"
                                variant="outlined"
                                fullWidth
                                style={{ verticalAlign: "middle", color: "#1878F2", textTransform: "none" }}
                                sx={{
                                    mt: 1,
                                    mb: 1,
                                    boxShadow: 0,
                                    border: 0.1,
                                    borderColor: "lightgray",
                                    borderRadius: 0.5,
                                    //backgroundColor: "#3B5998",
                                    height: 55,
                                    ":hover": { bgcolor: "#E5E8EB", borderColor: "#E5E8EB" },
                                }}
                            >
                                <Box sx={{ display: "flex", flex: 1 }}>
                                    <img
                                        src={KakaoLogoImage.src}
                                        style={{ width: 22, height: "auto", marginLeft: 2 }}
                                    />
                                </Box>
                                <Box sx={{ display: "flex", flex: 15, justifyContent: "center" }}>
                                    <Typography color="#737881" sx={{ fontWeight: "bold" }}>
                                        Kakao 로그인
                                    </Typography>
                                </Box>
                            </Button>
                        </Link>
                    </Box>
                    <Box
                        sx={{ display: "flex", position: "fixed", bottom: 8, width: "100%", justifyContent: "center" }}
                    >
                        <Typography color="#737881" sx={{ fontWeight: "normal", fontSize: 12 }}>
                            Copyright 2023. Gollaba All rights reserverd
                        </Typography>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    )
}
// 어디서 넘어왔는지를 쿼리스트링으로 담고 있으면 좋겠다. 지금은 푸터내브에서만 들어가지만, 추후에 투표 수정을 통해서도 들어갈 수 있다.
// 로그인 페이지에서 로그인 완료했을 때 이전 행선지로 바로 넘어갈 수 있도록.
