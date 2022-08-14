import * as React from "react";
import axios from "axios";
import Box from "@mui/material/Box";
import { Checkbox, TextField } from "@mui/material";
import jwt from "jsonwebtoken";

const label = { inputProps: { "aria-label": "Checkbox demo" } };

export default function CreateBtn(props) {
  const btnClicked = async () => {
    console.log("clicked");
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
      투표하기
    </Box>
  );
}
