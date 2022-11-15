import React from "react";
import { useRouter } from "next/router";
import Box from "@mui/material/Box";
import ShareIcon from "@mui/icons-material/Share";

import {
  FacebookShareButton,
  FacebookIcon,
  FacebookMessengerShareButton,
  FacebookMessengerIcon,
  TwitterShareButton,
  TwitterIcon,
  LineShareButton,
  LineIcon,
} from "react-share";

const label = { inputProps: { "aria-label": "Checkbox demo" } };
export default function ShareBar(props) {
  const router = useRouter();
  const currentUrl = "http://localhost:3000" + router.asPath;
  const clipboardCopy = () => {
    navigator.clipboard.writeText(currentUrl);
    alert("클립보드에 복사되었습니다.");
  };

  return (
    <Box
      className="outerContainer"
      sx={{
        maxWidth: "100%",
        height: "40px",
        mt: 0.5,
        mb: 2,
        display: "flex",
        flexDirection: "row",
        justifyContent: "right",
      }}
    >
      <Box
        component={"button"}
        onClick={clipboardCopy}
        sx={{
          display: "flex",
          backgroundColor: "coral",
          borderRadius: "50%",
          width: 30,
          height: 30,
          justifyContent: "center",
          alignItems: "center",
          mt: 0.2,
          mr: "10px",
          border: "none",
        }}
      >
        <ShareIcon fontSize="2" sx={{ color: "white" }} />
      </Box>

      <FacebookShareButton style={{ marginRight: "10px" }} url={currentUrl}>
        <FacebookIcon size={30} round={true} borderRadius={24}></FacebookIcon>
      </FacebookShareButton>
      <FacebookMessengerShareButton
        style={{ marginRight: "10px" }}
        url={currentUrl}
      >
        <FacebookMessengerIcon
          size={30}
          round={true}
          borderRadius={24}
        ></FacebookMessengerIcon>
      </FacebookMessengerShareButton>
      <TwitterShareButton style={{ marginRight: "10px" }}>
        <TwitterIcon
          size={30}
          round={true}
          borderRadius={24}
          url={currentUrl}
        ></TwitterIcon>
      </TwitterShareButton>
      <LineShareButton>
        <LineIcon
          size={30}
          round={true}
          borderRadius={24}
          url={currentUrl}
        ></LineIcon>
      </LineShareButton>
    </Box>
  );
}
