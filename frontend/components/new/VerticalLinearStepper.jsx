import { Button, Paper, Step, StepContent, StepLabel, Stepper, TextField, Typography } from "@mui/material";
import { makeStyles } from "@mui/styles";
import React, { useState } from "react";
import { useRef } from "react";
import CommonValidator from "../../utils/CommonValidator";
import IdGenerator from "../../utils/IdGenerator";
import DraggableList from "./DraggableList";
  
export default function VerticalLinearStepper() {
  const classes = useStyles();
  const [activeStep, setActiveStep] = useState(0);
  const titleRef = useRef({ value: "", isInvalid: false });
  const itemsRef = useRef([{ value: { id: IdGenerator.generate(), description: "test" }, isInvalid: false }, { id: IdGenerator.generate(), value: { description: "test2" }, isInvalid: false }]);
  const optionsValue = useRef({ isBallot : false, responseType: "SINGLE" });
  const steps = getSteps();

  const handleNext = () => {
    if (titleRef.current.isInvalid) return;

    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleReset = () => {
    setActiveStep(0);
  };

  return (
    <div className={classes.root}>
      <Stepper activeStep={activeStep} orientation="vertical">
        {steps.map((label, index) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
            <StepContent>
              <Typography>{getStepContent(index, [titleRef, itemsRef, optionsValue])}</Typography>
              <div className={classes.actionsContainer}>
                <div>
                  <Button
                    disabled={activeStep === 0}
                    onClick={handleBack}
                    className={classes.button}
                  >
                    Back
                  </Button>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={handleNext}
                    className={classes.button}
                  >
                    {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                  </Button>
                </div>
              </div>
            </StepContent>
          </Step>
        ))}
      </Stepper>
      {activeStep === steps.length && (
        <Paper square elevation={0} className={classes.resetContainer}>
          <Typography>All steps completed - you&apos;re finished</Typography>
          <Button onClick={handleReset} className={classes.button}>
            Reset
          </Button>
        </Paper>
      )}
    </div>
  );
}

const useStyles = makeStyles((theme) => ({
  root: {
    flex: 1,
    marginTop: theme.spacing(2),
    width: '100%',
  },
  button: {
    marginTop: theme.spacing(4),
    marginRight: theme.spacing(1),
  },
  actionsContainer: {
    marginBottom: theme.spacing(2),
  },
  resetContainer: {
    padding: theme.spacing(3),
  },
}));
  
function getSteps() {
  return ['투표 주제를 입력해주세요.', '투표 항목을 생성해주세요.', '투표 옵션을 선택해주세요.'];
}

function getStepContent(step, refs) {
  switch (step) {
    case 0:
      return <PollTitle titleRef={refs[step]} />;
    case 1:
      return <PollItems itemsRef={refs[step]} />;
    case 2:
      return `Try out different ad text to see what brings in the most customers,
              and learn how to enhance your ads using features like ad extensions.
              If you run into any problems with your ads, find out how to tell if
              they're running and how to resolve approval issues.`;
    default:
      return 'Unknown step';
  }
}


const pollTitleStyles = makeStyles((themes) => ({
  inputTitle: {
    width:"100%"
  }
}))

function PollTitle({titleRef}) {
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
            helperText={isInvalid ? CommonValidator.pollTitle.message : ""}
            onChange={handleChange} 
            />;
}

function PollItems({itemsRef}) {
  const [items, setItems] = useState(itemsRef.current);

  const onDragEnd = ({ destination, source }) => {
    // dropped outside the list
    if (!destination) return;

    const newItems = reorder(items, source.index, destination.index);

    setItems(newItems);
  };

  const reorder = (items, startIndex, endIndex) => {
    const result = Array.from(items);
    const [removed] = result.splice(startIndex, 1);
    result.splice(endIndex, 0, removed);

    return result;
  }

  return <DraggableList items={itemsRef.current} onDragEnd={onDragEnd} />
  //return itemsRef.current.map(el => <PollItem itemRef={el} />)
}


const pollItemStyles = makeStyles((themes) => ({
  container: {
    width: "100%",
    height: "64px",
    marginTop: themes.spacing(2),
    display: "flex",
    flexDirection: "row",
    alignItems: "center"
  },
  dragIconWrap: {
    width: "40px",
    height: "40px"
  },
  descriptionWrap: {
    flex: 1
  },
  deleteIconWrap: {
    width: "40px",
    height: "40px"
  }
}))

function PollItem({itemRef}) {
  const classes = pollItemStyles();
  const [description, setDescription] = useState(itemRef.value.description);
  const [isInvalid, setIsInvalid] = useState(false);

  return <div className={classes.container}>
    <div className={classes.dragIconWrap}></div>
    <div className={classes.descriptionWrap}>{description}</div>
    <div className={classes.deleteIconWrap}></div>
  </div>;

}