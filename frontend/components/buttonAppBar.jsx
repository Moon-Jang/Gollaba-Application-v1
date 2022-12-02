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
    const [data, setData] = useState()
    //const [cookies, setCookies] = useCookies()
    //const [token, setToken] = useState(null)
    //const token = localStorage.getItem("accessToken")
    let token
    let userId

    let imgUrl
    const router = useRouter()

    const showUser = async () => {
        if (!token || !userId) return

        const userInfo = await ApiGateway.showUser(userId, token)
        setData(userInfo)
        console.log("showUser :", userInfo)
    }

    useEffect(() => {
        token = localStorage.getItem("accessToken")
        userId = jwt_decode(token).id
    }, [])
    useEffect(() => {
        showUser()
    }, [token])

    if (data !== undefined && data.profileImageUrl !== "") {
        imgUrl = data.profileImageUrl
    }

    const IconButtonOnClick = () => {
        router.push("/account")
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
                            <Avatar src={imgUrl} sx={{ width: 40, height: 40, border: "5px soild black" }} />
                        </Box>
                    ) : (
                        "로그인"
                    )}
                </Toolbar>
            </AppBar>
        </Box>
    )
}
