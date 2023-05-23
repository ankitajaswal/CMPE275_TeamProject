import React, { Component } from 'react';
import EmployerRequirement from './EmployerRequirement';

class CreateEmployerRequirement extends Component {

    constructor(props) {
        super(props);
        this.state = { mop: 0 };
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
        iterator.then(res => {});
        // EmployerRequirement.componentDidMount();
    }

    render() {
        const mop = this.state.mop;

        return (
            <div>
            <form onSubmit={this.handleSubmit}>
                <label htmlFor='mop'>Enter new MOP:</label>
                <input type='number' id='mop' value={mop} onChange={this.handleMopChange} />
                <button type='submit'>Submit</button>
            </form>
            </div>
        )
    }

}

export default CreateEmployerRequirement;
