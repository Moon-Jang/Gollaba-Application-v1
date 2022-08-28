import { Button } from '@mui/material'
import { Link } from 'react-router-dom'

export default function PasswordChange() {
    return (
        <>
            <div>비밀번호 변경 기능 컴포넌트</div>
            {/* <Link href='/*'> */}
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
            {/* </Link> */}
        </>
    )
}
