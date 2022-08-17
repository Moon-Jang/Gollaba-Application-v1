import { TextField } from "@mui/material";
import React, { useState } from "react";
import { makeStyles } from "@mui/styles";
import CommonValidator from "../../utils/CommonValidator";
  
export default function PollTitle({titleRef}) {
    const classes = pollTitleStyles();
    const [isInvalid, setIsInvalid] = useState(false);
    const [title, setTitle] = useState(titleRef.current.value);

    const handleChange = (event) => {
        const {value} = event.target;

        if (!CommonValidator.validate("pollTitle", value)) {
        titleRef.current.isInvalid = true;
        setIsInvalid(true)
        } else {
        titleRef.current.isInvalid = false;
        setIsInvalid(false)
        }

        titleRef.current.value = value;
        setTitle(value);
    };

    return <TextField className={classes.inputTitle}
                label=""
                variant="standard"
                name="pollTitle"
                value={title}
                error={isInvalid}
                placeholder="투표 주제"
                helperText={isInvalid ? CommonValidator.pollTitle.message : ""}
                onChange={handleChange} 
                />;
}

const pollTitleStyles = makeStyles((themes) => ({
    inputTitle: {
        width:"100%"
    }
}))