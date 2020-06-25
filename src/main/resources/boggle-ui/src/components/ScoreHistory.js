import React from 'react';
import {Button} from "@material-ui/core";
import Container from "@material-ui/core/Container";
import Typography from "@material-ui/core/Typography";
import List from "@material-ui/core/List";
import ListItem from '@material-ui/core/ListItem';
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import {useHistory} from "react-router-dom";

export default function ScoreHistory(scoreResp) {
    const history = useHistory();
    return (
        <Container maxWidth="sm">
            {/*<Typography component="div" style={{ backgroundColor: '#cfe8fc', height: '100vh' }} />*/}
            <List subheader={<ccc><h2>Your Game Results</h2>
                <Button variant="outlined" className="playbtn" color="primary" onClick={() => {
                    history.push('/')
                }}>
                    Start New Game
                </Button>
            </ccc>}>
                {scoreResp.value.map((item) => <ListItem>
                    <Card className="root" variant="outlined">
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                Your Score is : {item["score"]}
                            </Typography>
                            <Typography color="textSecondary">
                                Game Type : {item["gameType"]}
                            </Typography>
                            <Typography variant="body2" component="p">
                                Correct Words are :
                                {item["words"]}
                            </Typography>
                        </CardContent>
                        {/*<CardActions>*/}
                        {/*    <Button size="small">Learn More</Button>*/}
                        {/*</CardActions>*/}
                    </Card>


                </ListItem>)}
            </List>
        </Container>
    );
}