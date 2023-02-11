import React, { useState, useEffect } from "react"
import CssBaseline from "@mui/material/CssBaseline"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import Box from "@mui/material/Box"
import { createTheme, ThemeProvider } from "@mui/material/styles"
import defaultImage from "../../public/defaultImage.png"

export default function Options(props) {
    return (
        <Box
            sx={{
                display: "flex",
                height: "85%",
                flex: 1,
                backgroundColor: "rgb(230,230,230)",
                margin: 0.2,
                marginTop: 1,
                marginBottom: 0,
                alignItems: "flex-end",
                backgroundSize: "cover",
                backgroundPosition: "50% 50%",
                backgroundImage:
                    props.data.imageUrl !== null ? `url(${props.data.imageUrl})` : `url(${defaultImage.src})`,
            }}
        ></Box>
    )
}
