import { useState } from 'react'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import jwt from 'jsonwebtoken'

const [cookies, setCookies, removeCookies] = useCookies([])
const token = jwt.decode(cookies.accessToken)
console.log(token['id'])
const userId = token['id']
const [data, setData] = useState(null)

const userInfo = async () => {
    try {
        const response = await axios.get(
            `https://dev/api.gollaba.net/v1/user/${userId}`
        )
        setData(response.data)
    } catch (e) {
        console.log(e)
    }
}
export default userInfo
