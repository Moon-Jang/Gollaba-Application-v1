import React, { useState, useEffect } from "react"
import CssBaseline from "@mui/material/CssBaseline"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import Box from "@mui/material/Box"
import { createTheme, ThemeProvider } from "@mui/material/styles"
import BottomNavigation from "@mui/material/BottomNavigation"
import BottomNavigationAction from "@mui/material/BottomNavigationAction"
import { Icon } from "@mui/material"
import { useInView } from "react-intersection-observer"

import axios from "axios"
import ButtonAppBar from "../components/buttonAppBar"
import FooterNav from "../components/footerNav"
import PollsMap from "../components/polls/mapPoll"
import theme from "../src/theme"
import ApiGateway from "../apis/ApiGateway"

import OngoingPolls from "../components/main/ongoingPolls"
import NewResults from "../components/main/newResults"

const PollTheme = createTheme(theme)

export default function Main() {
    const [polls, setPolls] = useState([])
    let response
    const offset = 0
    const limit = 10

    const getData = async () => {
        response = await ApiGateway.getPolls(offset, limit)
        setPolls([...polls, ...response.polls])
    }

    useEffect(() => {
        getData()
    }, [])

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 9,
                        marginBottom: 7,
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "left",
                        justifyContent: "center",
                        height: "83vh",
                        maxHeight: "83vh",
                        overflow: "hidden",
                    }}
                >
                    <div className="header">
                        <ButtonAppBar titletext={"Voting"} />
                    </div>
                    <Box
                        className="body"
                        sx={{
                            display: "flex",
                            flexDirection: "column",
                            flex: 1,
                            overflow: "auto",
                            maxHeight: "90vh",
                        }}
                    >
                        <OngoingPolls data={polls} menuTitle={"New Results!"} />
                        <NewResults data={polls} menuTitle={"Ongoing Polls"} />
                    </Box>

                    <div className="footer">
                        <FooterNav />
                    </div>
                </Box>
            </Container>
        </ThemeProvider>
    )
}
