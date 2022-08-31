import Favorite from './favorite'
import Logout from './logout'
import MyPoll from './myPoll'
import PasswordChange from './passwordChange'

export default function AccountMenu() {
    return (
        <>
            <MyPoll />
            <Favorite />
            <PasswordChange />
            <Logout />
        </>
    )
}
