import * as React from 'react';

import Paper from '@mui/material/Paper';
import BottomNavigation from "@mui/material/BottomNavigation";
import BottomNavigationAction from "@mui/material/BottomNavigationAction";

import PollOutlinedIcon from "@mui/icons-material/PollOutlined";
import ThumbUpOutlinedIcon from "@mui/icons-material/ThumbUpOutlined";
import AddOutlinedIcon from "@mui/icons-material/AddOutlined";
import AccountCircleOutlinedIcon from "@mui/icons-material/AccountCircleOutlined";

export default function FooterNav() {
      return(
        <Paper sx={{ position: 'fixed', bottom: 0, left: 0, right: 0 }} elevation={3}>
            <BottomNavigation
              sx={{  }}
              showLabels
              fullWidth
              //value={value}
              onChange={(event, newValue) => {
                setValue(newValue);
              }}
            >
              <BottomNavigationAction
                label="Polls"
                icon={<PollOutlinedIcon />}
              />
              <BottomNavigationAction
                label="Voting"
                icon={<ThumbUpOutlinedIcon />}
              />
              <BottomNavigationAction label="New" icon={<AddOutlinedIcon />} />
              <BottomNavigationAction
                label="Account"
                icon={<AccountCircleOutlinedIcon />}
              />
            </BottomNavigation>
            </Paper>
            )
        
}