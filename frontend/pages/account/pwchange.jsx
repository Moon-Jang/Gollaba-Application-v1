import {
    Box,
    Button,
    Container,
    CssBaseline,
    Divider,
    TextField,
    ThemeProvider,
    Typography,
} from '@mui/material'
import theme from '../../src/theme'
import React, { useState } from 'react'
import CommonValidator from '../../utils/CommonValidator'
import { useRouter } from 'next/router'
import { useCookies } from 'react-cookie'
import ApiGateway from '../../apis/ApiGateway'

// const AccountTheme = createTheme(theme)
export default function Pwchange() {
    const router = useRouter()
    const [cookies, setCookies, removeCookies] = useCookies([])
    const [data, setData] = useState(null)
    const [currentPassword, setCurrentPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const [newPasswordCheck, setNewPasswordCheck] = useState('')

    const [password, setPassword] = useState('')
    const [isErrorPassword, setIsErrorPassword] = useState(false)
    const [helperTextPassword, setHelperTextPassword] = useState('')
    const [isErrorPasswordCheck, setIsErrorPasswordCheck] = useState(false)
    const [helperTextPasswordCheck, setHelperTextPasswordCheck] = useState('')

    const changePassword = async () => {
        try {
            const formData = new FormData()
            formData.append('currentPassword', currentPassword)
            formData.append('newPassword', newPassword)
            formData.append('updateType', 'PASSWORD')
            for (const keyValue of formData)
                console.log('keyValue : ', keyValue)
            const passwordChange = await ApiGateway.updateForm(
                formData,
                cookies.accessToken
            )
            setData(passwordChange)
            console.log(currentPassword)
            console.log(newPassword)
            console.log('passwordChange : ', passwordChange)
            console.log('password change success')
            // window.location.href = '/'
        } catch (e) {
            console.log(e)
        }
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        const input = new FormData(event.currentTarget)

        if (
            !CommonValidator.validate(
                'currentPassword',
                input.get('currentPassword')
            )
        ) {
            alert(CommonValidator.password.message)
            return
        }

        if (
            !CommonValidator.validate('newPassword', input.get('newPassword'))
        ) {
            alert(CommonValidator.password.message)
            return
        }

        if (input.get('newPassword') !== input.get('newPasswordCheck')) {
            alert('새로운 비밀번호와 새로운 비밀번호 확인이 서로 다릅니다.')
            return
        }
    }
    const onChangeCurrentPasswordHandler = (e) => {
        setCurrentPassword(e.target.value)
    }
    const onChangeNewPasswordHandler = (e) => {
        setNewPassword(e.target.value)
    }
    const onChangeNewPasswordCheckHandler = (e) => {
        setNewPasswordCheck(e.target.value)
        if (e.target.value !== newPassword) {
            setIsErrorPasswordCheck(true)
            setHelperTextPasswordCheck(
                '비밀번호와 비밀번호 확인이 서로 다릅니다.'
            )
            return
        }
        setIsErrorPasswordCheck(false)
        setHelperTextPasswordCheck('')
    }

    // const handleChangePassword = (event) => {
    //     setPassword(event.target.value)
    //     if (
    //         event.target.name === 'password' &&
    //         !CommonValidator.validate('password', event.target.value)
    //     ) {
    //         setIsErrorPassword(true)
    //         setHelperTextPassword(
    //             '비밀번호는 8~24자의 숫자, 문자, 특수문자가 모두 포함되어야 합니다.'
    //         )
    //         return
    //     }
    //     setIsErrorPassword(false)
    //     setHelperTextPassword('')
    // }

    // const handleChangePasswordCheck = (event) => {
    //     if (event.target.value !== password) {
    //         setIsErrorPasswordCheck(true)
    //         setHelperTextPasswordCheck(
    //             '비밀번호와 비밀번호 확인이 서로 다릅니다.'
    //         )
    //         return
    //     }
    //     setIsErrorPasswordCheck(false)
    //     setHelperTextPasswordCheck('')
    // }

    return (
        <>
            <ThemeProvider theme={theme}>
                <Container component='main' maxWidth='xs'>
                    <CssBaseline />
                    <Box
                        sx={{
                            marginTop: 8,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }}
                    >
                        <Typography
                            component='h1'
                            variant='h3'
                            sx={{ mt: 2.5, mb: 7.5, fontWeight: 'bold' }}
                        >
                            비밀번호 변경
                        </Typography>
                        <Box>
                            <TextField
                                required
                                fullWidth
                                margin='dense'
                                name='currentPassword'
                                variant='standard'
                                label='현재 비밀번호'
                                type='password'
                                id='currentPassword'
                                value={currentPassword}
                                helperText={helperTextPassword}
                                error={isErrorPassword ? true : false}
                                onChange={onChangeCurrentPasswordHandler}
                            />
                            <Divider />
                            <TextField
                                required
                                fullWidth
                                margin='dense'
                                name='newPassword'
                                variant='standard'
                                label='새로운 비밀번호'
                                type='password'
                                id='newPassword'
                                value={newPassword}
                                helperText={helperTextPassword}
                                error={isErrorPassword ? true : false}
                                onChange={onChangeNewPasswordHandler}
                            />

                            <TextField
                                required
                                fullWidth
                                margin='dense'
                                name='newPasswordCheck'
                                variant='standard'
                                label='새로운 비밀번호 확인'
                                type='password'
                                id='newPasswordCheck'
                                value={newPasswordCheck}
                                helperText={helperTextPasswordCheck}
                                error={isErrorPasswordCheck ? true : false}
                                onChange={onChangeNewPasswordCheckHandler}
                            />

                            <Button
                                color='primary'
                                onClick={changePassword}
                                variant='outlined'
                                fullWidth
                                style={{
                                    verticalAlign: 'middle',
                                    color: '#000000',
                                }}
                                sx={{
                                    mt: 4.5,
                                    mb: 2,
                                    borderRadius: 12.5,
                                    boxShadow: 4,
                                }}
                            >
                                비밀번호 변경
                            </Button>
                        </Box>
                    </Box>
                </Container>
            </ThemeProvider>
        </>
    )
}
