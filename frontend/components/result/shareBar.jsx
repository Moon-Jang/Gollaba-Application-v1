import React, { useEffect } from "react"
import { useRouter } from "next/router"
import Box from "@mui/material/Box"
import ShareIcon from "@mui/icons-material/Share"

import {
    FacebookShareButton,
    FacebookIcon,
    FacebookMessengerShareButton,
    FacebookMessengerIcon,
    TwitterShareButton,
    TwitterIcon,
    LineShareButton,
    LineIcon,
} from "react-share"

import kakao_share from "../../public/kakaotalk_sharing_btn.png"

const label = { inputProps: { "aria-label": "Checkbox demo" } }
export default function ShareBar(props) {
    const router = useRouter()
    const currentUrl = "https://gollaba.net" + router.asPath
    const clipboardCopy = () => {
        navigator.clipboard.writeText(currentUrl)
        alert("클립보드에 복사되었습니다.")
    }
    const options =
        props.data && props.data.option ? props.data.option.slice(0, Math.min(props.data.option.length, 5)) : []

    const newOptions = props.data.options.map((option) => ({
        title: option.description,
        description: "",
        imageUrl: option.imageUrl,
        link: {
            mobileWebUrl: "https://gollaba.net/",
            webUrl: "https://gollaba.net/",
        },
    }))

    const handleKakao = () => {
        const { Kakao, location } = window

        Kakao.Share.sendDefault({
            objectType: "list",
            headerTitle: props.data.title,
            headerLink: {
                mobileWebUrl: "https://gollaba.net/",
                webUrl: "https://gollaba.net/",
            },
            contents: newOptions,
            buttons: [
                {
                    title: "투표 보러가기",
                    link: {
                        mobileWebUrl: currentUrl,
                        webUrl: currentUrl,
                    },
                },
            ],
        })
    }

    return (
        <Box
            className="outerContainer"
            sx={{
                maxWidth: "100%",
                height: "40px",
                mt: 0.5,
                mb: 2,
                display: "flex",
                flexDirection: "row",
                justifyContent: "right",
            }}
        >
            <Box
                component={"button"}
                onClick={clipboardCopy}
                sx={{
                    display: "flex",
                    backgroundColor: "coral",
                    borderRadius: "50%",
                    width: 30,
                    height: 30,
                    justifyContent: "center",
                    alignItems: "center",
                    mt: 0.3,
                    mr: "5px",
                    border: "none",
                }}
            >
                <ShareIcon fontSize="2" sx={{ color: "white" }} />
            </Box>

            <Box sx={{ display: "flex", pr: 1 }}>
                <button
                    onClick={handleKakao}
                    style={{ width: "30px", height: "30px", border: "none", backgroundColor: "transparent" }}
                >
                    <img src={kakao_share.src} style={{ borderRadius: "50%", width: "33px", height: "33px" }} />
                </button>
            </Box>
        </Box>
    )
}
