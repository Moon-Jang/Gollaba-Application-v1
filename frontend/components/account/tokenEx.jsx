import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'

export default function TokenEx() {
    const [cookies, setCookies, removeCookies] = useCookies([])

    const decoded = jwt.decode(cookies.accessToken)
    console.log('decoded', decoded)

    return (
        <>
            <div>
                토큰 정보 <br />
                <span>idx: {decoded.id}</span> <br />
                <span>사용자 아이디: {decoded.uid}</span> <br />
                <span>사용자 이름: {decoded.un}</span> <br />
                <span>발생시간: {decoded.iat}</span> <br />
                <span>만료시간: {decoded.exp}</span> <br />
            </div>
        </>
    )
}
