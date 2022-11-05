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
import React, { useState } from "react"
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
import { useCookies } from "react-cookie"
import jwt_decode from "jwt-decode"
//import { ko } from "date-fns/esm/locale";

const RESPONSE_TYPE_SIGNLE = "SINGLE"
const RESPONSE_TYPE_MULTI = "MULTI"

const steps = [
    "작성자 이름을 입력해주세요.",
    "투표 주제를 입력해주세요.",
    "투표 이미지를 삽입해주세요. (선택사항 입니다.)",
    "투표 항목을 생성해주세요.",
    "투표 옵션을 선택해주세요.",
    "투표 마감일을 선택해주세요.",
]

export default function VerticalLinearStepper() {
    const classes = useStyles()
    const router = useRouter()
    const [activeStep, setActiveStep] = useState(0)
    const [isSubmit, setIsSubmit] = useState(false)
    const [cookies, setCookies] = useCookies()
    const nameRef = useRef({ value: "", isInvalid: false })
    const titleRef = useRef({ value: "", isInvalid: false })
    const imageRef = useRef({})
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
    ])
    const optionsRef = useRef({
        isBallot: false,
        responseType: RESPONSE_TYPE_SIGNLE,
    })
    const expireRef = useRef({})
    const handleNext = () => {
        if (isSubmit) return

        switch (activeStep) {
            case 0:
                if (nameRef.current.value === "" || nameRef.current.isInvalid) return
                break
            case 1:
                if (titleRef.current.value === "" || titleRef.current.isInvalid) return
                break

            case 2:
                //if (imageRef.current.value !== "") return;
                break

            case 3:
                if (itemsRef.current.some(el => el.isInvalid === true)) return
                break
            case 4:
                break
            case 5:
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

        const payload = {
            title: titleRef.current.value,
            creatorName: nameRef.current.value,
            ended_at: expireRef.current,
            isBallot: optionsRef.current.isBallot,
            responseType: optionsRef.current.responseType,
        }

        if (cookies?.accessToken) {
            const decoded = jwt_decode(cookies.accessToken)
            payload.userId = decoded.id
        }

        const formData = new FormData()
        Object.keys(payload).forEach(key => formData.append(key, payload[key]))
        if (imageRef.current.name !== undefined) formData.append("pollImage", imageRef.current)

        for (let i = 0; i < itemsRef.current.length; i++) {
            formData.append(`options[${i}].description`, itemsRef.current[i].value.description)
        }

        const response = await ApiGateway.createPoll(formData)

        if (response.error) {
            alert(response.message)
            setIsSubmit(false)
            return
        }

        router.push("/polls/" + response.pollId)
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
                            {getStepContent(index, [nameRef, titleRef, imageRef, itemsRef, optionsRef, expireRef])}
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

function getStepContent(step, refs) {
    switch (step) {
        case 0:
            return <PollCreatorName nameRef={refs[step]} />
        case 1:
            return <PollTitle titleRef={refs[step]} />
        case 2:
            return <PollImage imageRef={refs[step]} />
        case 3:
            return <PollItemsWrapper itemsRef={refs[step]} />
        case 4:
            return <PollOptionsWrapper optionsRef={refs[step]} />
        case 5:
            return <PollExpireDate expireRef={refs[step]} />
        default:
            return "Unknown step"
    }
}

export const PollItemsContext = React.createContext(null)

function PollItemsWrapper({ itemsRef }) {
    const itemListState = useState(itemsRef.current)

    return (
        <PollItemsContext.Provider value={{ itemsRef, itemListState }}>
            <PollItems itemsRef={itemsRef} itemListState={itemListState} />
            <AddPollButtion itemsRef={itemsRef} itemListState={itemListState} />
        </PollItemsContext.Provider>
    )
}

function PollItems({ itemsRef, itemListState }) {
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

    return <DraggableList items={itemsRef.current} onDragEnd={onDragEnd} />
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

function PollImage({ imageRef }) {
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

function PollExpireDate({ expireRef }) {
    const classes = pollOptionStyles()
    const now = new Date()
    const minDate = new Date(now)
    minDate.setDate(minDate.getDate() + 1)

    const maxDate = new Date(now)
    maxDate.setDate(maxDate.getDate() + 30)

    const [expireDate, setExpireDate] = useState(new Date())

    const handleChange = date => {
        setExpireDate(date)
        expireRef.current = date
        console.log("선택한 날짜>>>", date)
    }

    return (
        <>
            <DatePicker
                //locale={ko}
                selected={minDate}
                onChange={handleChange}
                closeOnScroll={true}
                minDate={minDate}
                maxDate={maxDate}
                placeholderText="마감일을 선택하세요."
                dateFormat="yyyy-MM-dd"
            />
        </>
        //시간단위 포함할것.
        //최소 30분
    )
}
