import React, { useState } from "react"
import Router from "next/router"
import Button from "@mui/material/Button"
import CssBaseline from "@mui/material/CssBaseline"
import Divider from "@mui/material/Divider"
import TextField from "@mui/material/TextField"
import Container from "@mui/material/Container"
import Box from "@mui/material/Box"

import { createTheme, ThemeProvider } from "@mui/material/styles"
import axios from "axios"
import { useCookies } from "react-cookie"

const theme = createTheme({
    palette: {
        primary: {
            main: "#808080",
        },
    },
})

export default function socialBtn() {
    return (
        <Button
            color="primary"
            type="submit"
            variant="outlined"
            fullWidth
            style={{ verticalAlign: "middle", color: "#000000" }}
            sx={{ mt: 8, mb: 1, borderRadius: 12.5, boxShadow: 4 }}
        >
            Login With Facebook
        </Button>
    )
}
