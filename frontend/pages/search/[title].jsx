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
import { useRouter } from "next/router"

import axios from "axios"
import ButtonAppBar from "../../components/buttonAppBar"
import FooterNav from "../../components/footerNav"
import PollsMap from "../../components/polls/mapPoll"
import theme from "../../src/theme"
import ApiGateway from "../../apis/ApiGateway"
import jwt_decode from "jwt-decode"

const PollTheme = createTheme(theme)

export default function Search() {
    const router = useRouter()
    const [polls, setPolls] = useState([])
    const [ref, inView] = useInView()
    const [isLoading, setIsLoading] = useState(false)
    const [offset, setOffset] = useState(0)
    const [totalCount, setTotalCount] = useState(0)
    const [userInfo, setUserInfo] = useState()

    let response
    const limit = 15
    const [pollTitleFromQuery, setPollTitleFromQuery] = useState("")

    useEffect(() => {
        console.log("37", router.query.title)
        if (router.query.title) setPollTitleFromQuery(router.query.title)
    }, [router.query.title])

    useEffect(async () => {
        if (pollTitleFromQuery && pollTitleFromQuery !== null) {
            console.log("43", pollTitleFromQuery)
            await getData()
        }
    }, [pollTitleFromQuery])

    const getData = async (token) => {
        setIsLoading(true)
        response = await ApiGateway.searchPolls(offset, limit, pollTitleFromQuery, null)
        setPolls([...polls, ...response.polls])
        setTotalCount(response.totalCount)
        setIsLoading(false)
    }

    useEffect(async () => {
        const token = getToken()

        if (token !== null) {
            const userInfo = await fetchUser(token)
            setUserInfo(userInfo)
        }
    }, [])

    useEffect(() => {
        if (inView && !isLoading) {
            setOffset((prevState) => prevState + 1)
        }
    }, [inView, isLoading])

    if (polls !== undefined)
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
                            <ButtonAppBar titletext={"Polls"} />
                        </div>
                        <Box
                            className="body"
                            flex="1"
                            sx={{
                                display: "flex",
                                flexDirection: "column",
                            }}
                        >
                            <Box className="Title" sx={{ pl: 0.3, mt: 0.3 }}>
                                ✍ 내 투표
                            </Box>
                            <Box display={"flex"} flexDirection={"column"} flex={"1"}>
                                <Box
                                    sx={{
                                        display: "flex",
                                        flexDirection: "column",
                                        flex: 1,
                                        overflow: "auto",
                                        maxHeight: "80vh",
                                        mt: 0,
                                        pl: 1.2,
                                        pr: 1.2,
                                    }}
                                >
                                    <PollsMap data={polls} />
                                </Box>
                            </Box>
                            <Box ref={ref} />
                        </Box>

                        <div className="footer">
                            <FooterNav />
                        </div>
                    </Box>
                </Container>
            </ThemeProvider>
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
