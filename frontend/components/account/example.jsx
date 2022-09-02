import { Avatar, Button } from '@mui/material'
import { useRef, useState } from 'react'

export default function KMExample() {
    const photoInput = useRef()
    const handleClick = () => {
        photoInput.current.click()
    }
    const [photoToAddList, setPhotoToAddList] = useState([])
    const photoToAddPreview = () => {
        return photoToAddList.map((photo) => {
            return (
                <>
                    <div className='photoBox' key={photo.url}>
                        <Button
                            className='photoBoxDelete'
                            onClick={() => onRemoveToAdd(photo.url)}
                        >
                            삭제하기
                        </Button>
                        <img className='photoPreview' src={photo.url} />
                    </div>
                </>
            )
        })
    }
    const onRemoveToAdd = (deleteUrl) => {
        setPhotoToAddList(
            photoToAddList.filter((photo) => photo.url != deleteUrl)
        )
    }
    const handlePhoto = (e) => {
        const temp = []
        const photoToAdd = e.target.files
        for (let i = 0; i < photoToAdd.length; i++) {
            temp.push({
                id: photoToAdd[i].name,
                file: photoToAdd[i],
                url: URL.createObjectURL(photoToAdd[i]),
            })
        }
        setPhotoToAddList(temp.concat(photoToAddList))
    }
    return (
        <>
            <div className='contentWrapper'>
                <div className='contentBody photoUploaderWrapper'>
                    <p>사진 업로드 예제</p>

                    <div className='photoUploaderContent'>
                        <div className='photoBox addPhoto'>
                            {/* <PlusOutlined /> */}
                            <Avatar
                                src='../public/camera_icon.png'
                                sx={{ width: 85, height: 85 }}
                                onClick={handleClick}
                            />
                            <input
                                type='file'
                                accept='image/jpg, image/jpeg, image/png'
                                multiple
                                ref={photoInput}
                                onChange={(e) => handlePhoto(e)}
                                style={{ display: 'none' }}
                            />
                        </div>
                        {photoToAddPreview()}
                        {/* {photoAddedPreview()} */}
                    </div>
                </div>

                {/* <Button className='photoUploadComplete' onClick={savePhoto}>
                    기록하기
                </Button> */}
            </div>
        </>
    )
}
