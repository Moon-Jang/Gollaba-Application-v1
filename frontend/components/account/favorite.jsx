import { Button } from '@mui/material'
import Link from 'next/link'
import FavoriteIcon from '@mui/icons-material/Favorite'

export default function Favorite() {
    const leftalign = {
        float: 'left',
    }
    const iconStyle = {
        fontSize: '24px',
        margin: '0 15 0 15',
    }
    return (
        <>
            <Link href='/account/favorites'>
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
                        <FavoriteIcon style={iconStyle} />
                        즐겨찾기
                    </Button>
                </div>
            </Link>
        </>
    )
}
// 현재 상태 : 즐겨찾기 화면 구현, 맵핑 기능은 후순위로 미룸
