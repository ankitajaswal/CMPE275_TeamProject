import React, { Component } from 'react';

class EmployeeRequirement extends Component {

    constructor(props) {
        super(props);
        this.state = {
            baseMop: 0,
            mop: 0,
            gtds: [],
            newGtdDay: null
        };
        this.submitMopChange = this.submitMopChange.bind(this);
        this.addNewGtd = this.addNewGtd.bind(this);
        this.handleMopChange = this.handleMopChange.bind(this);
    }

    componentDidMountLogic() {
        const url = global.config.url + "requirement/employee/" + global.config.employeeId;
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
        let iterator1 = fetch(url + "/gtd", {
            method: "GET",
            headers: {
                "Accept": "application/json"
            }
        });
        console.log(this.state.gtds);
        console.log(this.state.gtds.length);
        console.log(url + "/gtd");
        iterator1.then(res => res.json())
            .then(dat => {
                this.setState({gtds: dat});
            });
    }

    componentDidMount() {
        this.componentDidMountLogic();
    }

    handleMopChange(event) {
        this.setState({ mop: event.target.value });
    }

    submitMopChange(event) {
        event.preventDefault();
        let url = global.config.url + "requirement?" 
            + "numberOfDays=" + this.state.mop
            + "&creator=" + global.config.employeeId
            + "&employer=" + global.config.employerId;

        let iterator = fetch(url, {
            method: "POST",
        });
        iterator.then(res => {
            this.componentDidMountLogic();
        });
    }

    addNewGtd(event) {
        event.preventDefault();
        this.state.newGtdDay = event.target.querySelector('input[name="newGtdDay"]:checked').value;
        let url = global.config.url + "requirement?numberOfDays=1"
            + "&creator=" + global.config.employeeId
            + "&employer=" + global.config.employerId
            + "&isGetTogetherDay=true";
        if (this.state.newGtdDay === "Monday") {
            url += "&dayOfWeek=1";
        } else if (this.state.newGtdDay === "Tuesday") {
            url += "&dayOfWeek=2";
        } else if (this.state.newGtdDay === "Wednesday") {
            url += "&dayOfWeek=3";
        } else if (this.state.newGtdDay === "Thursday") {
            url += "&dayOfWeek=4";
        } else if (this.state.newGtdDay === "Friday") {
            url += "&dayOfWeek=5";
        }

        let iterator = fetch(url, {
            method: "POST",
        });
        iterator.then(res => {
            this.componentDidMountLogic();
        }).then(() => {
            this.componentDidMountLogic();
        });
    }

    render() {
        const mop = this.state.mop;
        return (
            <div>
                <div>
                Current MOP for employee with ID {global.config.employeeId} (and reports): {this.state.baseMop}
                </div>
                {this.state.gtds.length > 0 && <div>
                Current get together days: {this.state.gtds}
                </div>}
                <br />
                <form onSubmit={this.submitMopChange}>
                    <label htmlFor='mop'>Enter new MOP:</label>
                    <input type='number' id='mop' value={mop} onChange={this.handleMopChange} />
                    <button type='submit'>Submit</button>
                </form>
                <br />
                <p>Create a new get together day (each manager can only have one)</p>
                <form onSubmit={this.addNewGtd}>
                  <label>
                    <input type="radio" name="newGtdDay" value="Monday" /> Monday
                  </label>
                  <br />
                  <label>
                    <input type="radio" name="newGtdDay" value="Tuesday" /> Tuesday
                  </label>
                  <br />
                  <label>
                    <input type="radio" name="newGtdDay" value="Wednesday" /> Wednesday
                  </label>
                  <br />
                  <label>
                    <input type="radio" name="newGtdDay" value="Thursday" /> Thursday
                  </label>
                  <br />
                  <label>
                    <input type="radio" name="newGtdDay" value="Friday" /> Friday
                  </label>
                  <br />
                  <button type="submit">Submit</button>
                </form>
            </div>
        )
    }

}

export default EmployeeRequirement;
