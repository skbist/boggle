import React from "react";
import "../css/GameBoard.css"

export default function Square(data) {
    return (<div className="board-cell">
        <span className="board-cell-content"> {data.value} </span>
    </div>);
}