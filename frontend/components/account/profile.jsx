import { Avatar, Box } from '@mui/material'
import { useRef, useState } from 'react'

import Nickname from './nickname'

export default function Profile() {
    // photoInput.current.click() is not a function
    // Cannot read properties of null (reading 'click')
    const photoInput = useRef(null)
    const PROFILE_BASIC =
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'
    const handleClick = () => {
        photoInput.current.click()
    }
    const [profileImage, setProfileImage] = useState(PROFILE_BASIC)

    const handlePhoto = (e) => {
        const photoToAdd = e.target.files

        if (photoToAdd[0]) {
            setProfileImage(photoToAdd[0])
        } else {
            setProfileImage(PROFILE_BASIC)
        }
        const reader = new FileReader()
        reader.onload = () => {
            if (reader.readyState === 2) {
                setProfileImage(reader.result)
            }
        }
        reader.readAsDataURL(photoToAdd[0])
    }

    return (
        <>
            <Box
                sx={{
                    marginTop: 10,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
            >
                <div>
                    <Avatar
                        src={profileImage}
                        id='profileImage'
                        sx={{ width: 215, height: 215 }}
                        onClick={handleClick}
                        // ref={photoInput}
                    />
                    <input
                        type='file'
                        id='profileImageInput'
                        style={{ display: 'none' }}
                        accept='image/jpg image/jpeg image/png'
                        onChange={(e) => handlePhoto(e)}
                        ref={photoInput}
                    />
                </div>

                <Nickname />
            </Box>
        </>
    )
}
// 닉네임 span으로 처리?
