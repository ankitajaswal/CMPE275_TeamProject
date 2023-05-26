import React from 'react';
import SetSeatCapacity from './SetSeatCapacity';
import SeatReservation from './SeatReservationComponent';
import EmployerRequirement from "./EmployerRequirement";
import EmployeeRequirement from "./EmployeeRequirement";
import CSVImporterComponent from './CSVImporterComponent';
import Reservations from './Reservations.js';

function UserDash() {
    console.log(global.config);
    if (global.config.isEmployer) {
        return (
            <div>
                <EmployerRequirement />
                <SetSeatCapacity/>
                <CSVImporterComponent />
            </div>
        )
    } else {
        return (
            <div>
                <EmployeeRequirement />
                <SeatReservation />
                <Reservations />
            </div>
        )
    }

}

export default UserDash;
