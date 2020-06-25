import {AppBar, Button, Card, CardContent, Toolbar, Typography} from "@material-ui/core";
import React from "react";
import {useHistory} from "react-router-dom";

export default function Home() {
    const history = useHistory();


    return <div>
        <AppBar position="static" style={{background: '#2E3B55'}}>
            <Toolbar>
                <Typography variant="h6" style={{flex: 1}}>
                    Play game and score best
                </Typography>
                {/*<CountdownTimer />*/}
            </Toolbar>
        </AppBar>

        <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh'}}>
            <Card className="root" variant="outlined">
                <CardContent>
                    <Typography className="title" color="textSecondary" gutterBottom>
                        Welcome To The Game
                    </Typography>
                    <Typography variant="subtitle1">
                        Players can choose to play a traditional 4x4 board or a 5x5 board. Take your pick!
                    </Typography>
                    <div style={{display: 'flex', marginTop: 20, marginBottom: 20, justifyContent: "space-around"}}>
                        <div>
                            <Button variant="outlined" className="playbtn" onClick={() => {
                                history.push('/startPlay/5')
                            }} color="primary">
                                Play on 5*5 Board
                            </Button>
                            <Typography className="description" color="textSecondary" gutterBottom>
                                The original, traditional Wordtwist board. A four-by-four square of letters, with 3
                                minutes to find as many words as possible. Each word must have at least three letters.
                            </Typography>
                        </div>
                        <div>

                            <Button variant="outlined" className="playbtn" color="primary" onClick={() => {
                                history.push('/startPlay/4')
                            }}>
                                Play on 4*4 Board
                            </Button>

                            <Typography className="description" color="textSecondary" gutterBottom>
                                A new twist on an old favorite. A five-by-five square of letters, with three minutes to
                                find as many words as possible. Each word must have at least three letters.
                            </Typography>
                        </div>
                    </div>
                </CardContent>

            </Card>
        </div>
    </div>;
}