import { useState, useEffect } from 'react'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'

const AxiosEx = () => {
    const [cookies, setCookies, removeCookies] = useCookies([])
    const [token, setToken] = useState(null)
    const [data, setData] = useState(null)

    // 현재상황 : 헤더 요청으로 토큰 권한까지 넣어서 요청했음에도 404 응답이 나오는 상황. 백엔드 체크 요청
    console.log('cookies1 :', cookies)
    console.log('token1 :', token)
    console.log('data1: ', data)
    const userInfo = async () => {
        try {
            const response = await axios.get(
                `https://dev.api.gollaba.net/v1/users/${token?.id}`,
                {
                    headers: {
                        'GA-Access-Token': `Bearer ${cookies?.accessToken}`,
                    },
                }
            )
            setData(response.data)
        } catch (e) {
            console.log(e)
        }
    }
    useEffect(() => {
        setToken(jwt.decode(cookies.accessToken))
        userInfo()
        console.log('cookies2 :', cookies)
        console.log('token2 :', token)
        console.log('data2 :', data)
    }, [])

    return (
        <div>
            <div>
                <button onClick={userInfo}>회원정보</button>
            </div>
            {data && (
                <textarea
                    rows={20}
                    value={JSON.stringify(data, null, 2)}
                    readOnly={true}
                />
            )}
            <div>
                <span>사용자 이름 : {data?.nickName}</span>
                <br />
                <span>프로필 이미지 : {data?.profileImageUrl}</span>
                <br />
                <span>배경 이미지 : {data?.backgroundImageUrl}</span>
                <br />
                <span>사용자 역할 : {data?.userRole}</span>
                <br />
                <span>사용자 번호 : {data?.userId}</span>
                <br />
            </div>
        </div>
    )
}
export default AxiosEx
