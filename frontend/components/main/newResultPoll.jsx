import React, { useState, useEffect } from "react"
import CssBaseline from "@mui/material/CssBaseline"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import Box from "@mui/material/Box"
import { createTheme, ThemeProvider } from "@mui/material/styles"
import { width } from "@mui/system"
import Options from "./options"
import Router from "next/router"

export default function NewResultPoll(props) {
    const handleClick = () => {
        Router.push(`/polls/${props.data.pollId}`)
    }
    const OptionsMap = () => {
        const data = props.data.options
        return data.map(el => <Options data={el} />)
    }
    return (
        <Box
            sx={{
                display: "flex",
                height: "150px",
                width: "300px",
                backgroundColor: "rgb(230,230,230)",
                mr: 2,
                borderRadius: 3,
                flexShrink: 0,
                flexDirection: "column",
                //alignItems: "flex-end",
            }}
            onClick={handleClick}
        >
            <Box
                sx={{
                    display: "flex",
                    width: "100%",
                    height: "70px",
                    alignItems: "center",
                    justifyContent: "center",
                }}
            >
                {props.data.title}
            </Box>
            <Box
                sx={{
                    display: "flex",
                    width: "100%",
                    height: "80px",
                    backgroundColor: "rgb(180,180,180)",
                    borderRadius: 3,
                    borderTopRightRadius: 0,
                    borderTopLeftRadius: 0,
                    borderTop: 1,
                }}
            >
                {OptionsMap()}
            </Box>
        </Box>
    )
}
