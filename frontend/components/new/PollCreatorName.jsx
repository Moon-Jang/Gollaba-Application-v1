import { TextField } from "@mui/material";
import React, { useState } from "react";
import { makeStyles } from "@mui/styles";
import CommonValidator from "../../utils/CommonValidator";
  
export default function PollCreatorName({nameRef}) {
    const classes = pollnameStyles();
    const [isInvalid, setIsInvalid] = useState(false);
    const [name, setName] = useState(nameRef.current.value);

    const handleChange = (event) => {
        const {value} = event.target;

        if (!CommonValidator.validate("pollcreatorName", value)) {
        nameRef.current.isInvalid = true;
        setIsInvalid(true)
        } else {
        nameRef.current.isInvalid = false;
        setIsInvalid(false)
        }

        nameRef.current.value = value;
        setName(value);
    };

    return <TextField className={classes.inputname}
                label=""
                variant="standard"
                name="pollcreatorName"
                value={name}
                error={isInvalid}
                placeholder="작성자 이름"
                helperText={isInvalid ? CommonValidator.pollcreatorName.message : ""}
                onChange={handleChange} 
                />;
}

const pollnameStyles = makeStyles((themes) => ({
    inputname: {
        width:"100%"
    }
}))