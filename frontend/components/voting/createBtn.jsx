import React, { useState } from "react";
import axios from "axios";
import Box from "@mui/material/Box";
import { Checkbox, TextField } from "@mui/material";
import { CookiesProvider, useCookies } from "react-cookie";
import jwt_decode from "jwt-decode";

const label = { inputProps: { "aria-label": "Checkbox demo" } };

export default function CreateBtn(props) {
  const [nickname, setNickname] = useState("");
  const [cookies, setCookies, removeCookies] = useCookies(null);

  const nicknameChanged = (event) => {
    setNickname(event.target.value);
  };

  const btnClicked = async () => {
    const userId = null;
    if (cookies) {
      const decoded = jwt_decode(cookies.accessToken);
      userId = decoded.id;
    }

    const payload = {
      optionIds: props.voted,
      pollId: props.pollId,
      userId: userId,
      voterName: nickname,
    };

    console.log(payload, "clicked");
  };

  return (
    <Box sx={{ display: "flex", flexDirection: "row" }}>
      <Box
        className="nickname"
        sx={{
          flex: 3,
          pr: 3,
          pt: 2,
        }}
      >
        <TextField
          label="닉네임"
          variant="outlined"
          size="small"
          onChange={nicknameChanged}
        />
      </Box>
      <Box
        className="button"
        component={"button"}
        onClick={btnClicked}
        sx={{
          flex: 2,
          maxWidth: "100%",
          height: 40,
          mt: 2,
          mb: 2,
          padding: 1,
          border: 1,
          borderRadius: "5px",
          padding: 0.5,
          boxShadow: 2,
          display: "flex",
          border: 1,
          flexDirection: "row",
          alignItems: "center",
          justifyContent: "center",
          textAlign: "center",
          fontSize: 15,
          border: "none",
        }}
      >
        투표하기
      </Box>
    </Box>
  );
}
