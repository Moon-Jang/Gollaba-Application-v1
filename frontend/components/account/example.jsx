export default function KMExample() {
    const { user } = useUserState()
    return (
        <>
            <container>
                <span>아이디 : {user.userId}</span>
            </container>
        </>
    )
}
