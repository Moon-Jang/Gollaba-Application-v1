import * as React from "react"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import { useState } from "react"
import { useEffect } from "react"
import { Avatar, Box, IconButton, TextField, Button, CssBaseline, ThemeProvider } from "@mui/material"
import { useRouter } from "next/router"
import { useRef } from "react"
import ApiGateway from "../../apis/ApiGateway"
import { set } from "date-fns"

export default function SignUp() {
    const router = useRouter()
    const [loadedPage, setLoadedPage] = useState(false)
    const [name, setName] = useState("")
    const emailRef = useRef("")
    const providerIdRef = useRef("")
    const providerTypeRef = useRef("")
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

    const signup = async (e) => {
        /*
        const formData = new FormData()
        formData.append("email", emailRef.current)
        formData.append("nickName", name)
        formData.append("providerId", providerIdRef.current)
        formData.append("providerType", providerTypeRef.current)
        formData.append("providerImageUrl", imagePayload.current)
        const response = await ApiGateway.signupForm(formData)
        */

        const payload = {
            email: emailRef.current,
            name: name,
            profileImageUrl: profileImage,
            providerType: providerTypeRef.current,
            providerId: providerIdRef.current,
        }

        console.log("페이로드>", payload)
        const response = await ApiGateway.signupForm(payload)

        if (response?.error === true) {
            alert(response.message)
            return
        }

        router.push("/login")
    }

    useEffect(() => {
        if (!router.query || !Object.keys(router.query).length) return

        const { name, email, providerId, providerType, profileImageUrl } = router.query
        console.log("check>>", name, email, providerId, providerType, profileImageUrl)
        setLoadedPage(true)
        setName(name)
        emailRef.current = email
        providerIdRef.current = providerId
        providerTypeRef.current = providerType
        setProfileImage(profileImageUrl)
    }, [router.query])

    if (!loadedPage) return <></>

    return (
        <Container maxWidth="sm">
            <Box
                sx={{ my: 4, display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center" }}
            >
                <div style={{ marginTop: 80 }}>
                    <Typography component="h1" varient="h1" sx={{ fontWeight: "bold", fontSize: 38 }}>
                        회원가입
                    </Typography>
                </div>
                <div>
                    <Avatar
                        src={profileImage}
                        id="profileImage"
                        sx={{
                            width: 120,
                            height: 120,
                            objectFit: "cover",
                            // position: "absolute",
                            marginTop: 2,
                            marginBottom: 10,
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
                <TextField
                    disabled
                    fullWidth
                    margin="dense"
                    variant="standard"
                    id="email"
                    name="email"
                    value={emailRef.current}
                    label="이메일"
                    // helperText={helperTextId}
                    // error={isErrorId ? true : false}
                    onChange={() => {}}
                />
                <TextField
                    required
                    fullWidth
                    margin="dense"
                    variant="standard"
                    id="nickname"
                    name="nickname"
                    value={name}
                    label="닉네임"
                    // helperText={helperTextId}
                    // error={isErrorId ? true : false}
                    onChange={(e) => setName(e.target.value)}
                />
            </Box>
            <Button
                fullWidth
                type="submit"
                color="primary"
                variant="outlined"
                style={{ verticalAlign: "middle", color: "#000000" }}
                sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                onClick={signup}
            >
                회원가입
            </Button>
        </Container>
    )
}
