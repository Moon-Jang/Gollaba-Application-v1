import { Button } from '@mui/material'
import Link from 'next/link'
import HowToRegIcon from '@mui/icons-material/HowToReg'

export default function MyPoll() {
    const leftalign = {
        float: 'left',
    }
    const iconStyle = {
        fontSize: '24px',
        margin: '0 15 0 15',
    }
    return (
        <>
            <Link href='/account/mypolls'>
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
                            // border: '0 0 0 0',
                        }}
                        // startIcon={}
                        // sx={{ mt: 4.5, mb: 2, borderRadius: 12.5, boxShadow: 4 }}
                    >
                        <HowToRegIcon style={iconStyle} />내 투표
                    </Button>
                </div>
            </Link>
        </>
    )
}
//현재 상태 : 내 투표 화면 구현, 맵핑은 아직 (후순위)
