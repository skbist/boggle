import React, {useEffect, useState} from "react";
import {makeStyles} from "@material-ui/styles";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";

const useStyles = makeStyles({
    table: {
        minWidth: 200,
        maxHeight: 400,
    },
});

export default function ScoreTable(words) {
    const classes = useStyles();
// console.log(words.value)
    const [rows, setRows] = useState([]);
    useEffect(() => {
        setRows([...words.value]);
    }, [words.value.size]);
    // const prevSizeRef = useRef();
    // useEffect(() => {
    //     prevSizeRef.current = words.value.size;
    // });
    // const prevCount = prevSizeRef.current;
    // // var dataArr ;
    // if(prevCount !== words.value.size){
    //     console.log(words.value)
    //     setRows([...words.value])
    // }
    // if(words.value.size)


    return (
        <TableContainer component={Paper}>
            <Table className={classes.table} size="small" aria-label="a dense table">
                <TableHead>
                    <TableRow>
                        <TableCell>Word</TableCell>
                        <TableCell align="right">Score</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((word,) => (
                        <TableRow key={word}>
                            <TableCell component="th" scope="row">
                                {word}
                            </TableCell>
                            <TableCell align="right">{word.length}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}