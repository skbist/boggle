import React, { useState, useEffect } from 'react';
import { Grid } from '@material-ui/core';
import { AppBar,Toolbar,Typography,Paper } from '@material-ui/core';
import {IconButton,Button} from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';
import Routes from './routes/Router';

function App() {

    const [userName, setUsername] = useState('JD');
    const [firstName, setFirstname] = useState('John');
    const [lastName, setLastname] = useState('Doe');

    useEffect(() => {
        // setInterval(() => {
        //     setUsername('MJ');
        //     setFirstname('Mary');
        //     setLastname('Jane');
        // }, 5000);
    });

    const logName = () => {
        // do whatever with the names ...
        // console.log(this.state.userName);
        // console.log(this.state.firstName);
        // console.log(this.state.lastName);
    };

    const handleUserNameInput = e => {
        // setUsername({ userName: e.target.value });
    };
    const handleFirstNameInput = e => {
        // setFirstname({ firstName: e.target.value });
    };
    const handleLastNameInput = e => {
        // setLastname({ lastName: e.target.value });
    };
    const useStyles = makeStyles((theme) => ({
        root: {
            flexGrow: 1,
        },
        paper: {
            padding:10,
            marginTop:10,
            marginBottom:10,
            height: "100%",
        },
        container: {
            padding:10,
            marginTop:10,
            marginBottom:10,
            height: "100%",
        },
        control: {
            padding: theme.spacing(2),
        },
    }));

    const classes = useStyles();
    return (<div>
           <Routes/>
        </div>
    );
};

export default App;