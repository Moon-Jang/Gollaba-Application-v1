import ApiGateway from './../../apis/ApiGateway'
import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'
import { useRef } from 'react'

export default function KMExample() {
    const showApi = () => {
        console.log(ApiGateway.showUser())
        JSON.stringify(ApiGateway.showUser())
    }
    const bgImgRef = useRef('')
    const profileImgRef = useRef('')
    const nickNameRef = useRef('')
    const userIdRef = useRef('')
    const uniqueIdRef = useRef('')
    const userRoleRef = useRef('')

    const handleSubmit = async () => {
        const payload = {
            userId: userIdRef.current.value,
            uniqueId: uniqueIdRef.current.value,
            nickName: nickNameRef.current.value,
            backgroundImageUrl: bgImgRef.current.value,
            profileImageUrl: profileImgRef.current.value,
            userRole: userRoleRef.current.value,
        }
        console.log('payload', payload)
        const response = await ApiGateway.showUser(payload)
        if (response.error) {
            alert(response)
            setIsSubmit(false)
            return
        }
    }
    // TypeError: Cannot read properties of undefined 'value'

    const [cookies, setCookies, removeCookies] = useCookies([])
    const token = jwt.decode(cookies.accessToken)
    console.log('token', token)

    return (
        <>
            <div>
                회원정보: <br />
                <button onClick={handleSubmit}></button>
                {handleSubmit} <br />
                토큰정보: <br />
                {JSON.stringify(token)}
            </div>
        </>
    )
}
