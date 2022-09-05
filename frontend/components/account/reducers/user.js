const { createSlice } = require('@reduxjs/toolkit')
const { login } = require('../actions/user')

const initialState = {
    isLoggingIn: false,
    isLoggedIn: true,
    loginError: false,
    data: {
        id: 0,
        nickname: '[사용자 닉네임]',
    },
}

const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        editNickname(state, action) {
            state.data.nickname = action.payload
        },
    },
    extraReducers: {
        [login.pending](state, action) {
            state.isLoggingIn = true
        },
        [login.fulfilled](state, action) {
            state.isLoggingIn = false
            state.isLoggedIn = true
            state.data = action.payload
        },
        [login.rejected](state, action) {
            state.isLoggingIn = false
            state.data = null
        },
    },
})
module.exports = userSlice
