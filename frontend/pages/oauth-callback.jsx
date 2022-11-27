import * as React from "react"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import Box from "@mui/material/Box"
import { useEffect } from "react"
import { useRouter } from "next/router"

export default function OAuth2CallbackPage() {
    const router = useRouter()

    useEffect(() => {
        if (!router.query || !Object.keys(router.query).length) return
        console.log(router.query)
        // 회원가입된 회원일 경우
        if (router.query.accessToken) {
            localStorage.setItem("accessToken", router.query.accessToken)
            router.push("/")
            return
        }

        // 회원가입이 필요한 회원일 경우
        const { name, email, providerId, providerType, profileImageUrl } = router.query

        // 필수 값 없을 경우 back
        if (!email || !providerId || !providerType) {
            alert("회원가입에 필요한 필수 값이 없습니다. 관리자에게 문의하세요.")
            router.push("/login")
            return
        }

        router.push({ pathname: "signup", query: router.query })
    }, [router.query])

    return <></>
}
