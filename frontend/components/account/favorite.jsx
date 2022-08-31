import { Button } from '@mui/material'
import Link from 'next/link'

export default function Favorite() {
    return (
        <>
            <Link href='/account/favorites'>
                <Button
                    color='primary'
                    type='submit'
                    variant='outlined'
                    fullWidth
                    style={{ verticalAlign: 'middle', color: '#000000' }}
                    sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                >
                    즐겨찾기
                </Button>
            </Link>
        </>
    )
}
// 현재 상태 : 즐겨찾기 화면 구현, 맵핑 기능은 후순위로 미룸
