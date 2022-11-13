import {
    Button,
    Checkbox,
    CircularProgress,
    Paper,
    Step,
    StepContent,
    StepLabel,
    Stepper,
    TextField,
    Typography,
} from "@mui/material"
import { makeStyles } from "@mui/styles"
import React, { useState, useEffect } from "react"
import { useRef } from "react"
import IdGenerator from "../../utils/IdGenerator"
import DraggableList from "./DraggableList"
import AddPollButtion from "./AddPollButton"
import PollTitle from "./PollTitle"
import ApiGateway from "../../apis/ApiGateway"
import { useRouter } from "next/router"
import PollCreatorName from "./PollCreatorName"
import { formatDiagnosticsWithColorAndContext } from "typescript"
import DatePicker from "react-datepicker"
import "react-datepicker/dist/react-datepicker.css"
import { BeforeListItem } from "./BeforeListItem"
import { useCookies } from "react-cookie"
//import { ko } from "date-fns/esm/locale";

const RESPONSE_TYPE_SIGNLE = "SINGLE"
const RESPONSE_TYPE_MULTI = "MULTI"

const steps = [
    "수정할 투표 제목를 입력해주세요.",
    "수정할 투표 이미지를 삽입해주세요. (선택사항 입니다.)",
    "투표 항목을 추가해주세요.",
    "변경할 투표 마감일을 선택해주세요.",
]

export default function VerticalLinearStepper() {
    const classes = useStyles()
    const router = useRouter()
    let response
    const [polls, setPolls] = useState([])
    const [cookies, setCookies] = useCookies()
    const { pollId } = router.query
    console.log("zt", pollId)

    const getData = async () => {
        response = await ApiGateway.getPoll(pollId)
        setPolls(response)
    }

    useEffect(() => {
        if (pollId) {
            getData()
        }
    }, [pollId])

    console.log("response>>2", polls)

    const [activeStep, setActiveStep] = useState(0)
    const [isSubmit, setIsSubmit] = useState(false)
    const titleRef = useRef({ value: polls.title, isInvalid: false })
    const imageRef = useRef({})
    /*
    const itemsRef = useRef([
        {
            id: IdGenerator.generate(),
            value: { description: "" },
            isInvalid: false,
        },
        {
            id: IdGenerator.generate(),
            value: { description: "" },
            isInvalid: false,
        },
    ])*/
    const itemsRef = useRef([])
    const expireRef = useRef({})
    const handleNext = () => {
        if (isSubmit) return

        switch (activeStep) {
            case 0:
                if (titleRef.current.isInvalid) return
                break
            case 1:
                //if (imageRef.current.value !== "") return;
                break

            case 2:
                if (itemsRef.current.some(el => el.isInvalid === true)) return
                break

            case 3:
                console.log("submit 가동")
                handleSubmmit()
                break
            default:
                console.error("존재하지 않는 스텝입니다.")
        }

        setActiveStep(prevActiveStep => prevActiveStep + 1)
    }

    const handleSubmmit = async () => {
        //setIsSubmit(true)

        if (itemsRef.current.some(el => el.value.description === "")) {
            alert("빈 항목이 존재합니다. 항목을 수정해주세요.")
            setIsSubmit(false)
            return
        }
        const currentTimestamp = +new Date()
        const defaultEndedAt = new Date(currentTimestamp + 1000 * 60 * 60 * 24 * 7).toISOString()

        const formData = new FormData()

        if (titleRef.current.value !== undefined) formData.append("title", titleRef.current.value)
        if (JSON.stringify(expireRef.current).length !== 2) formData.append("endedAt", expireRef.current.toISOString())
        if (JSON.stringify(imageRef.current.name) !== undefined) formData.append("pollImage", imageRef.current)

        for (let i = 0; i < itemsRef.current.length; i++) {
            formData.append(`options[${i}].description`, itemsRef.current[i].value.description)
        }

        const res = await ApiGateway.updatePoll(polls.pollId, formData, cookies.accessToken)

        if (res?.error) {
            alert(res.message)
            setIsSubmit(false)
            return
        }

        router.push("/polls/" + pollId)
    }

    const handleBack = () => {
        setActiveStep(prevActiveStep => prevActiveStep - 1)
    }

    return (
        <div className={classes.root}>
            <Stepper activeStep={activeStep} orientation="vertical">
                {steps.map((label, index) => (
                    <Step key={label}>
                        <StepLabel>{label}</StepLabel>
                        <StepContent>
                            {getStepContent(index, [titleRef, imageRef, itemsRef, expireRef], polls)}
                            <div className={classes.actionsContainer}>
                                <div>
                                    <Button disabled={activeStep === 0} onClick={handleBack} className={classes.button}>
                                        이전
                                    </Button>
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        onClick={handleNext}
                                        className={classes.button}
                                    >
                                        {isSubmit && <CircularProgress size={24} color="info" />}
                                        {!isSubmit && (activeStep === steps.length - 1 ? "완료" : "다음")}
                                    </Button>
                                </div>
                            </div>
                        </StepContent>
                    </Step>
                ))}
            </Stepper>
        </div>
    )
}

const useStyles = makeStyles(theme => ({
    root: {
        flex: 1,
        marginTop: theme.spacing(2),
        width: "100%",
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
}))

function getStepContent(step, refs, polls) {
    console.log("Step", polls.title)
    console.log("만기1", polls.endedAt)
    switch (step) {
        case 0:
            return <PollTitle originTitle={polls.title} titleRef={refs[step]} />
        case 1:
            return <PollImage imageRef={refs[step]} originImage={polls.pollImageUrl} />
        case 2:
            return <PollItemsWrapper itemsRef={refs[step]} options={polls.options} />
        case 3:
            return <PollExpireDate endedAt={polls.endedAt} expireRef={refs[step]} />
        default:
            return "Unknown step"
    }
}

export const PollItemsContext = React.createContext(null)

function PollItemsWrapper({ itemsRef, options }) {
    const itemListState = useState(itemsRef.current)
    console.log("options>>", options)

    const beforeOptions = options.map((el, index) => (
        <text>
            {el.description}
            <br />
        </text>
    ))

    return (
        <PollItemsContext.Provider value={{ itemsRef, itemListState }}>
            <PollItems itemsRef={itemsRef} itemListState={itemListState} />
            <AddPollButtion itemsRef={itemsRef} itemListState={itemListState} />
        </PollItemsContext.Provider>
    )
}

function PollItems({ itemsRef, itemListState, options }) {
    const [items, setItems] = itemListState

    const onDragEnd = ({ destination, source }) => {
        // dropped outside the list
        if (!destination) return

        const newItems = reorder(items, source.index, destination.index)

        itemsRef.current = newItems
        setItems(newItems)
    }

    const reorder = (items, startIndex, endIndex) => {
        const result = Array.from(items)
        const [removed] = result.splice(startIndex, 1)
        result.splice(endIndex, 0, removed)

        return result
    }

    return (
        <>
            <DraggableList items={itemsRef.current} onDragEnd={onDragEnd} />
        </>
    )
}

const pollOptionStyles = makeStyles(theme => ({
    container: {
        width: "100%",
        height: "100px",
        display: "flex",
        flexDirection: "column",
    },
    wrapper: {
        width: "100%",
        height: "40px",
        marginTop: "8px",
    },
    actionsContainer: {
        marginBottom: theme.spacing(2),
    },
    resetContainer: {
        padding: theme.spacing(3),
    },
}))

function PollOptionsWrapper({ optionsRef }) {
    const classes = pollOptionStyles()
    const [checkedIsBallot, setCheckedIsBallot] = useState(optionsRef.current.isBallot)
    const [checkedResponseType, setCheckedResponseType] = useState(
        optionsRef.current.responseType === RESPONSE_TYPE_SIGNLE ? false : true
    )

    const handleChangeIsBallot = () => {
        optionsRef.current.isBallot = !optionsRef.current.isBallot
        setCheckedIsBallot(prev => !prev)
    }

    const handleChangeResponseType = () => {
        if (optionsRef.current.responseType === RESPONSE_TYPE_SIGNLE) {
            optionsRef.current.responseType = RESPONSE_TYPE_MULTI
            setCheckedResponseType(prev => !prev)
            return
        }

        if (optionsRef.current.responseType === RESPONSE_TYPE_MULTI) {
            optionsRef.current.responseType = RESPONSE_TYPE_SIGNLE
            setCheckedResponseType(prev => !prev)
            return
        }
    }

    return (
        <div className={classes.container}>
            <PollOption onChange={handleChangeIsBallot} checked={checkedIsBallot} label={"익명 투표"} />
            <PollOption onChange={handleChangeResponseType} checked={checkedResponseType} label={"복수 선택"} />
        </div>
    )
}

function PollOption({ onChange, checked, label }) {
    const classes = pollOptionStyles()

    return (
        <div className={classes.wrapper}>
            <Checkbox checked={checked} color="primary" onChange={onChange} />
            <Typography component={"span"}>{label}</Typography>
        </div>
    )
}

function PollImage({ imageRef, originImage }) {
    const classes = pollOptionStyles()
    const [imgName, setImgName] = useState("")

    const imgHandle = e => {
        imageRef.current = e.target.files[0]
        setImgName(e.target.files[0].name)
    }

    return (
        <>
            <div className={classes.wrapper}>
                <Typography component={"span"}>{imgName}</Typography>
            </div>
            <Button variant="contained" component="label">
                이미지 업로드
                <input type="file" hidden accept="image/*" onChange={imgHandle} />
            </Button>
        </>
    )
}

function PollExpireDate({ expireRef, endedAt }) {
    const classes = pollOptionStyles()
    const now = new Date()
    const minDate = new Date(now)
    minDate.setDate(minDate.getDate() + 1)

    const maxDate = new Date(now)
    maxDate.setDate(maxDate.getDate() + 30)
    const [expireDate, setExpireDate] = useState(new Date(endedAt))

    const handleChange = date => {
        setExpireDate(date)
        expireRef.current = date
    }

    return (
        <>
            <DatePicker
                //locale={ko}
                selected={expireDate}
                onChange={handleChange}
                closeOnScroll={true}
                minDate={minDate}
                maxDate={maxDate}
                placeholderText="마감일을 선택하세요."
                dateFormat="yyyy-MM-dd"
            />
        </>
    )
}
