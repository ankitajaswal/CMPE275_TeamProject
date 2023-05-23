import React, { Component } from 'react';

class EmployeeRequirement extends Component {

    constructor(props) {
        super(props);
        this.state = { mop: 0 };
    }

    componentDidMount() {
        const url = global.config.url + "requirement/employee/" + global.config.employeeId;
        let iterator = fetch(url, {
            method: "GET",
            headers: {
                "Accept": "application/json"
            }
        });
        iterator.then(res => res.json())
            .then(dat => {
                this.setState({mop: dat.days});
            });
    }

    render() {
        return (
            <div>Current MOP for employee with ID {global.config.employeeId}: {this.state.mop}</div>
        )
    }

}

export default EmployeeRequirement;
