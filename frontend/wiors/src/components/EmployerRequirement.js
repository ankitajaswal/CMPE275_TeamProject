import React, { Component } from 'react';

class EmployerRequirement extends Component {

    constructor(props) {
        super(props);
        this.state = {
            baseMop: 0,
            mop: 0
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleMopChange = this.handleMopChange.bind(this);
    }

    handleMopChange(event) {
        this.setState({ mop: event.target.value });
    }

    handleSubmit(event) {
        event.preventDefault();
        let url = global.config.url + "requirement/employer/" 
            + global.config.employerId + "?numberOfDays=" + this.state.mop;

        let iterator = fetch(url, {
            method: "POST",
        });
        iterator.then(res => {
            this.componentDidMountLogic();
        });
    }

    componentDidMountLogic() {
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

    componentDidMount() {
        this.componentDidMountLogic();
    }

    render() {
        const mop = this.state.mop;
        return (
            <div>
                <div>Current MOP for {global.config.employerId} employees: {this.state.baseMop}</div>
                <form onSubmit={this.handleSubmit}>
                    <label htmlFor='mop'>Enter new MOP:</label>
                    <input type='number' id='mop' value={mop} onChange={this.handleMopChange} />
                    <button type='submit'>Submit</button>
                </form>
            </div>
        )
    }

}

export default EmployerRequirement;
