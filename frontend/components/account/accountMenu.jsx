import Favorite from './favorite'
import Logout from './logout'
import MyPolls from './myPolls'
import PasswordChange from './passwordChange'

export default function AccountMenu() {
    return (
        <>
            <MyPolls />
            <Favorite />
            <PasswordChange />
            <Logout />
        </>
    )
}
