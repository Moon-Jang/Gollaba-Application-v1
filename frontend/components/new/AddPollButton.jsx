import React from "react";
import { makeStyles } from '@mui/styles';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { Typography } from "@mui/material";
import IdGenerator from "../../utils/IdGenerator";

export default function AddPollButtion({itemsRef, itemListState}) {
    const classes = addPollButtionStyles();
    const [items, setItems] = itemListState;

    const addItem = (event) => {    
        const newItem = { id: IdGenerator.generate(), value: { description: "" }, isInvalid: false };
        items.push(newItem)
        const newItems = [...items];

        itemsRef.current = newItems;
        setItems(newItems)
    }

    return <div className={classes.addPollButtonWrapper} onClick={addItem}>
        <AddCircleIcon className={classes.addIcon} />
        <Typography className={classes.addText} component={'span'} variant={'body2'}>항목 추가</Typography>
    </div>
}

const addPollButtionStyles = makeStyles((themes) => ({
    addPollButtonWrapper: {
        width: "100%",
        height: "32px",
        display: "flex",
        marginTop: themes.spacing(4),
        flexDirection: "row",
        alignItems: "center",
    },
    addIcon: {
        color: "rgba(255, 0, 0, 0.7)",
        width: "28px",
        height: "28px"
    },
    addText: {
        paddingLeft: "8px",
        fontWeight: 700
    }
}))