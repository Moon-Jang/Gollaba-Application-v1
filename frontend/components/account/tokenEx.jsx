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
        <>
            <div>
                <span>{result}</span>
            </div>
        </>
    )
}
