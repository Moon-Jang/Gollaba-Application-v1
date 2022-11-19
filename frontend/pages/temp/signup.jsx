import * as React from 'react';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import { useState } from 'react';
import { useEffect } from 'react';
import { Button, TextField } from '@mui/material';
import { useRouter } from 'next/router';
import { useRef } from 'react';
import ApiGateway from '../../apis/ApiGateway';

export default function SignUp() {
  const router = useRouter();
  const [loadedPage, setLoadedPage] = useState(false);
  const [name, setName] = useState("");
  const emailRef = useRef("");
  const providerIdRef = useRef("");
  const providerTypeRef = useRef("");
  
  const signup = async (e) => {
    const formData = new FormData()
    formData.append("email", emailRef.current)
    formData.append("nickName", name)
    formData.append("providerId", providerIdRef.current)
    formData.append("providerType", providerTypeRef.current)
    for (const keyValue of formData) console.log("keyValue : ", keyValue)
    const response = await ApiGateway.signupForm(formData)

    if (response.error === true) {
      alert(response.message)
      return;
    }

    router.push("/login")
  }

  useEffect(() => {
    if (!router.query || !Object.keys(router.query).length) return

    
    const {name, email, providerId, providerType} = router.query

    setLoadedPage(true)
    setName(name)
    emailRef.current = email
    providerIdRef.current = providerId
    providerTypeRef.current = providerType
    
  }, [router.query])

  if (!loadedPage) return <></>;

  return (
    <Container maxWidth="sm">
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          임시 회원가입
        </Typography>
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
            label="이름"
            // helperText={helperTextId}
            // error={isErrorId ? true : false}
            onChange={e => setName(e.target.value)}
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
  );
}
