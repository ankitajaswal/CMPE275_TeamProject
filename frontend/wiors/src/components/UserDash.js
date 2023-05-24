import React from 'react';
import SetSeatCapacity from './SetSeatCapacity';
import SeatReservation from './SeatReservationComponent';
import EmployerRequirement from "./EmployerRequirement";
import EmployeeRequirement from "./EmployeeRequirement";

function UserDash() {
    console.log(global.config);
    if (global.config.isEmployer) {
        return (
            <div>
                <EmployerRequirement />
                <SetSeatCapacity/>
            </div>
        )
    } else {
        return (
            <div>
                <EmployeeRequirement />
                <SeatReservation/>
            </div>
        )
    }

}

export default UserDash;