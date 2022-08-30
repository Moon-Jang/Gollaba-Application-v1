import { Button } from '@mui/material'
import Link from 'next/link'

export default function MyPolls() {
    return (
        <>
            <div>내 투표 확인 기능 컴포넌트</div>
            <div>새로운 화면 구현할 것</div>
            <Link href='/내투표'>
                <Button
                    color='primary'
                    type='submit'
                    variant='outlined'
                    fullWidth
                    style={{ verticalAlign: 'middle', color: '#000000' }}
                    sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                >
                    내 투표
                </Button>
            </Link>
        </>
    )
}
