import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'

export default function TokenEx() {
    const [cookies, setCookies, removeCookies] = useCookies([])

    const decoded = jwt.decode(cookies.accessToken)
    console.log('decoded', decoded)
    const payload = {
        userId: cookies.accessToken,
    }

    const result = JSON.stringify(decoded)
    return (
        // cookie에서 컴포넌트
        <>
            <div>
                <span>{result}</span>
            </div>
        </>
    )
}
