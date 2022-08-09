import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { Checkbox, TextField } from "@mui/material";

const label = { inputProps: { "aria-label": "Checkbox demo" } };
export default function Option(props) {
  const data = props.data;
  const index = props.index + 1;
  const chosenOption = props.chosenOption;
  const setChosenOption = props.setChosenOption;

  const [color, setColor] = useState("rgb(230,230,230)");

  const optionClick = () => {
    console.log("cliekc");
    setChosenOption(index - 1);
    console.log("chosen>>", chosenOption);
    color === "rgb(230,230,230)"
      ? setColor("rgb(160,160,160)")
      : setColor("rgb(230,230,230)");
  };

  useEffect(() => {
    console.log("useEffect !!");
  }, [chosenOption]);

  return (
    <Box
      className="outerContainer"
      compomemt="button"
      onClick={optionClick}
      backgroundColor={color}
      sx={{
        maxWidth: "100%",
        height: 60,
        mt: 2,
        mb: 2,
        borderRadius: "10px",
        padding: 1,
        flexDirection: "row",
        display: "flex",
      }}
    >
      <Box
        className="numbering"
        sx={{
          display: "flex",
          flex: 1,
          justifyContent: "center",
          alignItems: "center",
          fontSize: 30,
          letterSpacing: -5,
        }}
      >
        {index}
      </Box>
      <Box
        className="optionName"
        sx={{
          display: "flex",
          flex: 10,
          justifyContent: "center",
          alignItems: "center",
          fontSize: 40,
          letterSpacing: -5,
        }}
      >
        <Box
          sx={{
            display: "flex",
            flex: 10,
            justifyContent: "center",
            alignItems: "center",
            fontSize: 35,
            letterSpacing: 0,
          }}
        >
          {data.description}
        </Box>
      </Box>
    </Box>
  );
}
