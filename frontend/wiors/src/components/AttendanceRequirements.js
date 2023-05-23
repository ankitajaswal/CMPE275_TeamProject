import React from 'react';
import EmployerRequirement from "./EmployerRequirement";
import EmployeeRequirement from "./EmployeeRequirement";

function AttendanceRequirements() {
    console.log(global.config);
    if (global.config.isEmployer) {
        return (
            <div>
                <EmployerRequirement />
            </div>
        )
    } else {
        return (
            <div>
                <EmployeeRequirement />
            </div>
        )
    }

}

export default AttendanceRequirements;
