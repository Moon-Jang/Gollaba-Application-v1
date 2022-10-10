import React, { useState, useEffect, useRef } from "react"
import { useRouter } from "next/router"
import Avatar from "@mui/material/Avatar"
import Button from "@mui/material/Button"
import CssBaseline from "@mui/material/CssBaseline"
import TextField from "@mui/material/TextField"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import Box from "@mui/material/Box"
import { createTheme, ThemeProvider } from "@mui/material/styles"
import axios from "axios"
import CommonValidator from "../utils/CommonValidator"

const theme = createTheme({
    palette: {
        primary: {
            main: "#808080",
        },
    },
})

export default function signup() {
    const [res, setRes] = useState(null)

    const [isErrorId, setIsErrorId] = useState(false)
    const [helperTextId, setHelperTextId] = useState("")

    const [password, setPassword] = useState("")
    const [isErrorPassword, setIsErrorPassword] = useState(false)
    const [helperTextPassword, setHelperTextPassword] = useState("")

    const [isErrorNickname, setIsErrorNickname] = useState(false)
    const [helperTextNickname, setHelperTextNickname] = useState("")

    const [isErrorPasswordCheck, setIsErrorPasswordCheck] = useState(false)
    const [helperTextPasswordCheck, setHelperTextPasswordCheck] = useState("")

    const router = useRouter()

    const handleSubmit = async event => {
        event.preventDefault()
        const input = new FormData(event.currentTarget)

        if (!CommonValidator.validate("id", input.get("id"))) {
            alert(CommonValidator.id.message)
            return
        }

        if (!CommonValidator.validate("password", input.get("password"))) {
            alert(CommonValidator.password.message)
            return
        }

        if (!CommonValidator.validate("nickName", input.get("nickName"))) {
            alert(CommonValidator.nickName.message)
            return
        }

        if (input.get("password") !== input.get("passwordCheck")) {
            alert("비밀번호와 비밀번호 확인이 서로 다릅니다.")
            return
        }

        const payload = {
            id: input.get("id"),
            password: input.get("password"),
            nickName: input.get("nickName"),
        }

        let response
        try {
            response = await axios.post("https://dev.api.gollaba.net/v1/signup", payload)
            router.push("/login")
        } catch (e) {
            response = e.response
            alert(response.data.error.message)
        } finally {
            return response
        }
    }

    const handleChangeId = event => {
        if (event.target.name === "id" && !CommonValidator.validate("id", event.target.value)) {
            setIsErrorId(true)
            setHelperTextId("ID는 8~32자의 숫자, 문자로 구성되어야 합니다.")
            return
        }
        setIsErrorId(false)
        setHelperTextId("")
    }

    const handleChangeNickName = event => {
        if (event.target.name === "nickName" && !CommonValidator.validate("nickName", event.target.value)) {
            setIsErrorNickname(true)
            setHelperTextNickname("닉네임은 2~20자의 숫자, 문자로 구성되어야 합니다.")
            return
        }
        setIsErrorNickname(false)
        setHelperTextNickname("")
    }

    const handleChangePassword = event => {
        setPassword(event.target.value)
        if (event.target.name === "password" && !CommonValidator.validate("password", event.target.value)) {
            setIsErrorPassword(true)
            setHelperTextPassword("비밀번호는 8~24자의 숫자, 문자, 특수문자가 모두 포함되어야 합니다.")
            return
        }
        setIsErrorPassword(false)
        setHelperTextPassword("")
    }

    const handleChangePasswordCheck = event => {
        if (event.target.value !== password) {
            setIsErrorPasswordCheck(true)
            setHelperTextPasswordCheck("비밀번호와 비밀번호 확인이 서로 다릅니다.")
            return
        }
        setIsErrorPasswordCheck(false)
        setHelperTextPasswordCheck("")
    }

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 8,
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "center",
                    }}
                >
                    {/* <Typography component="h1" variant="h3" sx={{ mt: 2.5, mb: 7.5, fontWeight: "bold" }}>
                        Profile
                    </Typography>

                    <Avatar src="../public/camera_icon.png" sx={{ width: 85, height: 85 }} /> */}

                    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 6 }}>
                        <TextField
                            margin="dense"
                            required
                            fullWidth
                            id="id"
                            label="아이디"
                            name="id"
                            variant="standard"
                            helperText={helperTextId}
                            error={isErrorId ? true : false}
                            onChange={handleChangeId}
                        />

                        <TextField
                            required
                            margin="dense"
                            fullWidth
                            name="nickName"
                            variant="standard"
                            label="닉네임"
                            id="nickName"
                            helperText={helperTextNickname}
                            error={isErrorNickname ? true : false}
                            onChange={handleChangeNickName}
                        />

                        <TextField
                            required
                            fullWidth
                            margin="dense"
                            name="password"
                            variant="standard"
                            label="비밀번호"
                            type="password"
                            id="password"
                            helperText={helperTextPassword}
                            error={isErrorPassword ? true : false}
                            onChange={handleChangePassword}
                        />

                        <TextField
                            required
                            fullWidth
                            margin="dense"
                            name="passwordCheck"
                            variant="standard"
                            label="비밀번호 확인"
                            type="password"
                            id="passwordCheck"
                            helperText={helperTextPasswordCheck}
                            error={isErrorPasswordCheck ? true : false}
                            onChange={handleChangePasswordCheck}
                        />

                        <Button
                            color="primary"
                            type="submit"
                            variant="outlined"
                            fullWidth
                            style={{ verticalAlign: "middle", color: "#000000" }}
                            sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                        >
                            회원가입
                        </Button>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    )
}
