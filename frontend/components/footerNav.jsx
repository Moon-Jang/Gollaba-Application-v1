import React, { Component, useState, useEffect } from "react"
import { Link } from "react-router-dom"

import Paper from "@mui/material/Paper"
import BottomNavigation from "@mui/material/BottomNavigation"
import BottomNavigationAction from "@mui/material/BottomNavigationAction"

import PollOutlinedIcon from "@mui/icons-material/PollOutlined"
import ThumbUpOutlinedIcon from "@mui/icons-material/ThumbUpOutlined"
import AddOutlinedIcon from "@mui/icons-material/AddOutlined"
import AccountCircleOutlinedIcon from "@mui/icons-material/AccountCircleOutlined"
import { useRouter } from "next/router"
import { useCookies } from "react-cookie"
import jwt from "jsonwebtoken"

export default function FooterNav() {
    const [value, setValue] = useState(0)
    const router = useRouter()
    const [cookies, setCookies, removeCookies] = useCookies(null)
    const [token, setToken] = useState(null)

    useEffect(() => {
        setToken(jwt.decode(cookies.accessToken))
    }, [cookies])

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
                    label="Polls"
                    onClick={() => {
                        router.push("/")
                    }}
                    icon={<PollOutlinedIcon />}
                />
                <BottomNavigationAction
                    label="Voting"
                    onClick={() => {
                        router.push("/voting")
                    }}
                    icon={<ThumbUpOutlinedIcon />}
                />
                <BottomNavigationAction
                    label="New"
                    onClick={() => {
                        router.push("/new")
                    }}
                    icon={<AddOutlinedIcon />}
                />
                <BottomNavigationAction
                    label="Account"
                    onClick={() => {
                        if (token == null) {
                            router.push("/login")
                        } else {
                            router.push("/account")
                        }
                    }}
                    icon={<AccountCircleOutlinedIcon />}
                />
            </BottomNavigation>
        </Paper>
    )
}
