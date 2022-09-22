import { useState, useEffect } from 'react'
import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'
import ApiGateway from '../../apis/ApiGateway'

export default function MultipartEx() {
    const [cookies, setCookies, removeCookies] = useCookies([])
    const token = jwt.decode(cookies.accessToken)
    const [data, setData] = useState(null)
    const showUser = async () => {
        if (!token) return
        try {
            //await ApiGateway.updateNickName(formData, cookies.accessToken);
            const userInfo = await ApiGateway.showUser(
                token.id,
                cookies.accessToken
            )
            setData(userInfo)
            console.log('showUser :', userInfo)
        } catch (e) {
            console.log(e)
        }
    }
    // useEffect(() => {
    //     setToken(jwt.decode(cookies.accessToken))

    //     console.log('useEffect data :', data)
    // }, [])
    const userIdx = data?.userId
    const profileImageSrc = () => {
        if (data?.profileImageUrl == null) return 'NO IMAGE HERE'
        return data?.profileImageUrl
    }
    const changeNickname = async () => {
        if (!token) return
        try {
            const nickChange = await ApiGateway.updateNickName()
        } catch (e) {
            console.log(e)
        }
    }

    return (
        <div>
            <button onClick={showUser}>API호출</button> <br />
            <span>UserId : {data?.userId}</span> <br />
            <span>UniqueId : {data?.uniqueId}</span> <br />
            <span>NickName : {data?.nickName}</span> <br />
            <span>ProfileImageUrl : {data?.profileImageUrl}</span> <br />
            <span>BackgroundImageUrl : {data?.backgroundImageUrl}</span> <br />
            <span>UserRole : {data?.userRole}</span> <br />
            <span>Created At : {data?.createdAt}</span> <br />
            <span>Updated At : {data?.updatedAt}</span> <br />
            {userIdx} <br />
            {profileImageSrc()} <br />
            <img src={profileImageSrc()}></img>
        </div>
    )
}
