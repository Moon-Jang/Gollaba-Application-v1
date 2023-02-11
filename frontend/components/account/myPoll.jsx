import { Button } from "@mui/material"
import Link from "next/link"
import HowToRegIcon from "@mui/icons-material/HowToReg"

export default function MyPoll() {
    const leftalign = {
        float: "left",
        marginTop: 2,
        marginBottom: 2,
    }
    const iconStyle = {
        fontSize: "24px",
        margin: "0 15 0 15",
    }
    return (
        <>
            <div style={leftalign}>
                <Link href="/account/mypolls">
                    <Button
                        color="primary"
                        type="submit"
                        variant="text"
                        fullWidth
                        style={{
                            verticalAlign: "middle",
                            color: "#000000",
                            fontSize: "24px",
                            textAlign: "left",
                            fontWeight: "lighter",
                            padding: "0 0 0 0",
                        }}
                    >
                        <HowToRegIcon style={iconStyle} />내 투표
                    </Button>
                </Link>
            </div>
        </>
    )
}
//현재 상태 : 내 투표 화면 구현, 맵핑은 아직 (후순위)
