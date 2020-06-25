import CircularProgress from "@material-ui/core/CircularProgress";
import React from "react";

export default function LoadingProgress() {
    return (
        <div style={{display: 'flex',  justifyContent:'center', alignItems:'center',}}>
            <CircularProgress disableShrink />
        </div>
    );

}