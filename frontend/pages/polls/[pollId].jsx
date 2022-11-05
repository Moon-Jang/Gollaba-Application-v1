import React, { useState, useEffect, useRef } from "react"

import CssBaseline from "@mui/material/CssBaseline"
import Container from "@mui/material/Container"
import Box from "@mui/material/Box"
import { createTheme, ThemeProvider } from "@mui/material/styles"

import ButtonAppBar from "../../components/buttonAppBar"
import FooterNav from "../../components/footerNav"
import axios from "axios"
import Description from "../../components/voting/description"
import MapOption from "../../components/voting/mapOption"
import CreateBtn from "../../components/voting/createBtn"

import { useRouter } from "next/router"
import ApiGateway from "../../apis/ApiGateway"
import UpdateBtn from "../../components/voting/updateBtn"

const theme = createTheme({
    palette: {
        primary: {
            main: "#808080",
        },
    },
    typography: {
        fontFamily: "'Jua', sans-serif",
        //fontFamily: "GmarketSansMedium",
    },
})

export default function Polls() {
    const router = useRouter()
    let response
    const { pollId } = router.query
    console.log(pollId)
    const [selected, setSelected] = useState([])
    const [polls, setPolls] = useState([])

    const getData = async () => {
        response = await ApiGateway.getPoll(pollId)
        setPolls(response)
    }

    useEffect(() => {
        if (pollId) {
            getData()
        }
    }, [pollId])

    const [voted, setVoted] = useState([])

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 9,
                        marginBottom: 7,
                        pl: 0.3,
                        pr: 0.3,
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "left",
                        justifyContent: "center",
                        height: "83vh",
                        overflow: "hidden",
                    }}
                >
                    <Box className="header">
                        <ButtonAppBar titletext={"Polls"} />
                    </Box>
                    <Box
                        className="body"
                        flex="1"
                        sx={{
                            display: "flex",
                            flexDirection: "column",
                        }}
                    >
                        <Description data={polls} />
                        <Box display={"flex"} flexDirection={"column"} flex={"1"}>
                            <Box
                                sx={{
                                    display: "flex",
                                    flexDirection: "column",
                                    flex: 1,
                                    //maxHeight: "42vh",
                                    overflow: "auto",
                                }}
                            >
                                <MapOption data={polls} voted={voted} setVoted={setVoted} />
                            </Box>
                            <CreateBtn pollId={pollId} isBallot={polls.isBallot} voted={voted} />
                        </Box>
                    </Box>

                    <Box className="footer">
                        <FooterNav />
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    )
}
