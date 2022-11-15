import * as React from "react"
import Box from "@mui/material/Box"
import { Checkbox, TextField } from "@mui/material"
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

    console.log("이름", name)

    let token

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
                height: "180px",
                // minHeight: 180,
                mt: 1.5,
                mb: 2,
                borderRadius: "5px",
                padding: 0.5,
                boxShadow: 2,
                letterSpacing: 1.2,
                display: "flex",
                //flex: 1,
                borderColor: "grey.500",
                flexDirection: "column",
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
                    <AccountCircleIcon fontSize="12" sx={{ mr: 0.2 }} />
                    {name !== undefined && (name.length <= 5 ? name : name.substring(0, 5) + "...")}
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
                {data.title}
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
                    <SettingsIcon fontSize="large" sx={{ ml: 0.8, mb: 0.3 }} />
                </Box>
            </Box>
        </Box>
    )
}
