import React, {useEffect, useRef, useState} from "react";
import Square from "./Square";
import "../css/GameBoard.css"
import {v4 as uuidv4} from 'uuid';
import {AppBar, Button, Card, CardContent, Grid, Paper, TextField, Toolbar, Typography,} from "@material-ui/core";
import {Alert} from '@material-ui/lab';
import {useParams} from "react-router-dom";
import ScoreTable from "./scoreTable";
import axios from "axios";
import LoadingProgress from "./LoadingProgress";
import ScoreHistory from "./ScoreHistory";
import Timer from "./Timer";


const alphabetsArr = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
const newArr = (size) => {
    return boardArray(size);
}
var datArray = newArr;
var dataSet = new Set();

function boardArray(boxSize) {
    dataSet.clear()
    var alphabets = alphabetsArr.sort(() => .5 - Math.random());
    let newArr = [];
    for (let i = 0; i < boxSize; i++) {
        let start = Math.floor(Math.random() * alphabets.length);
        if (start > alphabets.length - boxSize) {
            start -= 7
        }
        let end = Number(start) + Number(boxSize)
        var dataChunk = alphabets.slice(start, end)
        newArr.push(dataChunk);
    }
    return newArr
}

export default function GameBoard() {
    const {size} = useParams();

    const API_NAME = {
        VALIDATE: "validate",
        SAVE_SCORE: "saveScore",
        NONE: "none",
    }


    let message = "alphabets on box are valid"
    const [errorMessage, setErrorMessage] = useState(message);
    const [error, setError] = useState(false);
    const [word, setWord] = useState("");
    const [words, setWords] = useState(new Set());
    const [timeout, setTimeOut] = useState(false)
    const [respData, setRespData] = useState(Array());
    const [scoreResp, setScoreResp] = useState(Array());
    const [apiErrorMessage, setapiErrorMessage] = useState(message);
    const [apiName, setApiName] = useState(API_NAME.VALIDATE);
    const [validate, setValidate] = useState(true);

    const retryComponent = () => {
        return (
            // <div style={{display: 'flex',  justifyContent:'center', alignItems:'center',}}>
            <div>
                Some Error Occured Please Try again !! <br/>
                <h5>error message{apiErrorMessage} </h5>
                <div><Button variant="outlined" className="playbtn" color="primary" onClick={() =>storeScore() }>
                    Retry
                </Button></div>
            </div>
        );
    };

    const SCORE_API_STATUS = {
        SUBMITTING: "submitting",
        SUCCESS: "success",
        FAILED: "failed",
        NONE: "none,"
    }

    const handleTimeout=() =>{
        setTimeOut(true);
        if(words.size>1)
        storeScore()
    }

    const [apiStat, setApiStat] = useState(SCORE_API_STATUS.SUBMITTING)

    const [query, setQuery] = useState('react');

    const boggleBoard = () => {
        return <Grid id container spacing={3} xs={12}>
            <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
                <div>
                    <Timer timeoutCallback={handleTimeout}/>
                    <div className="board">
                        {
                            datArray.map((row) => {
                                // return <div key={row} className="board-row">
                                return <div className="board-row">
                                    {
                                        row.map((alphabet) => {
                                            // return <Square key={alphabet} value = {alphabet}/>
                                            return <Square value={alphabet.toUpperCase()}/>
                                        })
                                    }
                                </div>
                            })
                        }
                    </div>


                    {/*<Paper className={classes.center} style={{width:200,padding:'4px 4px'}}>*/}
                    <Paper style={{width: 200, padding: '8px 8px', margin: "auto", marginTop: 22}}>

                        <TextField
                            disabled={timeout}
                            error={error}
                            id="outlined-search"
                            label="Enter word" type="search"
                            variant="outlined"
                            helperText={errorMessage}
                            onChange={handleChange}
                            value={word}
                            //onFocus={handleFocus}
                            onKeyDown={handleKeyDown}
                        />

                    </Paper>
                    <div style={{display: 'flex', marginTop: 20, marginBottom: 20, justifyContent: "space-around"}}>
                        {
                            (timeout && words.size < 1) ?
                                <Button variant="outlined" className="playbtn" color="primary" onClick={() => {
                                    window.location.reload()
                                }}>
                                    Play Again
                                </Button> :
                                <Button variant="outlined" className="playbtn" color="primary" onClick={() => {
                                    storeScore()
                                }}>
                                    Submit Answers
                                </Button>
                        }

                        {/*</div>*/}
                    </div>


                </div>


            </Grid>
            <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>

                <ScoreTable value={words}/>
            </Grid>
        </Grid>
    }
    const getWiget = (stat) => {
        switch (stat) {
            case SCORE_API_STATUS.SUCCESS:
                return (apiName === API_NAME.SAVE_SCORE) ? <ScoreHistory value={scoreResp}/> : boggleBoard()
            case SCORE_API_STATUS.FAILED:
                return retryComponent()
            default:
                return <LoadingProgress/>
        }
    }

    useEffect(() => {
        async function fetchData() {
            setApiName(API_NAME.VALIDATE)
            setApiStat(SCORE_API_STATUS.SUBMITTING)
            axios.post('http://localhost:2222/validate', JSON.stringify(datArray))
                .then((response) => {
                    setApiStat(SCORE_API_STATUS.SUCCESS)
                    respData.push(response.data["words"]);
                }, (error) => {
                    setapiErrorMessage(error['msg'])
                    setApiStat(SCORE_API_STATUS.FAILED)
                    console.log(error);
                });
        }

        if (validate) {
            fetchData();
        }
    }, [validate]);

    var userId = localStorage.getItem("USER_ID")
    if(!userId) {
        userId = uuidv4().toString()
        localStorage.setItem("USER_ID", userId)
    }

    const storeScore = () => {
        if(words.size<1){
            //TODO
            return <Alert severity="warning">you havent entered the words yet!</Alert>
        }
        setApiStat(SCORE_API_STATUS.SUBMITTING)
        setApiName(API_NAME.SAVE_SCORE)
        var reqData = {
            name: "+userId+",
            gameSize: size,
            wordList: [...words],

        };
        axios.post('http://localhost:2222/saveScores', JSON.stringify(reqData))
            .then((response) => {
                setScoreResp(response.data)
                setApiStat(SCORE_API_STATUS.SUCCESS)
            }, (error) => {
                setapiErrorMessage(error['msg'])
                setApiStat(SCORE_API_STATUS.FAILED)
                console.log(error['msg']);
            });
    };


    const prevSizeRef = useRef();
    useEffect(() => {
        prevSizeRef.current = size;
    });
    const prevCount = prevSizeRef.current;
    // var dataArr ;
    if (prevCount !== size) {
        datArray = newArr(size)
    }

    const handleChange = event => {
        setWord(event.target.value)

    };

    const handleKeyDown = event => {
        setError(false)
        setErrorMessage(message)
        var keycode = event.keyCode
        if (keycode === 13) {
            if (word.length < 3) {
                console.log(event.target.value)
                setError(true);
                setErrorMessage("too short word");
                return
            }
            if (respData[0].includes(word.trim())) {
                setWords(words.add(word.trim().toLowerCase()));
            } else {
                setError(true)
                setErrorMessage("invalid word")
            }
            setWord("")
        }
    };

    return (
        <div>
            <AppBar position="static" style={{background: '#2E3B55'}}>
                <Toolbar>
                    <Typography variant="h6" style={{flex: 1}}>
                        Play game and score best
                    </Typography>
                </Toolbar>
            </AppBar>

            <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center',marginTop:30}}>
                <Card className="root" variant="outlined">
                    <CardContent>

                        {getWiget(apiStat)}

                    </CardContent>

                </Card>
            </div>
        </div>


    )


}
