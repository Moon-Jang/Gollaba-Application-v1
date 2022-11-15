import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { Checkbox, TextField } from "@mui/material";
import { ConstructionOutlined } from "@mui/icons-material";

const label = { inputProps: { "aria-label": "Checkbox demo" } };
export default function Option(props) {
  const data = props.data;
  const index = props.index;
  const chosenOption = props.chosenOption;
  const voted = props.voted;
  const setVoted = props.setVoted;
  const responseType = props.responseType;

  const optionClick = () => {
    //단일투표 && 비활성화
    if (responseType === "SINGLE" && voted.indexOf(data.optionId) === -1) {
      if (voted.length >= 1) voted.pop();
      setVoted([...voted, data.optionId]);
      return;
    }

    //복수투표 && 비활성화
    if (responseType === "MULTI" && voted.indexOf(data.optionId) === -1) {
      setVoted([...voted, data.optionId]);
      return;
    }

    //복수투표 && 활성화
    if (responseType === "MULTI" && voted.indexOf(data.optionId) !== -1) {
      voted.splice(voted.indexOf(data.optionId), 1);

      setVoted([...voted, data.optionId]);
      return;
    }

    //단일투표 && 활성화
    voted.splice(voted.indexOf(data.optionId), 1);
    setVoted([...voted]);
  };

  useEffect(() => {}, [chosenOption]);

  return (
    <Box
      className="outerContainer"
      compomemt="button"
      onClick={optionClick}
      backgroundColor={
        voted.indexOf(data.optionId) === -1
          ? "rgb(230, 230, 230)"
          : "rgb(130, 130, 130)"
      }
      sx={{
        maxWidth: "100%",
        mt: 1.5,
        mb: 1.5,
        borderRadius: "5px",
        padding: 0.5,
        boxShadow: 2,
        letterSpacing: 1.2,
        display: "flex",
        borderColor: "grey.500",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        fontSize: 22,
        flex: 0.1,
      }}
    >
      {data.description}
    </Box>
  );
}
