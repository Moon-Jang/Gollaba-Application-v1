import { Button } from '@mui/material'
import Link from 'next/link'

export default function MyPoll() {
    return (
        <>
            <Link href='/account/mypolls'>
                <Button
                    color='primary'
                    type='submit'
                    variant='text'
                    fullWidth
                    style={{ verticalAlign: 'middle', color: '#000000' }}
                    // sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                >
                    내 투표
                </Button>
            </Link>
        </>
    )
}
//현재 상태 : 내 투표 화면 구현, 맵핑은 아직 (후순위)
