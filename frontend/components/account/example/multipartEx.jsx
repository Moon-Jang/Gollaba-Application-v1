import { useState, useEffect } from "react"
import { useCookies } from "react-cookie"
import jwt from "jsonwebtoken"
import ApiGateway from "../../../apis/ApiGateway"
import { axios } from "axios"

export default function MultipartEx() {
    const [cookies, setCookies, removeCookies] = useCookies([])
    const token = jwt.decode(cookies.accessToken)
    const [data, setData] = useState(null)
    const showUser = async () => {
        if (!token) return
        try {
            const userInfo = await ApiGateway.showUser(token.id, cookies.accessToken)
            setData(userInfo)
            console.log("showUser :", userInfo)
        } catch (e) {
            console.log(e)
        }
    }

    const userIdx = data?.userId
    const profileImageSrc = () => {
        if (data?.profileImageUrl == null) return "NO IMAGE HERE"
        return data?.profileImageUrl
    }

    const [nickName, setNickName] = useState("")
    const onChangeNicknameHandler = e => {
        setNickName(e.target.value)
    }
    // 슈바 이게 된다
    const changeNickname = async () => {
        if (!token) return
        try {
            const formData = new FormData()
            formData.append("nickName", nickName)
            formData.append("updateType", "NICKNAME")
            const nickChange = await ApiGateway.updateNickName(formData, cookies.accessToken)
            setData(nickChange)
            console.log("nickChange :", nickChange)
            for (const keyValue of formData) {
                console.log("formData keyValue :", keyValue)
            }
        } catch (e) {
            console.log(e)
        }
    }
    // RefernceError: body is not defined -> formdata를 body로 보내는 법?
    const imageUploadHandler = async e => {
        if (!token) return
        try {
            const img = e.target.files[0]
            console.log("img", img)
            const formData = new FormData()
            formData.append("profileImage", img)
            formData.append("updateType", "PROFILE_IMAGE")
            for (const keyValue of formData) console.log("keyValue : ", keyValue)
            const profileChange = await ApiGateway.updateNickName(formData, cookies.accessToken)
            setData(profileChange)
            console.log("profileChange : ", profileChange)
        } catch (e) {
            console.log(e)
        }
        console.log("profileImageSrc : ", profileImageSrc())
    }
    // https://dev-gollaba-bucket.s3.ap-northeast-2.amazonaws.com/profile_image/30_fedcb263-76eb-4d7d-a097-4526f9189814.jpeg
    // https://dev-gollaba-bucket.s3.ap-northeast-2.amazonaws.com/30_fedcb263-76eb-4d7d-a097-4526f9189814.jpeg

    return (
        <>
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
                <img src={profileImageSrc()}></img> <br />
                <input type="text" name="nickNameChange" onChange={onChangeNicknameHandler} value={nickName} />
                <button onClick={changeNickname}>닉네임 변경</button>
            </div>
            <div>
                <h1>File Upload Test</h1>
                <input type="file" accept="image/*" name="profileImage" onChange={imageUploadHandler} />
            </div>
        </>
    )
}
