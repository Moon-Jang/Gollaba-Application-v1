import Favorite from "./favorite"
import Logout from "./logout"
import MyPoll from "./myPoll"
import PasswordChange from "./passwordChange"
import Box from "@mui/material/Box"

import List from "@mui/material/List"
import ListItem from "@mui/material/ListItem"
import ListItemText from "@mui/material/ListItemText"
import Divider from "@mui/material/Divider"

export default function AccountMenu() {
    const listStyle = {
        width: "100%",
        maxWidth: 400,
        bgcolor: "background.paper",
        paddingTop: 5,
    }

    return (
        <>
            <List sx={listStyle} component="nav" aria-label="mailbox folders">
                <Divider />
                <Box sx={{ maxHeight: 80 }}>
                    <ListItem button>
                        <MyPoll />
                    </ListItem>
                </Box>
                <Divider />
                <Box sx={{ maxHeight: 70 }}>
                    <ListItem button>
                        <Favorite />
                    </ListItem>
                </Box>

                <Divider />
                <Box sx={{ maxHeight: 80 }}>
                    <ListItem button>
                        <Logout />
                    </ListItem>
                </Box>
                <Divider />
            </List>
        </>
    )
}
