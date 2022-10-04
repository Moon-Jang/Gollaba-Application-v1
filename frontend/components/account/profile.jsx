import {
    Avatar,
    Box,
    IconButton,
    TextField,
    Button,
    CssBaseline,
} from '@mui/material'
import { useRef, useState, useEffect } from 'react'
import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'
import EditIcon from '@mui/icons-material/Edit'
import DoneOutlineIcon from '@mui/icons-material/DoneOutline'
import ApiGateway from './../../apis/ApiGateway'

export default function Profile() {
    // UserInfo API통해 페이지에 가져옴
    const [cookies, setCookies, removeCookies] = useCookies([])
    const [token, setToken] = useState(null)
    const [data, setData] = useState(null)
    const showUser = async () => {
        console.log('token : ', token)
        if (!token) return
        try {
            const userInfo = await ApiGateway.showUser(
                token.id,
                cookies.accessToken
            )
            setData(userInfo)
            console.log('showUser :', userInfo)
        } catch (e) {
            console.log(e)
        }
    }
    useEffect(() => {
        setToken(jwt.decode(cookies.accessToken))
    }, [cookies])
    useEffect(() => {
        showUser()
    }, [token])

    //Profile, Background Image 변경
    const BACKGROUND_BASIC =
        'https://cdn.pixabay.com/photo/2017/07/08/11/33/white-2484120_960_720.png'
    const PROFILE_BASIC =
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'
    const profileImageSrc = () => {
        if (data?.profileImageUrl == null) return PROFILE_BASIC
        return data?.profileImageUrl
    }
    const backgroundImageSrc = () => {
        if (data?.backgroundImageUrl == null) return BACKGROUND_BASIC
        return data?.backgroundImageUrl
    }
    const photoInput = useRef(null)
    const backgroundInput = useRef(null)
    const handleClick = () => {
        photoInput.current.click()
    }
    const handleBackgroundClick = () => {
        backgroundInput.current.click()
    }

    // formData로 이미지 파일 업데이트 하기
    const changeProfile = async (e) => {
        if (!token) return
        try {
            const photoToAdd = e.target.files[0]
            console.log('photoToAdd : ', photoToAdd)
            const formData = new FormData()
            formData.append('profileImage', photoToAdd)
            formData.append('updateType', 'PROFILE_IMAGE')
            for (const keyValue of formData)
                console.log('keyValue : ', keyValue)
            const profileChange = await ApiGateway.updateForm(
                formData,
                cookies.accessToken
            )
            setData(profileChange)
            console.log('profileChange : ', profileChange)
            // location.reload()
        } catch (e) {
            console.log(e)
        }
    }
    const changeBackground = async (e) => {
        if (!token) return
        try {
            const photoToAdd = e.target.files[0]
            console.log('photoToAdd : ', photoToAdd)
            const formData = new FormData()
            formData.append('backgroundImage', photoToAdd)
            formData.append('updateType', 'BACKGROUND_IMAGE')
            for (const keyValue of formData)
                console.log('keyValue : ', keyValue)
            const backgroundChange = await ApiGateway.updateForm(
                formData,
                cookies.accessToken
            )
            setData(backgroundChange)
            console.log('backgroundChange : ', backgroundChange)
            location.reload()
        } catch (e) {
            console.log(e)
        }
    }

    // 닉네임 변경
    const [nickName, setNickName] = useState('')
    const [visible, setVisible] = useState(false)
    const onChangeNicknameHandler = (e) => {
        setNickName(e.target.value)
    }
    const changeNickname = async () => {
        if (!token) return
        try {
            const formData = new FormData()
            formData.append('nickName', nickName)
            formData.append('updateType', 'NICKNAME')
            const nickChange = await ApiGateway.updateForm(
                formData,
                cookies.accessToken
            )
            setData(nickChange)
            console.log('nickChange :', nickChange)
            for (const keyValue of formData) {
                console.log('formData keyValue :', keyValue)
            }
            location.reload()
        } catch (e) {
            console.log(e)
        }
    }

    // 배경화면 스타일 CSS
    const backgroundStyle = {
        backgroundColor: '#ffffff',
        overflow: 'hidden',
        display: 'flex',
        flexDirection: 'column',
        height: 200,
        width: 390,
        padding: 0,
        marginTop: 0,
        alignItems: 'center',
        justifyContent: 'center',
    }
    const imageStyle = {
        width: 390,
        height: 200,
        // objectFit: 'none',
        objectPosition: 'center',
        overflow: 'hidden',
        display: 'flex',
        justifyContent: 'center',
    }

    return (
        <>
            <Box
                sx={{
                    marginTop: 0,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
            >
                <div>
                    <div style={backgroundStyle}>
                        <img
                            style={imageStyle}
                            src={backgroundImageSrc()}
                            alt='bgImg'
                            onClick={handleBackgroundClick}
                        />
                        <input
                            type='file'
                            id='backgroundImageInput'
                            style={{ display: 'none' }}
                            accept='image/jpg image/jpeg image/png'
                            onChange={(e) => {
                                changeBackground(e)
                            }}
                            ref={backgroundInput}
                        />
                        <Avatar
                            src={profileImageSrc()}
                            id='profileImage'
                            sx={{
                                width: 215,
                                height: 215,
                                objectFit: 'cover',
                                position: 'absolute',
                                marginTop: 23,
                            }}
                            onClick={handleClick}
                        />
                        <input
                            type='file'
                            id='profileImageInput'
                            style={{ display: 'none' }}
                            accept='image/jpg image/jpeg image/png'
                            onChange={(e) => changeProfile(e)}
                            ref={photoInput}
                        />
                    </div>
                </div>

                <div style={{ fontSize: '24px' }}>
                    <Box
                        sx={{
                            marginTop: 16,
                            marginBottom: 5,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }}
                    >
                        <span>
                            {data?.nickName}
                            <IconButton
                                aria-label='edit'
                                onClick={() => {
                                    setVisible(!visible)
                                }}
                            >
                                <EditIcon style={{ margin: '0 0 3 10' }} />
                            </IconButton>
                        </span>

                        {visible && (
                            <div>
                                <CssBaseline />
                                <TextField
                                    sx={{ mt: 1.5 }}
                                    required
                                    margin='dense'
                                    name='nickNameChange'
                                    variant='standard'
                                    type='text'
                                    id='nickNameChange'
                                    label='닉네임 변경'
                                    placeholder='변경할 닉네임은?'
                                    onChange={onChangeNicknameHandler}
                                />
                                <IconButton
                                    aria-label='done'
                                    onClick={changeNickname}
                                >
                                    <DoneOutlineIcon
                                        style={{ margin: '7 0 0 0' }}
                                    />
                                </IconButton>
                            </div>
                        )}
                    </Box>
                </div>
            </Box>
        </>
    )
}
