import { useState } from 'react'
import EditIcon from '@mui/icons-material/Edit'
import Box from '@mui/material/Box'
import { useCallback } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import userSlice from './reducers/user'

// react-redux toolkit이용해 만들어 보려 했으나 redux쪽에서 자꾸 컴파일 실패하여 보류
export default function Nickname() {
    const dispatch = useDispatch()
    const nickname = useSelector((state) => state.user.data.nickname)
    const [isEdit, setIsEdit] = useState(false)
    const onClickNickname = useCallback(() => {
        setIsEdit(!isEdit)
    }, [isEdit])
    const [nickname_, setNickname_] = useState(nickname)
    const onChangeNickname_ = useCallback((e) => {
        setNickname_(e.target.value)
    }, [])
    const editNickname = useCallback(
        (e) => {
            e.preventDefault()
            dispatch(userSlice.actions.editNickname(nickname_))
            setIsEdit(!isEdit)
        },
        [dispatch, nickname_, isEdit]
    )

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
                    <span onClick={onClickNickname}>
                        {nickname}의 페이지입니다.
                        <EditIcon style={{ margin: '0 0 0 10' }} />
                    </span>
                    {isEdit ? (
                        <form>
                            <input
                                value={nickname_}
                                onChange={onChangeNickname_}
                            ></input>
                            <button onClick={editNickname}></button>
                        </form>
                    ) : null}
                </Box>
            </div>
        </>
    )
}
// token에 따라 닉네임 바뀌는거 보여주는 기능 넣을 것
// Icon을 누르면 수정할 수 있는 텍스트박스로 나오는 것 추가할 것
