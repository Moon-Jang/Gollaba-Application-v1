import { Button } from '@mui/material'
import { Link } from 'react-router-dom'

export default function Logout() {
    return (
        <>
            <div>로그아웃 기능 컴포넌트</div>
            <Link href='/index'>
                <Button
                    color='primary'
                    type='submit'
                    variant='outlined'
                    fullWidth
                    style={{ verticalAlign: 'middle', color: '#000000' }}
                    sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                >
                    로그아웃
                </Button>
            </Link>
        </>
    )
}
