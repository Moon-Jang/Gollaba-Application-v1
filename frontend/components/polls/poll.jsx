import * as React from "react";
import Box from "@mui/material/Box";
import { useRouter } from "next/router";

export default function Poll(props) {
  const router = useRouter();
  const data = props.data;

  const buttonClick = () => {
    const pollId = data.pollId;
    router.push("/voting?pollId=" + pollId);
    //router.push(`/voting/${pollId}`);
  };

  return (
    <Box
      className="outerContainer"
      sx={{
        maxWidth: "100%",
        height: 130,
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
          height: 110,
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
            className="NameAndDate"
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
                flex: 2,
                display: "flex",
                mr: 2,
                justifyContent: "right",
                alignItems: "center",
                fontSize: 14,
              }}
            >
              {data.createdAt.substring(0, 10)}
            </Box>
          </Box>
        </Box>
        <Box
          onClick={buttonClick}
          component="button"
          className="Btn"
          sx={{
            flex: 1,
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            outline: "none",
            border: "none",
            backgroundColor: "rgb(230,230,230)",
          }}
        >
          ▶
        </Box>
      </Box>
    </Box>
  );
}
