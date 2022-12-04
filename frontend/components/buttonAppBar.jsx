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
    const [data, setData] = useState()
    //const [cookies, setCookies] = useCookies()
    //const [token, setToken] = useState(null)
    //const token = localStorage.getItem("accessToken")
    const token = useRef(null)
    const userId = useRef(null)
    const imgUrl = useRef(null)

    const showUser = async () => {
        if (!token.current || !userId.current) return

        const userInfo = await ApiGateway.showUser(userId.current, token.current)
        setData(userInfo)
        console.log("showUser :", userInfo)
    }

    useEffect(() => {
        token.current = localStorage.getItem("accessToken")
        if (token.current === null) return
        userId.current = jwt_decode(token.current).id
    }, [])
    useEffect(() => {
        showUser()
    }, [token.current])

    if (data !== undefined && data.profileImageUrl !== "") {
        imgUrl.current = data.profileImageUrl
    }

    const IconButtonOnClick = () => {
        router.push("/account")
    }
    const LoginButtonOnClick = () => {
        router.push("/login")
    }
    console.log("데이터", data)
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="fixed" color="default" sx={{ boxShadow: "none" }}>
                <Toolbar>
                    <img src={LogoImage.src} style={{ width: 50, height: "auto", marginLeft: -5, marginTop: 0 }} />
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        {title.titletext}
                    </Typography>
                    {!data?.error ? (
                        <Box className="IconButton" onClick={IconButtonOnClick}>
                            <Avatar src={imgUrl.current} sx={{ width: 40, height: 40, border: "5px soild black" }} />
                        </Box>
                    ) : (
                        <Typography onClick={LoginButtonOnClick}>로그인</Typography>
                    )}
                </Toolbar>
            </AppBar>
        </Box>
    )
}
