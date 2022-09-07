import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'

export default function TokenEx() {
    const [cookies, setCookies, removeCookies] = useCookies([])

    const decoded = jwt.decode(cookies.accessToken)
    console.log('decoded', decoded)

    return (
        <>
            <div>
                토큰 정보
                <span>idx: {decoded.id}</span>
                <span>uid: {decoded.uid}</span>
                <span>un: {decoded.un}</span>
            </div>
        </>
    )
}
