import React from "react"
import CssBaseline from "@mui/material/CssBaseline"
import Container from "@mui/material/Container"
import { createTheme, ThemeProvider } from "@mui/material/styles"
import { makeStyles } from "@mui/styles"
import ButtonAppBar from "../../components/buttonAppBar"
import FooterNav from "../../components/footerNav"
import VerticalLinearStepper from "../../components/edit/VerticalLinearStepper"
import ApiGateway from "../../apis/ApiGateway"

const theme = createTheme({
    palette: {
        primary: {
            main: "#808080",
        },
    },
})

const handleSubmit = async (event) => {
    event.preventDefault()
    await ApiGateway.updatePoll(pollId, payload, token)
    // 수정은 추후
}

const initPollingItems = [
    {
        description: "",
    },
    {
        description: "",
    },
]

const newPageStyles = makeStyles((theme) => ({
    container: {
        display: "flex",
        flexDirection: "column",
        height: "100vh",
    },
    main: {
        width: "100%",
        height: "100%",
        // display: "flex",
        marginTop: "16px",
        // flexDirection: "column",
        // justifyContent: "center",
    },
    header: {
        height: "56px",
        width: "100%",
    },
    footer: {
        height: "68px",
        width: "100%",
    },
}))

export default function EditPage() {
    if (typeof window !== "object") return <></>

    const classes = newPageStyles()

    return (
        <ThemeProvider theme={theme}>
            <Container className={classes.container} component="div" maxWidth="sm">
                <CssBaseline />
                <div className={classes.header}>
                    <ButtonAppBar titletext={"New"} />
                </div>
                <div className={classes.main}>
                    <VerticalLinearStepper />
                </div>
                <div className={classes.footer}>
                    <FooterNav />
                </div>
            </Container>
        </ThemeProvider>
    )
}
