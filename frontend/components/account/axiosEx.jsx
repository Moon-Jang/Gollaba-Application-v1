import { useState, useEffect } from 'react'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'

const AxiosEx = () => {
    const [cookies, setCookies, removeCookies] = useCookies([])
    const [token, setToken] = useState(null)
    const [data, setData] = useState(null)

    useEffect(() => {
        setToken(jwt.decode(cookies.accessToken))
    }, [])

    // 현재상황 : 헤더 요청으로 토큰 권한까지 넣어서 요청했음에도 404 응답이 나오는 상황. 백엔드 체크 요청
    const userInfo = async () => {
        try {
            const response = await axios.get(
                `https://dev.api.gollaba.net/v1/users/${token.id}`,
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
        </div>
    )
}
export default AxiosEx
