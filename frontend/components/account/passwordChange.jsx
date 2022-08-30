import { Button } from '@mui/material'
import Link from 'next/link'

export default function PasswordChange() {
    return (
        <>
            <div>비밀번호 변경 기능 컴포넌트</div>
            <div>현재 구현 기능 : 비밀번호 변경 화면 구현</div>
            <Link href='/pwchange'>
                <Button
                    color='primary'
                    type='submit'
                    variant='outlined'
                    fullWidth
                    style={{ verticalAlign: 'middle', color: '#000000' }}
                    sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                >
                    비밀번호 변경
                </Button>
            </Link>
        </>
    )
}
