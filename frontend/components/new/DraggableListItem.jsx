import { Avatar, ListItem, ListItemAvatar, ListItemText } from '@mui/material';
import { makeStyles } from "@mui/styles";
import { Draggable } from 'react-beautiful-dnd';
import DragIndicatorIcon from '@mui/icons-material/DragIndicator';

const useStyles = makeStyles({
  draggingListItem: {
    background: 'rgb(235,235,235)'
  }
});


export default function DraggableListItem({ item, index }) {
  const classes = useStyles();
  return (
    <Draggable draggableId={item.id} index={index}>
      {(provided, snapshot) => (
        <ListItem
          ref={provided.innerRef}
          {...provided.draggableProps}
          {...provided.dragHandleProps}
          className={snapshot.isDragging ? classes.draggingListItem : ''}
        >
          <ListItemAvatar>
            <Avatar>
                <DragIndicatorIcon />
            </Avatar>
          </ListItemAvatar>
          <ListItemText primary={item.primary} secondary={"test secondary"} />
        </ListItem>
      )}
    </Draggable>
  );
};