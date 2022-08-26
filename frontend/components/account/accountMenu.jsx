import Logout from './logout'
import PasswordChange from './passwordChange'

export default function AccountMenu() {
    return (
        <>
            <div>내 투표</div>
            <div>즐겨찾기</div>
            <div>비밀번호 변경</div>
            <PasswordChange />
            <div>로그아웃</div>
            <Logout />
        </>
    )
}
