import { Button } from "@mui/material"
import { Box } from "@mui/material/Box"
import ApiGateway from "./../../apis/ApiGateway"
import { useCookies } from "react-cookie"

export default function UpdateBtn() {
    const [cookies, setCookies, removeCookies] = useCookies([])
    const updateVote = async () => {
        const pollId = data?.pollId
        const payload = {}
        await ApiGateway.updatePoll(pollId, payload, cookies.accessToken)
    }
    return (
        <Box
            sx={{
                background: "white",
                position: "fixed",
                width: "92vw",
                pr: 1,
                mb: -1,
                maxHeight: 1,
                bottom: 60,
                display: "flex",
                flexDirection: "row",
            }}
        >
            <Button
                onClick={updateVote}
                sx={{
                    flex: 2,
                    maxWidth: "100%",
                    height: 40,
                    mt: 2,
                    mb: 2,
                    padding: 1,
                    border: 1,
                    borderRadius: "5px",
                    padding: 0.5,
                    boxShadow: 2,
                    display: "flex",
                    border: 1,
                    flexDirection: "row",
                    alignItems: "center",
                    justifyContent: "center",
                    textAlign: "center",
                    fontSize: 15,
                    border: "none",
                }}
            >
                수정하기
            </Button>
        </Box>
    )
}
