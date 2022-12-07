import AppBar from "@mui/material/AppBar"
import Box from "@mui/material/Box"
import Toolbar from "@mui/material/Toolbar"
import Typography from "@mui/material/Typography"
import Button from "@mui/material/Button"
import IconButton from "@mui/material/IconButton"
import MenuIcon from "@mui/icons-material/Menu"
import React, { useRef, useState, useEffect } from "react"
import { useCookies } from "react-cookie"
import jwt from "jsonwebtoken"
import ApiGateway from "../apis/ApiGateway"
import { useRouter } from "next/router"
import LogoImage from "../public/Gollaba_logo_textless.png"

import NotificationsIcon from "@mui/icons-material/Notifications"
import { Avatar } from "@mui/material"
import jwt_decode from "jwt-decode"

export default function ButtonAppBar(title) {
    const router = useRouter()
    const [userInfo, setUserInfo] = useState()

    useEffect(async () => {
        const token = getToken();
        const userInfo = await fetchUser(token);

        setUserInfo(userInfo);
    }, [])

    const IconButtonOnClick = () => {
        router.push("/account")
    }
    const LoginButtonOnClick = () => {
        router.push("/login")
    }

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="fixed" color="default" sx={{ boxShadow: "none" }}>
                <Toolbar>
                    <img src={LogoImage.src} style={{ width: 50, height: "auto", marginLeft: -5, marginTop: 0 }} />
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        {title.titletext}
                    </Typography>
                    {userInfo ? (
                        <Box className="IconButton" onClick={IconButtonOnClick}>
                            <Avatar src={userInfo.profileImageUrl} sx={{ width: 40, height: 40, border: "5px soild black" }} />
                        </Box>
                    ) : (
                        <Typography onClick={LoginButtonOnClick}>로그인</Typography>
                    )}
                </Toolbar>
            </AppBar>
        </Box>
    )
}

async function fetchUser(token) {
    const { id } = jwt_decode(token);
    const response = await ApiGateway.showUser(id, token)

    if (response.error) return null

    return response
}

function getToken() {
    const token = localStorage.getItem("accessToken")
    
    if (token === null) return null
    
    const {exp} = jwt_decode(token);
    const expiredDate = new Date(exp * 1000);
    const now = new Date();

    if (expiredDate < now) return null

    return token;
}
