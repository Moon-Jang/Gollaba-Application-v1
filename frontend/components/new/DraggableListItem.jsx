import { makeStyles } from "@mui/styles"
import { Draggable } from "react-beautiful-dnd"
import DragIndicatorIcon from "@mui/icons-material/DragIndicator"
import ClearIcon from "@mui/icons-material/Clear"
import { useState } from "react"
import { TextField, Button, Box } from "@mui/material"
import CommonValidator from "../../utils/CommonValidator"
import { PollItemsContext } from "./VerticalLinearStepper"
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto"
import { width } from "@mui/system"

export default function DraggableListItem({ item, index }) {
    const classes = pollItemStyles()

    return (
        <PollItemsContext.Consumer>
            {(value) => (
                <Draggable draggableId={item.id} index={index}>
                    {(provided, snapshot) => (
                        <PollItem
                            itemRef={item}
                            itemsRef={value.itemsRef}
                            itemListState={value.itemListState}
                            itemIndex={index}
                            dragRef={provided.innerRef}
                            dragHandleProps={provided.draggableProps}
                            draggableProps={provided.dragHandleProps}
                            isDragging={snapshot.isDragging}
                        />
                    )}
                </Draggable>
            )}
        </PollItemsContext.Consumer>
    )
}

const pollItemStyles = makeStyles((themes) => ({
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
        top: 16,
        zIndex: 999,
        cursor: "pointer",
    },
    imageAddIconWrap: {
        position: "absolute",
        right: 35,
        top: 15,
        zIndex: 999,
        cursor: "pointer",
    },
    inputDescription: {
        width: "100%",
    },
}))

function PollItem(props) {
    const {
        itemRef,
        itemsRef,
        itemListState,
        itemIndex,
        dragRef,
        dragHandleProps,
        draggableProps,
        isDragging,
        imageRef,
    } = props
    const classes = pollItemStyles()
    const [description, setDescription] = useState(itemRef.value.description)
    const [imgUrl, setImgUrl] = useState(itemRef.value.imgUrl)
    const [imgPreview, setImgPreview] = useState("")
    const [isInvalid, setIsInvalid] = useState(false)

    const imgHandle = (event) => {
        const value = event.target.files[0]
        itemRef.value.imgUrl = value
        setImgUrl(value)
        setImgPreview(URL.createObjectURL(event.target.files[0]))
    }

    const handleChange = (event) => {
        const { value } = event.target

        if (!CommonValidator.validate("pollItem", value)) {
            itemRef.isInvalid = true
            setIsInvalid(true)
        } else {
            itemRef.isInvalid = false
            setIsInvalid(false)
        }

        itemRef.value.description = value
        setDescription(value)
    }

    const deleteItem = () => {
        const [items, setItems] = itemListState

        if (items.length < 3) {
            alert("항목은 최소 2개 이상이여야 합니다.")
            return
        }

        items.splice(itemIndex, 1)

        const newItem = [...items]
        itemsRef.current = newItem
        setItems(newItem)
    }

    const stopPropagation = (event) => {
        event.preventDefault()
        event.stopPropagation()
    }

    return (
        <>
            <div
                ref={dragRef}
                className={isDragging ? classes.containerWithDragging : classes.container}
                {...dragHandleProps}
                {...draggableProps}
                // style={{background: isDragging ? 'rgb(235,235,235)' : ''}}
            >
                <div className={classes.dragIconWrap} onClick={stopPropagation}>
                    <DragIndicatorIcon />
                </div>
                <div className={classes.descriptionWrap}>
                    <TextField
                        className={classes.inputDescription}
                        label=""
                        variant="outlined"
                        name="pollTitle"
                        fullWidth={true}
                        value={description}
                        error={isInvalid}
                        placeholder={"항목" + (itemIndex + 1)}
                        helperText={isInvalid ? CommonValidator.pollItem.message : ""}
                        onChange={handleChange}
                    />
                    <div className={classes.imageAddIconWrap}>
                        <Button
                            variant="contained"
                            component="label"
                            sx={{ backgroundColor: "transparent", boxShadow: 0, mt: -0.8, mr: -2 }}
                        >
                            <AddAPhotoIcon style={{ color: "rgba(255, 0, 0, 0.55)" }} />
                            <input type="file" hidden accept="image/*" onChange={imgHandle} />
                        </Button>
                    </div>

                    <div className={classes.deleteIconWrap} onClick={deleteItem}>
                        <ClearIcon style={{ color: "rgba(255, 0, 0, 0.55)" }} />
                    </div>
                </div>
            </div>
            <div align="center">{imgPreview && <img src={imgPreview} height={100} />}</div>
        </>
    )
}
