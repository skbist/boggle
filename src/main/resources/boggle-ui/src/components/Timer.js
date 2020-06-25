import React, {useEffect, useState} from "react";

export default function Timer(props) {
    const [counter, setCounter] = useState(20);
    useEffect(() => {
        if (counter === 0) {
            props.timeoutCallback(counter)
        }
        const timer =
            counter > 0 && setInterval(() => setCounter(counter - 1), 1000);
        return () => clearInterval(timer);
    }, [counter]);

//     if(counter===1){
// console.log('inside counter');
//
//     }
    return (
        <div> {(counter > 0) ? <p className={(counter <= 10) ? 'timerWarning' : 'timerText'}>Time Remaining
                : {String(counter / 60 | 0).padStart(2, "0")}:{String(counter % 60 | 0).padStart(2, "0")}</p> :

            <h2>Time Up</h2>}
        </div>
    )
}