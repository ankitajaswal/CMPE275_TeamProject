import React, { Component } from "react";

class Reservations extends Component {
    constructor(props) {
        super(props);
        this.state = {
            reservations: []
        };
    }
        
    componentDidMount() {
        let url = global.config.url + "reservationService/getAllReservations/" + global.config.employerId;
        let iterator = fetch(url, {
            method: "GET",
            headers: {}
        });
        iterator.then(res => res.json())
        .then(r => {
            this.setState({reservations:r});
        });
    }

    render() {
        return (
            <div className="reservationsTable">
                <table>
                    <tbody>
                        <tr>
                            <th>Date</th>
                            <th>Reservee</th>
                            <th>Seat ID</th>
                        </tr>
                        {this.state.reservations.map((item,index) => (
                            <tr key={index}> 
                                <td>{item.date}</td>
                                <td>{item.reservee.name}</td>
                                <td>{item.seat.seatId}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default Reservations;
