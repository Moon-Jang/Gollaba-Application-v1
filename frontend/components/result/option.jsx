import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { Checkbox, TextField } from "@mui/material";
import { ConstructionOutlined } from "@mui/icons-material";

const label = { inputProps: { "aria-label": "Checkbox demo" } };
export default function Option(props) {
  const data = props.data;
  const chosenOption = props.chosenOption;
  const voted = props.voted;
  const totalVoteCount = props.totalVoteCount;
  const ratio = (props.data.voters.length / totalVoteCount) * 100;

  useEffect(() => {}, [chosenOption]);
  return (
    <Box
      className="outerContainer"
      compomemt="button"
      backgroundColor={
        voted.indexOf(data.optionId) === -1
          ? "rgb(230, 230, 230)"
          : "rgb(130, 130, 130)"
      }
      sx={{
        maxWidth: "100%",
        mt: 1,
        mb: 1,
        borderRadius: "5px",
        padding: 0.5,
        pl: 2,
        pt: 1,
        boxShadow: 2,
        letterSpacing: 1.2,
        display: "flex",
        borderColor: "grey.500",
        flexDirection: "row",
        justifyContent: "left",
        alignItems: "center",
        fontSize: 22,
        flex: 0.15,
        background: `linear-gradient(to right,  #9c9e9f 0%,#9c9e9f ${ratio}% ,#f6f6f6  ${ratio}%,#f6f6f6 100%)`,
      }}
    >
      {data.description}
      <Box
        sx={{
          display: "flex",
          fontSize: 13,
          flex: 0.2,
          pt: 2,
        }}
      >
        ({props.data.voters.length}명)
      </Box>
    </Box>
  );
}
