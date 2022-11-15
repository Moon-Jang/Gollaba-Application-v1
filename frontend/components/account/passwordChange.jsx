import { Button } from '@mui/material'
import Link from 'next/link'
import LockResetIcon from '@mui/icons-material/LockReset'

export default function PasswordChange() {
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
            <Link href='/account/pwchange'>
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
                    >
                        <LockResetIcon style={iconStyle} />
                        비밀번호 변경
                    </Button>
                </div>
            </Link>
        </>
    )
}
// 현재 상태 : 비밀번호 변경 화면 구현 (토큰은 아직)
