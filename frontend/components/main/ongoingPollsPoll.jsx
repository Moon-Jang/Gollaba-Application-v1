import React, { useState, useEffect } from "react"
import Router, { userRouter } from "next/router"
import CssBaseline from "@mui/material/CssBaseline"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import Box from "@mui/material/Box"
import { createTheme, ThemeProvider } from "@mui/material/styles"
import Options from "./options"

export default function OngoingPollsPoll(props) {
    const handleClick = () => {
        {
            today < date ? Router.push(`/polls/${props.data.pollId}`) : Router.push(`/result/${props.data.pollId}`)
        }
    }
    const OptionsMap = () => {
        const data = props.data.options
        return data.map(el => <Options data={el} />)
    }
    const date = new Date(props.data.endedAt)
    const strDate = date
        .toISOString()
        .substring(0, 10)
        .split("-")

    const today = new Date()

    if (props.data.pollImageUrl !== undefined)
        return (
            <Box
                sx={{
                    display: "flex",
                    height: "140px",
                    width: "300px",
                    mr: 1,
                    ml: 0.5,
                    boxShadow: "0 0 5px 1px rgba(0,0,0,0.095)",
                    //border: 1,
                    borderColor: "lightgray",
                    borderRadius: 2,
                    flexShrink: 0,
                    flexDirection: "column",
                    //alignItems: "flex-end",
                    mt: 0.8,
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
                        flexDirection: "column",
                        pt: 1,
                    }}
                >
                    <Typography sx={{ letterSpacing: 0 }}>{props.data.title}</Typography>
                    <Typography sx={{ fontSize: 12, letterSpacing: 0, color: "rgb(192, 192, 192)" }}>
                        {strDate[1] + "월 " + strDate[2] + "일까지 · " + props.data.totalVoteCount + "명 참여"}
                    </Typography>
                </Box>
                <Box
                    sx={{
                        display: "flex",
                        width: "100%",
                        height: "80px",
                        //backgroundColor: "rgb(180,180,180)",
                        borderRadius: 3,
                        borderTopRightRadius: 0,
                        borderTopLeftRadius: 0,
                    }}
                >
                    {OptionsMap()}
                </Box>
            </Box>
        )
}
