import * as React from "react"
import Box from "@mui/material/Box"
import { Checkbox, TextField, Typography } from "@mui/material"
import AccountCircleIcon from "@mui/icons-material/AccountCircle"
import SettingsIcon from "@mui/icons-material/Settings"
import ModeEditIcon from "@mui/icons-material/ModeEdit"
import { useCookies } from "react-cookie"
import { useRouter } from "next/router"
import jwt_decode from "jwt-decode"

const label = { inputProps: { "aria-label": "Checkbox demo" } }
export default function Description(props) {
    const data = props.data
    const name = data.creatorName
    let token

    const date = new Date(props.data.endedAt)
    console.log("응애", props.data.endedAt)
    /*
    const strDate = date
        .toISOString()
        .substring(0, 10)
        .split("-")
*/
    const today = new Date()

    const router = useRouter()
    const [cookies, setCookies] = useCookies()

    if (data.user) console.log("test", data.user.userId)
    if (cookies.accessToken) token = jwt_decode(cookies.accessToken)

    const editClick = () => {
        router.push("/edit/" + data.pollId)
    }

    return (
        <Box
            className="outerContainer"
            sx={{
                maxWidth: "100%",
                height: "160px",
                mt: 1,
                mb: 2,
                borderRadius: "5px",
                padding: 0.5,
                boxShadow: 2,
                letterSpacing: 1.2,
                display: "flex",
                borderColor: "grey.500",
                flexDirection: "column",
                boxShadow: "0 0 5px 1px rgba(0,0,0,0.095)",
                borderColor: "lightgray",
                borderRadius: 2,
                flexShrink: 0,
            }}
        >
            <Box
                className="dateAndProfile"
                sx={{
                    maxWidth: "100%",
                    flex: 1,
                    borderRadius: "5px",
                    flexDirection: "row",
                    display: "flex",
                    flexDirection: "row",
                }}
            >
                <Box
                    className="date"
                    sx={{
                        display: "flex",
                        flex: 2,
                        fontSize: 12,
                        height: "100%",
                        justifyContent: "left",
                        pl: 1,
                        alignItems: "center",
                    }}
                >
                    ~{("" + data.endedAt).substring(0, 10)}
                </Box>

                <Box
                    className="profile"
                    sx={{
                        display: "flex",
                        flex: 1,
                        height: "100%",
                        fontSize: 12,
                        justifyContent: "right",
                        pr: 1,
                        alignItems: "center",
                    }}
                >
                    <AccountCircleIcon sx={{ fontSize: 10, mr: 0.2 }} />
                    {name !== undefined && (name.length <= 4 ? name : name.substring(0, 4) + "...")}
                </Box>
            </Box>
            <Box
                className="title"
                sx={{
                    maxWidth: "100%",
                    flex: 5,
                    borderRadius: "5px",
                    flexDirection: "row",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    fontSize: 30,
                }}
            >
                <Typography sx={{ fontSize: 23, letterSpacing: 0, pt: 2.8 }}>{data.title}</Typography>
            </Box>

            <Box
                className="details"
                sx={{
                    maxWidth: "100%",
                    flex: 1,
                    pr: 1,
                    justifyContent: "right",
                    flexDirection: "row",
                    display: "flex",
                }}
            >
                <Box sx={{ display: "flex", flex: 1, justifyContent: "left" }}>
                    {cookies.accessToken && data.user && token.id === data.user.userId ? (
                        <Box
                            className="edit"
                            onClick={editClick}
                            sx={{
                                display: "flex",
                                flex: 0.15,
                                height: "100%",
                                fontSize: 12,
                                justifyContent: "right",
                                pr: 1,
                                alignItems: "center",
                            }}
                        ></Box>
                    ) : (
                        ""
                    )}
                </Box>
                <Box sx={{ display: "flex", flex: 1, justifyContent: "right" }}>
                    <SettingsIcon sx={{ fontSize: 25, ml: 0.8, mb: 0.3, color: "gray" }} />
                </Box>
            </Box>
        </Box>
    )
}
