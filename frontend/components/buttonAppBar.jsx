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
import Router from "next/router"

import NotificationsIcon from "@mui/icons-material/Notifications"
import { Avatar } from "@mui/material"

export default function ButtonAppBar(title) {
    const [data, setData] = useState()
    const [cookies, setCookies] = useCookies()
    const [token, setToken] = useState(null)
    let imgUrl

    const showUser = async () => {
        console.log("token : ", token)
        if (!token) return

        const userInfo = await ApiGateway.showUser(token.id, cookies.accessToken)
        setData(userInfo)
        console.log("showUser :", userInfo)
    }

    useEffect(() => {
        setToken(jwt.decode(cookies.accessToken))
    }, [cookies])
    useEffect(() => {
        showUser()
    }, [token])

    if (data !== undefined && data.profileImageUrl !== "") {
        imgUrl = data.profileImageUrl
    }

    console.log("데이터", data)
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="fixed" color="default" sx={{ boxShadow: "none" }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        {title.titletext}
                    </Typography>
                    <Box className="IconButton" onClick={IconButtonOnClick(token, data)}>
                        <Avatar src={imgUrl} sx={{ width: 40, height: 40, border: "5px soild black" }} />
                    </Box>
                </Toolbar>
            </AppBar>
        </Box>
    )
}

const IconButtonOnClick = (token, data) => {
    if (!token) Router.push(`/login`)
    if (token) Router.push(`/account/${data.userId}`)
}
