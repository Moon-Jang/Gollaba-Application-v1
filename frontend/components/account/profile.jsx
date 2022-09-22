import { Avatar, Box, IconButton } from '@mui/material'
import { useRef, useState, useEffect } from 'react'

import TokenEx from './tokenEx'
import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'
import EditIcon from '@mui/icons-material/Edit'
import axios from 'axios'
import ApiGateway from './../../apis/ApiGateway'

export default function Profile() {
    const BACKGROUND_IMAGE =
        'https://cdn.pixabay.com/photo/2013/08/20/15/47/poppies-174276_960_720.jpg'
    const PROFILE_BASIC =
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'

    const photoInput = useRef(null)
    const backgroundInput = useRef(null)
    const [profileImage, setProfileImage] = useState(PROFILE_BASIC)
    const [backgroundImage, setBackgroundImage] = useState(BACKGROUND_IMAGE)

    const handleClick = () => {
        photoInput.current.click()
    }
    const handleBackgroundClick = () => {
        backgroundInput.current.click()
    }
    const handlePhoto = (e) => {
        const photoToAdd = e.target.files

        if (photoToAdd[0]) {
            setProfileImage(photoToAdd[0])
        } else {
            setProfileImage(PROFILE_BASIC)
        }
        const reader = new FileReader()
        reader.onload = () => {
            if (reader.readyState === 2) {
                setProfileImage(reader.result)
                console.log('reader :', reader.result)
            }
        }
        reader.readAsDataURL(photoToAdd[0])
    }
    const handleBackground = (e) => {
        const BackgroundToAdd = e.target.files
        console.log('bgimg :', BackgroundToAdd)
        if (BackgroundToAdd[0]) {
            setBackgroundImage(BackgroundToAdd[0])
            console.log('bgimg[0]', BackgroundToAdd[0])
        } else {
            setBackgroundImage(BACKGROUND_IMAGE)
        }
        const reader = new FileReader()
        console.log('result: ', reader.result)
        reader.onload = () => {
            if (reader.readyState === 2) {
                setProfileImage(reader.result)
            }
        }
    }

    // 끄아아악 아니 프로필 사진은 멀쩡하게 reader.data가 알아서 들어갔것만 왜 배경 사진은 [object File] 이렇게 들어가서 ㅇㅈㄹ인거지 후
    // useState에서 setToken 함수 -> '다음 렌더링때' 변수를 바꿔주는 함수이다. 고로 해당 로직에서 setToken은 useEffect끝난 다음에
    // 일단 유저정보 받아오기
    const [cookies, setCookies, removeCookies] = useCookies([])
    const [token, setToken] = useState(null)
    const [data, setData] = useState(null)
    const showUser = async () => {
        console.log('token : ', token)
        if (!token) return
        try {
            //await ApiGateway.updateNickName(formData, cookies.accessToken);
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
    console.log('token useEffect', token)
    // useEffect(() => {
    //     showUser()
    //     console.log('useEffect data :', data)
    // }, [])
    console.log('data :', data)
    console.log('profile :', data?.profileImageUrl)
    console.log('background : ', data?.backgroundImageUrl)
    // console.log('un', token.un)

    // 질문: 여기서 난 showUser을 화면이 처음 마운트 될 때, 한번만 자동으로 호출하고 싶어서 useEffect를 이용해 넣었음.
    // 근데 같이 useEffect에 들어간 setToken은 정상적으로 작동되는 반면, showUser()은 실행이 안 됨.

    const backgroundStyle = {
        backgroundColor: 'pink',
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
                            src={backgroundImage}
                            alt='bgImg'
                            onClick={handleBackgroundClick}
                        />
                        <input
                            type='file'
                            id='backgroundImageInput'
                            style={{ display: 'none' }}
                            accept='image/jpg image/jpeg image/png'
                            onChange={(e) => {
                                handleBackground(e)
                            }}
                            ref={backgroundInput}
                        />
                        <Avatar
                            src={profileImage}
                            id='profileImage'
                            sx={{
                                width: 215,
                                height: 215,
                                objectFit: 'cover',
                                position: 'absolute',
                                marginTop: 23,
                            }}
                            onClick={handleClick}
                            // ref={photoInput}
                        />
                        <input
                            type='file'
                            id='profileImageInput'
                            style={{ display: 'none' }}
                            accept='image/jpg image/jpeg image/png'
                            onChange={(e) => handlePhoto(e)}
                            ref={photoInput}
                        />
                    </div>
                </div>
                {/* <TokenEx /> */}
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
                            <IconButton aria-label='edit'>
                                <EditIcon style={{ margin: '0 0 3 10' }} />
                            </IconButton>
                        </span>
                        {/* <button onClick={showUser}>회원정보 요청</button> */}
                    </Box>
                </div>
            </Box>
        </>
    )
}
// 닉네임 span으로 처리?
