import * as React from "react"
import Box from "@mui/material/Box"
import { Checkbox, TextField, Typography } from "@mui/material"
import AccountCircleIcon from "@mui/icons-material/AccountCircle"

const label = { inputProps: { "aria-label": "Checkbox demo" } }
export default function Description(props) {
    const data = props.data
    const strDate = data.endedAt.substring(0, 10).split("-")

    return (
        <Box
            className="outerContainer"
            sx={{
                maxWidth: "100%",
                height: "160px",
                // minHeight: 180,
                mt: 3,
                mb: 2,
                padding: 0.5,
                boxShadow: "0 0 5px 1px rgba(0,0,0,0.095)",
                //border: 1,
                borderColor: "lightgray",
                borderRadius: 2,
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
                    <Typography sx={{ fontSize: 12, letterSpacing: 0, pl: 0, pt: 0.6, color: "rgb(192, 192, 192)" }}>
                        {strDate[1] + "월 " + strDate[2] + "일까지 · " + props.data.totalVoteCount + "명 참여"}
                    </Typography>
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
                    <AccountCircleIcon fontSize="11" sx={{ mr: 0.2, mt: 0.5 }} />
                    <Typography sx={{ fontSize: 12, letterSpacing: 0.5, pt: 0.6 }}>{data.creatorName}</Typography>
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
                }}
            >
                <Typography sx={{ fontSize: 25, letterSpacing: 0 }}>{data.title}</Typography>
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
                    color: "#808080",
                }}
            >
                {data.isBallot ? "기명투표" : "익명투표"} {data.responseType === "SINGLE" ? "단일투표" : "복수투표"}
            </Box>
        </Box>
    )
}
