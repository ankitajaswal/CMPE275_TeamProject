import React, { Component } from "react";
import { Importer, ImporterField } from "react-csv-importer";

// theme CSS for React CSV Importer
import "react-csv-importer/dist/index.css";

class CSVImporterComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            opType: '',
            userRows: [],
            seatRows:[]
        };
        this.handleOpTypeChange = this.handleOpTypeChange.bind(this);
    }

    handleOpTypeChange(event) {
        this.setState({ opType: event.target.value });
    }

    // Open Source CSV Sample Grabbed From:
    // https://codesandbox.io/s/github/beamworks/react-csv-importer/tree/master/demo-sandbox?file=/index.jsx:417-425
    render() {
        let opType = this.state.opType;
        return(
            <div>
                <h1>Bulk CSV-based Operations</h1>

                <Importer
                dataHandler={async (rows) => {
                    // required, receives a list of parsed objects based on defined fieldsand
                    //      and user column mapping;
                    // may be called several times if file is large
                    // (if this callback returns a promise, the widget will wait for it 
                    //      before parsing more data)
                    console.log("received batch of rows", rows);
                    this.state.userRows = rows;
                    this.state.seatRows = rows;

                    // mock timeout to simulate processing
                    await new Promise((resolve) => setTimeout(resolve, 500));
                }}
                chunkSize={10000} // optional, internal parsing chunk size in bytes
                defaultNoHeader={true} // optional, keeps "data has headers" checkbox off by default
                restartable={false} // optional, lets user choose to upload another file when import 
                                    //      is complete
                onStart={({ file, fields }) => {
                    // optional, invoked when user has mapped columns and started import
                    console.log("starting import of file", file, "with fields", fields);
                }}
                onComplete={({ file, fields }) => {
                    // optional, invoked right after import is done (but user did 
                    //      not dismiss/reset the widget yet)
                    console.log("finished import of file", file, "with fields", fields);
                    if (this.state.opType === 'user') {
                        const url = global.config.url + "user/bulk?employerId=" + global.config.employerId;
                        let iterator = fetch(url, {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(this.state.userRows)
                        });
                        iterator.then(res => {
                            if (!res.ok) {
                                window.confirm("Bulk user registration failed");
                            }
                        });
                    } else {
                        // submit 'rows' to seat reservation backend
                        const url = global.config.url + "reservationService/bulkReservation/"
                        + global.config.employerId ;
                        let iterator = fetch (url, {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(this.state.seatRows)
                        });
                        iterator.then(res => {
                            if (!res.ok) {
                                window.confirm("Bulk seat reservation has failed!");
                            }
                        });
                    }
                }}
                onClose={() => {
                    // optional, invoked when import is done and user clicked "Finish"
                    // (if this is not specified, the widget lets the user upload another file)
                    console.log("importer dismissed");
                }}
                >
                <label htmlFor='opType'>Select operation:</label>
                <div>
                  <label>
                    <input
                      type='radio'
                      value='user'
                      id='opType'
                      checked={opType === 'user'}
                      onChange={this.handleOpTypeChange}
                    />
                    Bulk user registration
                  </label>
                </div>
                <div>
                  <label>
                    <input
                      type='radio'
                      value='seat'
                      checked={opType === 'seat'}
                      onChange={this.handleOpTypeChange}
                    />
                    Bulk seat reservation
                  </label>
                </div>
                {this.state.opType === 'user' && <div>
                    <ImporterField name="email" label="Email" />
                    <ImporterField name="name" label="Name" />
                    <ImporterField name="password" label="Password" />
                    <ImporterField name="manager" label="Manager" />
                </div>}
                {this.state.opType === 'seat' && <div>
                    <ImporterField name="email" label="Email" />
                    <ImporterField name="startDate" label="Start Date" />
                    <ImporterField name="endDate" label="End Date" />
                </div>}
                </Importer>
            </div>
        )
    }
}

export default CSVImporterComponent;

