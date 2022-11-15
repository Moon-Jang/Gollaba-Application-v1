import { Box, Container, CssBaseline, Divider, ThemeProvider } from "@mui/material"
import AccountMenu from "../components/account/accountMenu"
import Profile from "../components/account/profile"
import ButtonAppBar from "../components/buttonAppBar"
import FooterNav from "../components/footerNav"
import theme from "../src/theme"
import React, { useState } from "react"

export default function Accounts() {
    return (
        <>
            <ThemeProvider theme={theme}>
                <Container component="main" maxWidth="xs">
                    <CssBaseline />
                    <Box
                        sx={{
                            marginTop: 7,
                            marginBottom: 7,
                            display: "flex",
                            flexDirection: "column",
                            alignItems: "left",
                            justifyContent: "center",
                        }}
                    >
                        <div className="header">
                            <ButtonAppBar titletext={"Accounts"} />
                        </div>

                        <div className="body">
                            <Profile />
                            {/* <Divider /> */}
                            <AccountMenu />
                        </div>

                        <div className="footer">
                            <FooterNav />
                        </div>
                    </Box>
                </Container>
            </ThemeProvider>
        </>
    )
}
