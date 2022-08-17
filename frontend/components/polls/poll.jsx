import { useState } from "react";
import Box from "@mui/material/Box";
import { useRouter } from "next/router";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import ArrowDropDownCircleIcon from "@mui/icons-material/ArrowDropDownCircle";
import LinearProgress from "@mui/material/LinearProgress";

export default function Poll(props) {
  const router = useRouter();
  const data = props.data;
  const options = data.options;
  const [isExtend, setIsExtend] = useState(false);

  //const map1 = options.map((el) => console.log("map?", el.description));
  const map1 = options.map((el) => {
    return (
      <Box mt={0.5} mr={1} mb={0.5}>
        {el.description}
        <LinearProgress variant="determinate" value={10} />
      </Box>
    );
  });

  const buttonClick = () => {
    const pollId = data.pollId;
    router.push("/voting?pollId=" + pollId);
  };

  const extendClick = () => {
    isExtend === true ? setIsExtend(false) : setIsExtend(true);
    console.log(isExtend);
  };

  return (
    <Box sx={{ ml: -1, mr: -1 }}>
      <Box
        className="upperContainer"
        sx={{ flexDirection: "row", display: "flex" }}
      >
        <Box
          className="ExpireDate"
          sx={{
            padding: 0,
            margin: 0,
            ml: 0.5,
            mt: 1,
            fontSize: 12,
            color: "#808080",
            display: "flex",
            flex: 1,
          }}
        >
          {data.createdAt.substring(0, 10)}
        </Box>

        <Box
          className="link"
          sx={{
            padding: 0,
            margin: 0,
            mr: 0.5,
            mt: 1,
            fontSize: 12,
            color: "#808080",
            display: "flex",
            justifyContent: "right",
            flex: 1,
          }}
        >
          Pending execution
        </Box>
      </Box>
      <Box
        className="outerContainer"
        sx={{
          maxWidth: "100%",
          minHeight: 180,
          mt: 0.6,
          mb: 2,
          borderRadius: "5px",
          padding: 0.5,
          boxShadow: 2,
          letterSpacing: 1.2,
          //border: 1,
          borderColor: "grey.500",
        }}
      >
        <Box
          className="innerContainer"
          sx={{
            border: 0,
            padding: 0,
            maxWidth: "100%",
            height: "100%",
            flexDirection: "row",
            display: "flex",
            //backgroundColor: "rgb(0,0,0)",
          }}
        >
          <Box
            className="infoContainer"
            sx={{
              border: 0,
              padding: 0,
              flex: 1,
              flexDirection: "column",
              display: "flex",
            }}
          >
            <Box
              className="TitleAndProfile"
              sx={{
                flex: 2,
                display: "flex",
                flexDirection: "row",
                //justifyContent: "center",
                //alignItems: "center",
              }}
            >
              <Box
                className="Title"
                sx={{
                  display: "flex",
                  flex: 3,
                  pt: 2,
                  pl: 2.5,
                  fontWeight: "medium",
                  fontSize: 18,
                }}
              >
                {data.title}
              </Box>
              <Box
                className="Profile"
                sx={{
                  display: "flex",
                  flex: 1,
                  pt: 2,
                  pr: 2.5,
                  justifyContent: "right",
                  fontSize: 14,
                  //backgroundColor: "red",
                  color: "#808080",
                }}
              >
                <AccountCircleIcon fontSize="small" sx={{ mr: 0.2 }} />{" "}
                {data.creatorName}
              </Box>
            </Box>
            <Box
              className="Options"
              sx={{
                display: "flex",
                flexDirection: "column",
                flex: 4,
                ml: 2.5,
                mr: 1,
                mb: 3,
                fontSize: 13,
              }}
            >
              {isExtend === false ? map1.slice(0, 2) : map1}
            </Box>

            <Box
              className="Button"
              sx={{
                flex: 1.2,
                flexDirection: "row",
                display: "flex",
                mb: 1.5,
                pb: 0,
                //backgroundColor: "red",
              }}
            >
              <Box
                className="Extend"
                onClick={extendClick}
                sx={{
                  flex: 3,
                  display: "flex",
                  ml: 2,
                  justifyContent: "left",
                  alignItems: "center",
                  fontSize: 14,
                }}
              >
                {map1.length >= 3 ? (
                  <ArrowDropDownCircleIcon sx={{ color: "#808080" }} />
                ) : (
                  <></>
                )}
              </Box>

              <Box
                onClick={buttonClick}
                component="button"
                className="Btn"
                outline="none"
                sx={{
                  flex: 1,
                  display: "flex",
                  mr: 2,
                  justifyContent: "center",
                  alignItems: "center",
                  fontSize: 14,
                  border: 0,
                  borderRadius: "3px",
                  marginBottom: 0,
                }}
              >
                View vote
              </Box>
            </Box>
          </Box>
        </Box>
      </Box>
    </Box>
  );
}
