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
import theme from '../src/theme'
import React, { useState } from 'react'
import CommonValidator from './../utils/CommonValidator'
import { useRouter } from 'next/router'

// const AccountTheme = createTheme(theme)
export default function Pwchange() {
    const router = useRouter()
    const [password, setPassword] = useState('')
    const [isErrorPassword, setIsErrorPassword] = useState(false)
    const [helperTextPassword, setHelperTextPassword] = useState('')

    const [isErrorPasswordCheck, setIsErrorPasswordCheck] = useState(false)
    const [helperTextPasswordCheck, setHelperTextPasswordCheck] = useState('')

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

        const payload = {
            password: input.get('newPassword'),
        }

        let response
        try {
            response = await axios.post(
                'https://dev.api.gollaba.net/v1/pwchange',
                payload
            )
            router.push('/')
        } catch (e) {
            response = e.response
            alert(response.data.error.message)
        } finally {
            return response
        }
    }

    const handleChangePassword = (event) => {
        setPassword(event.target.value)
        if (
            event.target.name === 'password' &&
            !CommonValidator.validate('password', event.target.value)
        ) {
            setIsErrorPassword(true)
            setHelperTextPassword(
                '비밀번호는 8~24자의 숫자, 문자, 특수문자가 모두 포함되어야 합니다.'
            )
            return
        }
        setIsErrorPassword(false)
        setHelperTextPassword('')
    }

    const handleChangePasswordCheck = (event) => {
        if (event.target.value !== password) {
            setIsErrorPasswordCheck(true)
            setHelperTextPasswordCheck(
                '비밀번호와 비밀번호 확인이 서로 다릅니다.'
            )
            return
        }
        setIsErrorPasswordCheck(false)
        setHelperTextPasswordCheck('')
    }

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

                        {/* <Avatar
                            src='../public/camera_icon.png'
                            sx={{ width: 85, height: 85 }}
                        /> */}

                        <Box
                            component='form'
                            onSubmit={handleSubmit}
                            sx={{ mt: 6 }}
                        >
                            <TextField
                                required
                                fullWidth
                                margin='dense'
                                name='currentPassword'
                                variant='standard'
                                label='현재 비밀번호'
                                type='password'
                                id='currentPassword'
                                helperText={helperTextPassword}
                                error={isErrorPassword ? true : false}
                                onChange={handleChangePassword}
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
                                helperText={helperTextPassword}
                                error={isErrorPassword ? true : false}
                                onChange={handleChangePassword}
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
                                helperText={helperTextPasswordCheck}
                                error={isErrorPasswordCheck ? true : false}
                                onChange={handleChangePasswordCheck}
                            />

                            <Button
                                color='primary'
                                type='submit'
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
