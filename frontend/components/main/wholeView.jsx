import React, { useState, useEffect, useRef } from "react"
import CssBaseline from "@mui/material/CssBaseline"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import Box from "@mui/material/Box"
import { createTheme, ThemeProvider } from "@mui/material/styles"
import BottomNavigation from "@mui/material/BottomNavigation"
import BottomNavigationAction from "@mui/material/BottomNavigationAction"
import { Icon, TextField } from "@mui/material"
import { useInView } from "react-intersection-observer"

import PollsMap from "../main/mapPoll"
import theme from "../../src/theme"
import ApiGateway from "../../apis/ApiGateway"
import { useCookies } from "react-cookie"

const PollTheme = createTheme(theme)

export default function WholeView() {
    const [polls, setPolls] = useState([])
    const [ref, inView] = useInView()
    const [isLoading, setIsLoading] = useState(false)
    const [offset, setOffset] = useState(0)
    const [totalCount, setTotalCount] = useState(0)
    const [cookies, setCookies, removeCookies] = useCookies([])
    const inputRef = useRef(null)
    let response
    const limit = 15

    const getData = async () => {
        if (totalCount !== 0 && offset * 15 >= totalCount) return
        setIsLoading(true)
        response = await ApiGateway.getPolls(offset, limit, cookies.accessToken)
        setPolls([...polls, ...response.polls])
        setTotalCount(response.totalCount)
        setIsLoading(false)
    }

    useEffect(() => {
        getData()
    }, [offset])

    useEffect(() => {
        if (inView && !isLoading) {
            setOffset((prevState) => prevState + 1)
        }
    }, [inView, isLoading])

    const handleSubmit = (event) => {
        event.preventDefault()
        console.log(inputRef.current.value)
    }

    if (polls !== undefined)
        return (
            <Box sx={{ mt: 3, mb: 3 }}>
                <Box className="Title" sx={{ pl: 0.3, mt: 0.3, display: "flex", flexDirection: "row" }}>
                    <Box sx={{ display: "flex" }}>📝 전체 투표</Box>
                    <form onSubmit={handleSubmit}>
                        <TextField variant="outlined" sx={{ width: "150px", height: "4px" }} />
                        <button type="submit">Search</button>
                    </form>
                </Box>

                <Box display={"flex"} flexDirection={"column"} flex={"1"}>
                    <Box
                        sx={{
                            display: "flex",
                            flexDirection: "column",
                            flex: 1,
                            overflow: "auto",
                            // maxHeight: "90vh",
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
        )
}
