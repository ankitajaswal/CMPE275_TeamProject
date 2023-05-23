import React, { Component } from 'react';
import CSVImporterComponent from './CSVImporterComponent.js'


class Bulk extends Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <div>
            <CSVImporterComponent />
            </div>
        )
    }

}

export default Bulk;
