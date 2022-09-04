import Favorite from './favorite'
import Logout from './logout'
import MyPoll from './myPoll'
import PasswordChange from './passwordChange'

import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemText from '@mui/material/ListItemText'
import Divider from '@mui/material/Divider'

export default function AccountMenu() {
    const style = {
        width: '100%',
        maxWidth: 360,
        bgcolor: 'background.paper',
    }

    return (
        <>
            <List sx={style} component='nav' aria-label='mailbox folders'>
                <ListItem button>
                    <MyPoll />
                </ListItem>
                <Divider />
                <ListItem button>
                    <Favorite />
                </ListItem>
                <Divider />
                <ListItem button>
                    <PasswordChange />
                </ListItem>
                <Divider />
                <ListItem button>
                    <Logout />
                </ListItem>
                <Divider />
            </List>
        </>
    )
}
