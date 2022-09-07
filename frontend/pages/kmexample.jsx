import KMExample from '../components/account/example'
import { UserProvider } from '../components/account/ExUserContext'

export default function Example() {
    return (
        <>
            <UserProvider>
                <KMExample />
            </UserProvider>
        </>
    )
}
