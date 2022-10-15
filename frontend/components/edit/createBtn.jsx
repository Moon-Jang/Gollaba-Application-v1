import * as React from "react";
import axios from "axios";
import Box from "@mui/material/Box";
import { Cookies, useCookies } from "react-cookie";
import { Checkbox, TextField } from "@mui/material";
import jwt from "jsonwebtoken";

const label = { inputProps: { "aria-label": "Checkbox demo" } };

export default function CreateBtn(props) {
  const [cookies, setCookies, removeCookies] = useCookies([]);

  const btnClicked = async () => {
    const decoded = jwt.decode(cookies.accessToken);
    const time = new Date();
    const endTime = new Date(time.setDate(time.getDate() + 7));
    endTime = endTime.toISOString();
    const options = props.pollingItems.map((a) => ({
      description: a.description,
    }));
    const payload = {
      userId: cookies.accessToken !== null ? decoded.id : null,
      title: props.title,
      creatorName: cookies.accessToken !== null ? decoded.un : null,
      responseType: props.responseType,
      isBallot: props.isBallot,
      options: options,
      endedAt: endTime,
    };

    console.log("payload>>", payload);

    let response;
    try {
      response = await axios.post(
        "https://dev.api.gollaba.net/v1/polls",
        payload
      );
      router.push("/polls");
    } catch (e) {
      response = e.response;
      alert(response.data.error.message);
    } finally {
      return response;
    }
  };

  return (
    <Box
      className="outerContainer"
      component={"button"}
      onClick={btnClicked}
      sx={{
        maxWidth: "100%",
        height: 50,
        mt: 2,
        mb: 2,
        borderRadius: "10px",
        padding: 1,
        backgroundColor: "rgb(130, 130, 130)",
        flexDirection: "row",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
        fontSize: 30,
        border: "none",
      }}
    >
      생성
    </Box>
  );
}
