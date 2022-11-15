import { makeStyles } from "@mui/styles"
import { Draggable } from "react-beautiful-dnd"
import DragIndicatorIcon from "@mui/icons-material/DragIndicator"
import ClearIcon from "@mui/icons-material/Clear"
import { useState } from "react"
import { TextField } from "@mui/material"
import CommonValidator from "../../utils/CommonValidator"
import { PollItemsContext } from "./VerticalLinearStepper"

export default function BeforeListItem({ item, index, deleteItem, itemListState }) {
    //const classes = pollItemStyles()

    return <>hgellsad</>
}

const pollItemStyles = makeStyles(themes => ({
    container: {
        width: "100%",
        height: "64px",
        marginTop: themes.spacing(2),
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
    },
    containerWithDragging: {
        width: "100%",
        height: "64px",
        marginTop: themes.spacing(2),
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        background: "rgb(235,235,235)",
    },
    dragIconWrap: {
        display: "flex",
        alignItems: "center",
        width: "24px",
        height: "100%",
        marginRight: "8px",
    },
    descriptionWrap: {
        flex: 1,
        position: "relative",
    },
    deleteIconWrap: {
        position: "absolute",
        right: 8,
        top: 14,
        zIndex: 999,
        cursor: "pointer",
    },
    inputDescription: {
        width: "100%",
    },
}))
