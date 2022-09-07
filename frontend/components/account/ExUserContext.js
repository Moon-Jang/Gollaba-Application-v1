import React, { createContext, useContext, useReducer } from 'react'

const initialState = {
    userList: [],
    user: null,
}

const reducer = (state, action) => {
    switch (action.type) {
        case 'CREATE_USER':
            return {
                ...state,
                userList: state.userList.concat(action.user),
            }
        case 'LOGIN':
            return {
                ...state,
                user: {
                    userId: action.userId,
                },
            }
        case 'LOGOUT':
            return {
                ...state,
                user: null,
            }
        default:
            return state
    }
}

const UserStateContext = createContext(null)
const UserDispatchContext = createContext(null)

export const UserProvider = ({ children }) => {
    const [state, dispatch] = useReducer(reducer, initialState)

    return (
        <UserStateContext.Provider value={state}>
            <UserDispatchContext.Provider value={dispatch}>
                {children}
            </UserDispatchContext.Provider>
        </UserStateContext.Provider>
    )
}
