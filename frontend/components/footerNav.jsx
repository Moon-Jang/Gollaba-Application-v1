import React, { Component, useState } from 'react'
import { Link } from 'react-router-dom'

import Paper from '@mui/material/Paper'
import BottomNavigation from '@mui/material/BottomNavigation'
import BottomNavigationAction from '@mui/material/BottomNavigationAction'

import PollOutlinedIcon from '@mui/icons-material/PollOutlined'
import ThumbUpOutlinedIcon from '@mui/icons-material/ThumbUpOutlined'
import AddOutlinedIcon from '@mui/icons-material/AddOutlined'
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined'
import { useRouter } from 'next/router'

export default function FooterNav() {
    const [value, setValue] = useState(0)
    const router = useRouter()
    return (
        <Paper
            sx={{ position: 'fixed', bottom: 0, left: 0, right: 0 }}
            elevation={3}
        >
            <BottomNavigation
                sx={{}}
                showLabels
                // fullWidth
                value={value}
                onChange={(event, newValue) => {
                    setValue(newValue)
                }}
            >
                <BottomNavigationAction
                    label='Polls'
                    onClick={() => {
                        router.push('/')
                    }}
                    icon={<PollOutlinedIcon />}
                />
                <BottomNavigationAction
                    label='Voting'
                    onClick={() => {
                        router.push('/voting')
                    }}
                    icon={<ThumbUpOutlinedIcon />}
                />
                <BottomNavigationAction
                    label='New'
                    onClick={() => {
                        router.push('/new')
                    }}
                    icon={<AddOutlinedIcon />}
                />
                <BottomNavigationAction
                    label='Account'
                    icon={<AccountCircleOutlinedIcon />}
                />
            </BottomNavigation>
        </Paper>
    )
}
