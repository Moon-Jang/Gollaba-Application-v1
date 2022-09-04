import { Button } from '@mui/material'
import Link from 'next/link'
import { useCookies } from 'react-cookie'
import LogoutIcon from '@mui/icons-material/Logout'

export default function Logout() {
    const [cookies, setCookies, removeCookies] = useCookies(null)
    const handleChangeLogout = async (event) => {
        event.prevendDefault()
        removeCookies('accessToken', { path: '/' })
    }
    // useRouter , NextLink

    const leftalign = {
        float: 'left',
        marginTop: 7,
        marginBottom: 7,
    }
    const iconStyle = {
        fontSize: '24px',
        margin: '0 15 0 15',
    }
    return (
        <>
            <Link href='/'>
                <div style={leftalign}>
                    <Button
                        color='primary'
                        type='submit'
                        variant='text'
                        fullWidth
                        style={{
                            verticalAlign: 'middle',
                            color: '#000000',
                            fontSize: '24px',
                            textAlign: 'left',
                            padding: '0 0 0 0',
                        }}
                        // sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                        onChange={handleChangeLogout}
                    >
                        <LogoutIcon style={iconStyle} />
                        로그아웃
                    </Button>
                </div>
            </Link>
        </>
    )
}
// 현재 구현 상태 : 로그아웃 누르면 메인페이지로 이동 , 토큰 제거
