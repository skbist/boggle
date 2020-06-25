import React from "react";
import App from "../App";
import PlayStart from "../components/PlayStart";
import Home from "../components/Home";
import {BrowserRouter as Router, Route} from "react-router-dom";
import GameBoard from "../components/GameBoard";
import ScoreHistory from "../components/ScoreHistory";
function routes() {
    return (
        <Router>
            <Route path="/"  exact ><Home/></Route>
            <Route path="/startPlay/:size" exact><PlayStart/></Route>
            <Route path="/gameboard/:size" exact><GameBoard/></Route>
            <Route path="/scoreHistory" exact><ScoreHistory/></Route>
        </Router>
    );

}
export default routes;


