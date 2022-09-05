const { combineReducers } = require('redux')
const userSlice = require('./user')
// const todoSlice = require("./todo")

module.exports = combineReducers({
    user: userSlice.reducer,
})
