import { useState } from 'react'
import EditIcon from '@mui/icons-material/Edit'
import Box from '@mui/material/Box'

export default function Nickname() {
    const [userName, setUserName] = useState(null)
    const userNameSpan = () => {
        if (userName == null) {
            setUserName('닉네임을 정하지 않았습니다')
        } else {
            setUserName(`${userName}님의 페이지입니다.`)
        }
    }

    const nicknameStyle = {
        fontSize: '24px',
    }
    return (
        <>
            <div style={nicknameStyle}>
                <Box
                    sx={{
                        marginTop: 2,
                        marginBottom: 6,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        justifyContent: 'center',
                    }}
                >
                    <span>
                        {userName}닉네임 공간
                        <EditIcon style={{ margin: '0 0 0 10' }} />
                    </span>
                </Box>
            </div>
        </>
    )
}
// token에 따라 닉네임 바뀌는거 보여주는 기능 넣을 것
// Icon을 누르면 수정할 수 있는 텍스트박스로 나오는 것 추가할 것
