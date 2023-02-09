import React, { Component, useState, useEffect } from "react"
import { Link } from "react-router-dom"

import Paper from "@mui/material/Paper"
import BottomNavigation from "@mui/material/BottomNavigation"
import BottomNavigationAction from "@mui/material/BottomNavigationAction"

import PollOutlinedIcon from "@mui/icons-material/PollOutlined"
import ThumbUpOutlinedIcon from "@mui/icons-material/ThumbUpOutlined"
import AddOutlinedIcon from "@mui/icons-material/AddOutlined"
import AccountCircleOutlinedIcon from "@mui/icons-material/AccountCircleOutlined"
import HomeIcon from "@mui/icons-material/Home"
import { useRouter } from "next/router"
import jwt_decode from "jwt-decode"
import ApiGateway from "../apis/ApiGateway"

export default function FooterNav() {
    const [value, setValue] = useState(0)

    const router = useRouter()
    const [userInfo, setUserInfo] = useState()

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

    return (
        <Paper sx={{ position: "fixed", bottom: 0, left: 0, right: 0 }} elevation={3}>
            <BottomNavigation
                sx={{}}
                showLabels
                // fullWidth
                value={value}
                onChange={(event, newValue) => {
                    setValue(newValue)
                }}
            >
                <BottomNavigationAction
                    label="홈"
                    onClick={() => {
                        router.push("/")
                    }}
                    icon={<HomeIcon />}
                />
                <BottomNavigationAction
                    label="새 투표"
                    onClick={() => {
                        router.push("/new")
                    }}
                    icon={<AddOutlinedIcon />}
                />
                <BottomNavigationAction
                    label="My 투표"
                    onClick={() => {
                        {
                            userInfo ? router.push("/voting") : router.push("/login")
                        }
                    }}
                    icon={<PollOutlinedIcon />}
                />
            </BottomNavigation>
        </Paper>
    )
}

async function fetchUser(token) {
    const { id } = jwt_decode(token)
    const response = await ApiGateway.showUser(id, token)

    if (response.error) return null

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
