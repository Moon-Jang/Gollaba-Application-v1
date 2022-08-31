import { Button } from '@mui/material'
import Link from 'next/link'
import { useCookies } from 'react-cookie'

export default function Logout() {
    const [cookies, setCookies, removeCookies] = useCookies(null)
    const handleChangeLogout = async (event) => {
        event.prevendDefault()
        removeCookies('accessToken', { path: '/' })
    }
    // useRouter , NextLink
    return (
        <>
            <Link href='/'>
                <Button
                    color='primary'
                    type='submit'
                    variant='outlined'
                    fullWidth
                    style={{ verticalAlign: 'middle', color: '#000000' }}
                    sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                    onChange={handleChangeLogout}
                >
                    로그아웃
                </Button>
            </Link>
        </>
    )
}
// 현재 구현 상태 : 로그아웃 누르면 메인페이지로 이동 , 토큰 제거
