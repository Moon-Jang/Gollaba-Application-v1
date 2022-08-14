import * as React from "react";
import Box from "@mui/material/Box";
import { Checkbox, TextField } from "@mui/material";

const label = { inputProps: { "aria-label": "Checkbox demo" } };
export default function Description(props) {
  const data = props.data;

  return (
    <Box
      className="outerContainer"
      sx={{
        maxWidth: "100%",
        height: 150,
        mt: 2,
        mb: 2,
        borderRadius: "15px",
        padding: 1,
        backgroundColor: "rgb(230,230,230)",
      }}
    >
      <Box
        className="innerContainer"
        sx={{
          maxWidth: "100%",
          height: 130,
          borderRadius: "5px",
          flexDirection: "row",
          display: "flex",
        }}
      >
        <Box
          className="infoContainer"
          sx={{
            flex: 15,
            flexDirection: "column",
            display: "flex",
          }}
        >
          <Box
            className="Title"
            sx={{
              flex: 2,
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              fontWeight: "bold",
              fontSize: 17,
            }}
          >
            {data.title}
          </Box>
          <Box
            className="CheckBoxes"
            sx={{ flex: 1, flexDirection: "row", display: "flex" }}
          >
            <Box
              classname="Name"
              sx={{
                flex: 1,
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                fontSize: 14,
              }}
            >
              {data.creatorName}
            </Box>
            <Box
              classname="Date"
              sx={{
                flex: 1,
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                fontSize: 14,
              }}
            >
              {data.createdAt}
            </Box>
          </Box>
        </Box>
      </Box>
    </Box>
  );
}
