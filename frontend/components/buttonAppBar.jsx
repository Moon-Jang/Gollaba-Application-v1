import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";

import NotificationsIcon from "@mui/icons-material/Notifications";

export default function ButtonAppBar(title) {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="fixed" color="default" sx={{ boxShadow: "none" }}>
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            {title.titletext}
          </Typography>
          <Button
            color="inherit"
            onClick={() => {
              alert("미구현 기능입니다.");
            }}
            sx={{ mr: -2 }}
          >
            <NotificationsIcon sx={{}} />
          </Button>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
