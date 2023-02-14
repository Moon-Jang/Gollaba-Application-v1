import AppBar from "@mui/material/AppBar"
import Box from "@mui/material/Box"
import Toolbar from "@mui/material/Toolbar"
import Typography from "@mui/material/Typography"
import Button from "@mui/material/Button"
import IconButton from "@mui/material/IconButton"
import MenuIcon from "@mui/icons-material/Menu"
import React, { useRef, useState, useEffect } from "react"
import jwt from "jsonwebtoken"
import ApiGateway from "../apis/ApiGateway"
import { useRouter } from "next/router"
import LogoImage from "../public/Gollaba_logo_textless.png"
import InputBase from "@mui/material/InputBase"
import SearchIcon from "@mui/icons-material/Search"

import NotificationsIcon from "@mui/icons-material/Notifications"
import { Avatar } from "@mui/material"
import jwt_decode from "jwt-decode"

export default function ButtonAppBar(title) {
    const router = useRouter()
    const [userInfo, setUserInfo] = useState()
    const [isSearchClick, setIsSearchClick] = useState(false)
    const inputRef = useRef(null)

    useEffect(async () => {
        const token = getToken()

        if (token !== null) {
            const userInfo = await fetchUser(token)
            setUserInfo(userInfo)
        }
    }, [])

    const IconButtonOnClick = () => {
        router.push("/account")
    }
    const LoginButtonOnClick = () => {
        router.push("/login")
    }
    const handleSubmit = (event) => {
        event.preventDefault()
        router.push(`/search/${inputRef.current.value}`)
    }

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="fixed" color="default" sx={{ boxShadow: "none" }}>
                <Toolbar>
                    <Box sx={{ display: "flex", flex: 1 }}>
                        <img src={LogoImage.src} style={{ width: 50, height: "auto", marginLeft: -5, marginTop: 0 }} />
                        <Typography
                            variant="h6"
                            component="div"
                            sx={{ flexGrow: 1, letterSpacing: -0.5, pl: 0.5, pt: 1.2 }}
                        >
                            {title.titletext}
                        </Typography>
                    </Box>
                    <Box sx={{ display: "flex", flex: 1 }} />
                    <Box
                        sx={{
                            display: "flex",
                            flex: 3,
                            justifyContent: "flex-end",
                            marginLeft: "auto",
                        }}
                    >
                        <Box
                            sx={{
                                display: "flex",
                                flexDirection: "row",
                                flex: 4,
                                pr: 1,
                                justifyContent: "flex-end",
                                marginLeft: "auto",
                            }}
                        >
                            {isSearchClick ? (
                                <Box>
                                    <form onSubmit={handleSubmit}>
                                        <InputBase
                                            placeholder="검색하기..."
                                            inputProps={{ "aria-label": "search" }}
                                            inputRef={inputRef}
                                            onSubmit={handleSubmit}
                                            sx={{
                                                border: 1,
                                                borderRadius: 2,
                                                borderColor: "grey.300",
                                                boxShadow: "0 0 5px 1px rgba(0,0,0,0.055)",
                                                backgroundColor: "white",
                                                paddingLeft: "10px",
                                            }}
                                        />
                                    </form>
                                </Box>
                            ) : (
                                ""
                            )}
                            <SearchIcon
                                sx={{ color: "gray", mt: 0.3, ml: 0.5, fontSize: 30 }}
                                onClick={() => setIsSearchClick(true)}
                            />
                        </Box>
                        <Box
                            sx={{
                                mr: -7,
                                minWidth: "100px",
                                justifyContent: "flex-end",
                                pt: 0.8,
                            }}
                        >
                            {userInfo ? (
                                <Box className="IconButton" onClick={IconButtonOnClick}>
                                    <Avatar
                                        src={userInfo.profileImageUrl}
                                        sx={{ width: 40, height: 40, border: "5px soild black" }}
                                    />
                                </Box>
                            ) : (
                                <Typography onClick={LoginButtonOnClick}>로그인</Typography>
                            )}
                        </Box>
                    </Box>
                </Toolbar>
            </AppBar>
        </Box>
    )
}

async function fetchUser(token) {
    const { id } = jwt_decode(token)
    const response = await ApiGateway.showUser(id, token)

    if (response?.error) return null

    return response
}

function getToken() {
    const token = localStorage.getItem("accessToken")

    if (token === null) return null

    const { exp } = jwt_decode(token)
    const expiredDate = new Date(exp * 1000)
    const now = new Date()

    if (expiredDate < now) return null

    return token
}
