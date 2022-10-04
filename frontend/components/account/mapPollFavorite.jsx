import React from 'react'
import Poll from '../polls/poll'

const PollsMapFavorite = (props) => {
    const data = props.data
    return data.map((el) => <Poll data={el} />)
}
export default PollsMapFavorite
