import React, { Component } from 'react';

class EmployerRequirement extends Component {

    constructor(props) {
        super(props);
        this.state = { baseMop: 0 };
    }

    componentDidMount() {
        const url = global.config.url + "requirement/employer/" + global.config.employerId;
        let iterator = fetch(url, {
            method: "GET",
            headers: {
                "Accept": "application/json"
            }
        });
        iterator.then(res => res.json())
            .then(dat => {
                this.setState({baseMop: dat.days});
            });
    }

    render() {
        return (
            <div>Current MOP for {global.config.employerId} employees: {this.state.baseMop}</div>
        )
    }

}

export default EmployerRequirement;
