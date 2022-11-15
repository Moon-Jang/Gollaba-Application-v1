import React, { useState, useEffect } from "react"
import Router, { userRouter } from "next/router"
import CssBaseline from "@mui/material/CssBaseline"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import Box from "@mui/material/Box"
import { createTheme, ThemeProvider } from "@mui/material/styles"

export default function OngoingPollsPoll(props) {
    const handleClick = () => {
        Router.push(`/polls/${props.data.pollId}`)
    }

    if (props.data.pollImageUrl !== undefined)
        return (
            <Box sx={{ display: "flex", flex: 1, flexDirection: "column" }}>
                <Box
                    sx={{
                        display: "flex",
                        height: "150px",
                        width: "150px",
                        backgroundColor: "rgb(230,230,230)",
                        mr: 2,
                        borderRadius: 3,
                        flexShrink: 0,
                        padding: 1,
                        alignItems: "flex-end",
                        backgroundImage: `url(${props.data.pollImageUrl})`,
                        backgroundSize: "cover",
                        filter: "brightness(95%)",
                        color: "white",
                    }}
                    onClick={handleClick}
                >
                    {props.data.title}
                </Box>
            </Box>
        )
}
