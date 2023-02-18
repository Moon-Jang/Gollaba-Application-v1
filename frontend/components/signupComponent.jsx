import { Avatar, Box, IconButton, TextField, Button, CssBaseline, ThemeProvider } from "@mui/material"
import { useRef, useState, useEffect } from "react"
import jwt from "jsonwebtoken"
import ApiGateway from "../apis/ApiGateway"
import CommonValidator from "./../utils/CommonValidator"
import theme from "./../src/theme"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import { useRouter } from "next/router"

export default function SignupComponent() {
    const router = useRouter()

    //Profile, Background Image 변경

    const PROFILE_BASIC = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
    const [profileImage, setProfileImage] = useState(PROFILE_BASIC)
    const photoInput = useRef(null)
    const imagePayload = useRef(null)

    const handleClick = () => {
        photoInput.current.click()
    }

    const changeProfile = async (e) => {
        const profileImageSelected = e.target.files[0]
        if (e.target.files[0]) {
            // setProfileImage(e.target.files[0])
            imagePayload.current = e.target.files[0]
        } else {
            setProfileImage(PROFILE_BASIC)

            return
        }
        const reader = new FileReader()
        reader.onload = () => {
            if (reader.readyState === 2) {
                setProfileImage(reader.result)
            }
        }
        reader.readAsDataURL(e.target.files[0])
    }

    const [id, setId] = useState("")
    const [helperTextId, setHelperTextId] = useState("")
    const [isErrorId, setIsErrorId] = useState(false)

    const [nickName, setNickName] = useState("")
    const [helperTextNickName, setHelperTextNickName] = useState("")
    const [isErrorNickName, setIsErrorNickName] = useState(false)

    const [password, setPassword] = useState("")
    const [helperTextPassword, setHelperTextPassword] = useState("")
    const [isErrorPassword, setIsErrorPassword] = useState(false)

    const [helperTextPasswordCheck, setHelperTextPasswordCheck] = useState("")
    const [isErrorPasswordCheck, setIsErrorPasswordCheck] = useState(false)

    const onChangeIdHandler = (e) => {
        if (e.target.name === "id" && !CommonValidator.validate("id", e.target.value)) {
            setIsErrorId(true)
            setHelperTextId("ID는 8~32자의 숫자, 문자로 구성되어야 합니다.")
            return
        }
        setIsErrorId(false)
        setHelperTextId("")
        setId(e.target.value)
    }

    const onChangeNickNameHandler = (e) => {
        if (e.target.name === "nickName" && !CommonValidator.validate("nickName", e.target.value)) {
            setIsErrorNickName(true)
            setHelperTextNickName("닉네임은 2~20자의 숫자, 문자로 구성되어야 합니다.")
            return
        }
        setIsErrorNickName(false)
        setHelperTextNickName("")
        setNickName(e.target.value)
    }

    const onChangePasswordHandler = (e) => {
        if (e.target.name === "password" && !CommonValidator.validate("password", e.target.value)) {
            setIsErrorPassword(true)
            setHelperTextPassword("비밀번호는 8~24자의 숫자, 문자, 특수문자가 모두 포함되어야 합니다.")
            return
        }
        setIsErrorPassword(false)
        setHelperTextPassword("")
        setPassword(e.target.value)
    }

    const onChangePasswordCheckHandler = (e) => {
        if (e.target.value !== password) {
            setIsErrorPasswordCheck(true)
            setHelperTextPasswordCheck("비밀번호가 일치하지 않습니다.")
            return
        }
        setIsErrorPasswordCheck(false)
        setHelperTextPasswordCheck("")
    }

    const signupHandler = async (e) => {
        e.preventDefault()

        try {
            const formData = new FormData()
            formData.append("id", id)
            formData.append("nickName", nickName)
            formData.append("password", password)
            formData.append("profileImage", imagePayload.current)

            const signup = await ApiGateway.signupForm(formData)
        } catch (e) {}
        router.push("/login")
    }

    return (
        <>
            <ThemeProvider theme={theme}>
                <Container component="main" maxWidth="xs">
                    <CssBaseline />

                    <Box
                        component="form"
                        onSubmit={signupHandler}
                        sx={{
                            display: "flex",
                            flexDirection: "column",
                            alignItems: "center",
                            justifyContent: "center",
                        }}
                    >
                        <div style={{ marginTop: 80 }}>
                            <Typography component="h1" varient="h1" sx={{ fontWeight: "bold", fontSize: 38 }}>
                                회원 가입
                            </Typography>
                        </div>
                        <div>
                            <Avatar
                                src={profileImage}
                                id="profileImage"
                                sx={{
                                    width: 215,
                                    height: 215,
                                    objectFit: "cover",
                                    // position: "absolute",
                                    marginTop: 5,
                                    marginBottom: 2,
                                }}
                                onClick={handleClick}
                            />
                            <input
                                type="file"
                                id="profileImageInput"
                                style={{ display: "none" }}
                                accept="image/jpg image/jpeg image/png"
                                onChange={(e) => changeProfile(e)}
                                ref={photoInput}
                            />
                        </div>

                        <div>
                            <span>프로필 사진을 변경해 주세요</span>
                        </div>

                        <div style={{ marginTop: 20 }}>
                            <TextField
                                required
                                fullWidth
                                margin="dense"
                                variant="standard"
                                id="id"
                                name="id"
                                label="아이디"
                                helperText={helperTextId}
                                error={isErrorId ? true : false}
                                onChange={onChangeIdHandler}
                            />
                            <TextField
                                required
                                fullWidth
                                margin="dense"
                                variant="standard"
                                id="nickName"
                                name="nickName"
                                label="닉네임"
                                helperText={helperTextNickName}
                                error={isErrorNickName ? true : false}
                                onChange={onChangeNickNameHandler}
                            />
                            <TextField
                                required
                                fullWidth
                                margin="dense"
                                variant="standard"
                                id="password"
                                name="password"
                                type="password"
                                label="비밀번호"
                                helperText={helperTextPassword}
                                error={isErrorPassword ? true : false}
                                onChange={onChangePasswordHandler}
                            />
                            <TextField
                                required
                                fullWidth
                                margin="dense"
                                variant="standard"
                                id="passwordCheck"
                                name="passwordCheck"
                                type="password"
                                label="비밀번호 확인"
                                helperText={helperTextPasswordCheck}
                                error={isErrorPasswordCheck ? true : false}
                                onChange={onChangePasswordCheckHandler}
                            />
                            <Button
                                fullWidth
                                type="submit"
                                color="primary"
                                variant="outlined"
                                style={{ verticalAlign: "middle", color: "#000000" }}
                                sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                            >
                                회원가입
                            </Button>
                        </div>
                    </Box>
                </Container>
            </ThemeProvider>
        </>
    )
}
