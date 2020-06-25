import React from "react";
import "../App.css"
import {useHistory, useParams} from "react-router-dom";

import {AppBar, Button, Card, CardContent, Toolbar, Typography} from "@material-ui/core";

export default function PlayStart() {
    const history = useHistory();
    const {size} = useParams();
    return <div>
        <AppBar position="static" style={{background: '#2E3B55'}}>
            <Toolbar>
                <Typography variant="h6" style={{flex: 1}}>
                    Play game and score best
                </Typography>
            </Toolbar>
        </AppBar>

        <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh'}}>
            <Card className="root" variant="outlined">
                <CardContent>
                    <Typography className="title" color="textSecondary" gutterBottom>
                        Hello How are yout who is this
                    </Typography>
                    <Typography variant="subtitle1">
                        A new{size}* {size} game has been initialized and is ready for you to play. You'll have three
                        minutes to find as many words as you can. Each word must have three or more letters. When you're
                        ready to start, just click Start play!
                    </Typography>

                    <Button variant="outlined" className="playbtn" color="primary" onClick={() => {
                        history.push('/gameboard/' + size)
                    }}>
                        Start {size}* {size} Game
                    </Button>

                </CardContent>

            </Card>
        </div>
    </div>

}