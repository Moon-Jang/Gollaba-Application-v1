import { useState } from "react"
import Box from "@mui/material/Box"
import { useRouter } from "next/router"
import AccountCircleIcon from "@mui/icons-material/AccountCircle"
import ArrowDropDownCircleIcon from "@mui/icons-material/ArrowDropDownCircle"
import LinearProgress from "@mui/material/LinearProgress"
import StarBorderIcon from "@mui/icons-material/StarBorder"
import StarIcon from "@mui/icons-material/Star"
import { IconButton, Typography } from "@mui/material"
import ApiGateway from "./../../apis/ApiGateway"
import { useCookies } from "react-cookie"

export default function Poll(props) {
    const router = useRouter()
    const data = props.data
    const options = data.options
    const [isExtend, setIsExtend] = useState(false)
    const [favoriteId, setFavoriteId] = useState(data?.favorites?.favoritesId)
    const [cookies, setCookies, removeCookies] = useCookies([])

    const date = new Date(props.data.endedAt)
    const strDate = date.toISOString().substring(0, 10).split("-")

    const today = new Date()

    let temp = 0
    for (let i = 0; i < options.length; i++) {
        temp = temp + options[i].voteCount
    }

    const map1 = options.map((el) => {
        return (
            <Box mt={0.5} mr={1} mb={0.5} ml={-0.5}>
                {el.description}
                <LinearProgress
                    variant="determinate"
                    value={props.data.totalVoteCount !== 0 ? (el.voteCount / props.data.totalVoteCount) * 100 : 0}
                />
            </Box>
        )
    })

    const buttonClick = () => {
        {
            today < date ? router.push(`/polls/${props.data.pollId}`) : router.push(`/result/${props.data.pollId}`)
        }
    }

    const extendClick = () => {
        isExtend === true ? setIsExtend(false) : setIsExtend(true)
    }
    const favoriteClick = async (e) => {
        const hashId = data?.pollId
        const payload = { pollId: hashId }
        if (!favoriteId) {
            const favoriteSend = await ApiGateway.makeFavorite(payload, cookies.accessToken)

            if (favoriteSend?.error === true) {
                alert("로그인 이후에 사용할 수 있는 기능입니다.")
                return
            }
            setFavoriteId(favoriteSend?.favoritesId)
            return
        }

        const favoriteDelete = await ApiGateway.deleteFavorite(favoriteId, cookies.accessToken)

        if (favoriteDelete?.error === true) {
            // fail
            alert(favoriteDelete.message)
            return
        }
        setFavoriteId(null)

        const date = new Date(props.data.endedAt)
        const strDate = date.toISOString().substring(0, 10).split("-")

        const today = new Date()
    }

    return (
        <Box sx={{ ml: -1, mr: -1 }}>
            <Box className="upperContainer" sx={{ flexDirection: "row", display: "flex" }}>
                <Box
                    className="ExpireDate"
                    sx={{
                        padding: 0,
                        margin: 0,
                        ml: 0.5,
                        mt: 1,
                        fontSize: 12,
                        color: "#808080",
                        display: "flex",
                        flex: 1,
                    }}
                ></Box>

                <Box
                    className="link"
                    sx={{
                        padding: 0,
                        margin: 0,
                        mr: 0.5,
                        mt: 1,
                        fontSize: 12,
                        color: "#808080",
                        display: "flex",
                        justifyContent: "right",
                        flex: 1,
                    }}
                ></Box>
            </Box>
            <Box
                className="outerContainer"
                sx={{
                    maxWidth: "100%",
                    minHeight: 180,
                    mt: 0.6,
                    mb: 2,
                    borderRadius: "5px",
                    padding: 0.5,
                    boxShadow: 2,
                    letterSpacing: 1.2,
                    //border: 1,
                    borderColor: "grey.500",
                    boxShadow: "0 0 5px 1px rgba(0,0,0,0.095)",
                    //border: 1,
                    borderColor: "lightgray",
                    borderRadius: 2,

                    //alignItems: "flex-end"
                }}
            >
                <Box
                    className="innerContainer"
                    sx={{
                        border: 0,
                        padding: 0,
                        maxWidth: "100%",
                        height: "100%",
                        flexDirection: "row",
                        display: "flex",
                        //backgroundColor: "rgb(0,0,0)",
                    }}
                >
                    <Box
                        className="infoContainer"
                        sx={{
                            border: 0,
                            padding: 0,
                            flex: 1,
                            flexDirection: "column",
                            display: "flex",
                        }}
                    >
                        <Box
                            className="TitleAndProfile"
                            sx={{
                                flex: 2,
                                display: "flex",
                                flexDirection: "row",
                                //justifyContent: "center",
                                //alignItems: "center",
                            }}
                        >
                            <Box
                                className="Title"
                                sx={{
                                    display: "flex",
                                    flex: 3,
                                    pt: 2,
                                    pl: 2,
                                    pb: 1,
                                    fontWeight: "medium",
                                    fontSize: 18,
                                }}
                            >
                                <Typography sx={{ letterSpacing: 0 }}> {data.title}</Typography>
                            </Box>

                            <Box
                                className="Profile"
                                sx={{
                                    display: "flex",
                                    flex: 1,
                                    pt: 2,
                                    pr: 2.5,
                                    justifyContent: "right",
                                    fontSize: 14,
                                    //backgroundColor: "red",
                                    color: "#808080",
                                }}
                            >
                                <Box
                                    backgroundColor={today < date ? "#E8F4E7" : "rgb(251,239,236)"}
                                    sx={{
                                        display: "flex",
                                        width: "50px",
                                        height: "23px",
                                        alignItems: "center",
                                        justifyContent: "center",
                                        ml: 0.5,

                                        borderRadius: 0.5,
                                    }}
                                >
                                    {today < date ? (
                                        <Typography sx={{ fontSize: 12, letterSpacing: 0, color: "rgb(74,142,78)" }}>
                                            진행 중
                                        </Typography>
                                    ) : (
                                        <Typography sx={{ fontSize: 12, letterSpacing: 0, color: "rgb(213,82,49)" }}>
                                            종료
                                        </Typography>
                                    )}
                                </Box>
                            </Box>
                        </Box>

                        <Box
                            className="Options"
                            sx={{
                                display: "flex",
                                flexDirection: "column",
                                flex: 4,
                                ml: 2.5,
                                mr: 1,
                                mb: 1.5,
                                fontSize: 13,
                            }}
                        >
                            {isExtend === false ? map1.slice(0, 2) : map1}
                        </Box>
                        <Box
                            className="Button"
                            sx={{
                                flex: 1.2,
                                flexDirection: "row",
                                display: "flex",
                                mb: 1.5,
                                pb: 0,
                                //backgroundColor: "red",
                            }}
                        >
                            <Box
                                className="Extend"
                                onClick={extendClick}
                                sx={{
                                    flex: 2,
                                    display: "flex",
                                    ml: 2,
                                    justifyContent: "left",
                                    alignItems: "center",
                                    fontSize: 13,

                                    color: "rgb(192, 192, 192)",
                                    display: "flex",
                                    letterSpacing: 0,
                                }}
                            >
                                {map1.length >= 3 ? (
                                    <ArrowDropDownCircleIcon sx={{ color: "#808080", mr: 0.3 }} />
                                ) : (
                                    <></>
                                )}
                                {strDate[1] + "월 " + strDate[2] + "일까지 · " + props.data.totalVoteCount + "명 참여"}
                            </Box>

                            <Box className="favorite" sx={{ display: "flex" }}>
                                <IconButton onClick={favoriteClick}>
                                    {favoriteId ? (
                                        <>
                                            <StarIcon />
                                        </>
                                    ) : (
                                        <>
                                            <StarBorderIcon />
                                        </>
                                    )}
                                </IconButton>
                            </Box>
                            <Box
                                onClick={buttonClick}
                                component="button"
                                className="Btn"
                                outline="none"
                                sx={{
                                    flex: 1,
                                    display: "flex",
                                    mt: 0.4,
                                    mr: 2,
                                    justifyContent: "center",
                                    alignItems: "center",
                                    fontSize: 14,
                                    border: 0,
                                    borderRadius: "5px",
                                    marginBottom: 0,
                                    height: 35,
                                }}
                            >
                                투표하기
                            </Box>
                        </Box>
                    </Box>
                </Box>
            </Box>
        </Box>
    )
}
