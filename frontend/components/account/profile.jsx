import { Avatar, Box } from '@mui/material'
import { useRef, useState } from 'react'
import { axiosDefaultInstance } from 'axios'

export default function Profile() {
    // 파일 업로드 핸들러 다시 만들기
    const [image, setImage] = useState('public/camera_icon.png')
    const fileInput = useRef(null)
    let username = 'useridx'
    const setFile = (e) => {
        const target = e.currentTarget
        const files = target.files[0]
        if (files === undefined) {
            return
        }
    }
    const profileImageChangeHandler = async (e) => {
        if (file !== undefined) {
            try {
                const formData = new FormData()
                formData.append('file', file)
                const axiosResponse =
                    (await axiosDefaultInstance.post) <
                    ApiResponse <
                    FileUploadResponse >>
                        ('/files',
                        formData,
                        { headers: { 'content-type': 'multipart/form-data' } })

                if (
                    axiosResponse.status < 200 ||
                    axiosResponse.status >= 300 ||
                    axiosResponse.data.resultCode !== 0
                ) {
                    throw Error(
                        axiosResponse.data.message || '문제가 발생했어요'
                    )
                }
                alert('파일 업로드 성공!')
                console.log(axiosResponse.data.data)
            } catch {
                console.error(e)
                alert(e.message)
            }
        }
        if (e.target.files[0]) {
            setFile(e.target.files[0])
        } else {
            setImage('public/camera_icon.png')
            return
        }
    }
    return (
        <>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
            >
                <Avatar
                    src={image}
                    sx={{ width: 215, height: 215 }}
                    onClick={
                        profileImageChangeHandler
                        // () => {fileInput.current.click()}
                    }
                >
                    <input
                        type='file'
                        style={{ display: 'none' }}
                        accept='image/*'
                        id='selectFile'
                        name={`profile_img_${username}`}
                        onClick={profileImageChangeHandler}
                        ref={fileInput}
                    ></input>
                </Avatar>
                <div>배경이미지</div>
                <div>프로필이미지 (수정 가능)</div>
                <div>닉네임 공간 (수정 가능)</div>
            </Box>
        </>
    )
}
