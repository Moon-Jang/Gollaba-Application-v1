const { configureStore } = require('@reduxjs/toolkit')
const reducer = require('./reducers/index-reducer')

const firstMiddleware = () => (next) => (action) => {
    console.log('log:', action)
    next(action)
}

const store = configureStore({
    reducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(firstMiddleware),
    devTools: process.env.NODE_ENV !== 'production',
})
module.exports = store
