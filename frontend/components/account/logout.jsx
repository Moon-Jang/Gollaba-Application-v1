import { Button } from "@mui/material"
import Link from "next/link"
import { useCookies } from "react-cookie"
import LogoutIcon from "@mui/icons-material/Logout"

export default function Logout() {
    const [cookies, setCookies, removeCookies] = useCookies(null)
    const logoutHandler = async () => {
        localStorage.clear()
    }
    // useRouter , NextLink

    const leftalign = {
        float: "left",
        marginTop: 7,
        marginBottom: 7,
    }
    const iconStyle = {
        fontSize: "24px",
        margin: "0 15 0 15",
    }
    return (
        <>
            <Link href="/login">
                <div style={leftalign}>
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
                            padding: "0 0 0 0",
                        }}
                        onClick={logoutHandler}
                    >
                        <LogoutIcon style={iconStyle} />
                        로그아웃
                    </Button>
                </div>
            </Link>
        </>
    )
}
