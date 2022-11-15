import axios from "axios"
import { useCookies } from "react-cookie"
import jwt from "jsonwebtoken"
import { useState } from "react"

// axios.defaults.withCredentials = true;

const instance = axios.create({
    baseURL: "https://dev.api.gollaba.net",
    timeout: 100000,
    //withCredentials: true,
})

const EXPIRED_ACCESS_TOKEN = "액세스 토큰이 만료되었습니다."

const ApiTemplate = {
    sendApi: async (method, url, body, token) => {
        let result = null
        console.log(method, url, body)
        const authorizationHeader = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        }

        if (body) {
            try {
                result = await instance[method](url, body, authorizationHeader)
            } catch (e) {
                if (e.response.status === 401 && e.message === EXPIRED_ACCESS_TOKEN) {
                }

                return e.response.data
            }

            return result.datas
        }

        try {
            result = await instance[method](url, authorizationHeader)
        } catch (e) {
            if (e.response.status === 401 && e.message === EXPIRED_ACCESS_TOKEN) {
                alert("인증에러입니다. 다시 로그인 해주세요.")
            }

            return e.response.data
        }

        return result.data
    },
    sendApiMultiPart: async (method, url, formData, token) => {
        let result = null

        const authorizationHeader = {
            headers: {
                "Content-Type": "multipart/form-data",
                "GA-Access-Token": `Bearer ${token}`,
            },
        }

        try {
            result = await instance[method](url, formData, authorizationHeader)
        } catch (e) {
            if (e.response.status === 401 && e.message === EXPIRED_ACCESS_TOKEN) {
                alert("인증에러입니다. 다시 로그인 해주세요.")
            }

            return e.response.data
        }

        return result.data
    },
}

function getCookie(name) {
    const value = `; ${document.cookie}`
    const parts = value.split(`; ${name}=`)
    if (parts.length === 2)
        return parts
            .pop()
            .split(";")
            .shift()
}

export default ApiTemplate
