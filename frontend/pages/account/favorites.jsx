import CssBaseline from '@mui/material/CssBaseline'
import Container from '@mui/material/Container'
import Box from '@mui/material/Box'
import { createTheme, ThemeProvider } from '@mui/material/styles'
import { useState } from 'react'
import ButtonAppBar from '../../components/buttonAppBar'
import FooterNav from '../../components/footerNav'
import PollsMap from '../../components/polls/mapPoll'
import { useInView } from 'react-intersection-observer'
import theme from '../../src/theme'
import axios from 'axios'
import { useEffect } from 'react'
import PollsMapFavorite from '../../components/account/mapPollFavorite'

const PollTheme = createTheme(theme)

export default function Favorites(props) {
    let response
    const [polls, setPolls] = useState([])
    const [ref, inView] = useInView()
    const [isLoading, setIsLoading] = useState(false)
    const [offset, setOffset] = useState(0)
    const [totalCount, setTotalCount] = useState(0)

    const getData = async () => {
        if (totalCount !== 0 && offset * 1 >= totalCount) return
        setIsLoading(true)
        try {
            response = await axios.get(
                `https://dev.api.gollaba.net/v1/polls?limit=15&offset=${
                    offset * 1
                }`
            )
            console.log('res>', response.data)
            let arr = [...polls, ...response.data.polls]
            setPolls(arr)
            setTotalCount(response.data.totalCount)
            setIsLoading(false)
        } catch (e) {
            response = e.response
            alert(response.data.error.message)
        } finally {
            return response
        }
    }

    useEffect(() => {
        getData()
    }, [offset])

    useEffect(() => {
        if (inView && !isLoading) {
            setOffset((prevState) => prevState + 1)
        }
    }, [inView, isLoading])

    return (
        <>
            <ThemeProvider theme={theme}>
                <Container component='main' maxwidth='xs'>
                    <CssBaseline />
                    <Box
                        sx={{
                            marginTop: 7,
                            marginBottom: 10,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'left',
                            justifyContent: 'center',
                        }}
                    >
                        <div className='header'>
                            <ButtonAppBar titletext={'Favorites'} />
                        </div>

                        <div className='body' flex='1'>
                            <PollsMapFavorite data={polls} />
                            <Box ref={ref} />
                        </div>
                        <div className='footer'>
                            <FooterNav />
                        </div>
                    </Box>
                </Container>
            </ThemeProvider>
        </>
    )
}
