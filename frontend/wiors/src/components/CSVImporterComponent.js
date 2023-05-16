import React from "react";
import ReactDOM from "react-dom";
import { Importer, ImporterField } from "react-csv-importer";

// theme CSS for React CSV Importer
import "react-csv-importer/dist/index.css";


function CSVImporterComponent(){

    // Open Source CSV Sample Grabbed From:
    // https://codesandbox.io/s/github/beamworks/react-csv-importer/tree/master/demo-sandbox?file=/index.jsx:417-425
    ReactDOM.render(
    <div>
        <h1>React CSV Importer</h1>

        <Importer
        dataHandler={async (rows) => {
            // required, receives a list of parsed objects based on defined fields and user column mapping;
            // may be called several times if file is large
            // (if this callback returns a promise, the widget will wait for it before parsing more data)
            console.log("received batch of rows", rows);

            // mock timeout to simulate processing
            await new Promise((resolve) => setTimeout(resolve, 500));
        }}
        chunkSize={10000} // optional, internal parsing chunk size in bytes
        defaultNoHeader={false} // optional, keeps "data has headers" checkbox off by default
        restartable={false} // optional, lets user choose to upload another file when import is complete
        onStart={({ file, fields }) => {
            // optional, invoked when user has mapped columns and started import
            console.log("starting import of file", file, "with fields", fields);
        }}
        onComplete={({ file, fields }) => {
            // optional, invoked right after import is done (but user did not dismiss/reset the widget yet)
            console.log("finished import of file", file, "with fields", fields);
        }}
        onClose={() => {
            // optional, invoked when import is done and user clicked "Finish"
            // (if this is not specified, the widget lets the user upload another file)
            console.log("importer dismissed");
        }}
        >
        {/* Add more fields as needed. */}
        <ImporterField name="name" label="Name" />
        <ImporterField name="email" label="Email" />
        </Importer>
    </div>,
    document.getElementById("root")
    );
}

export default CSVImporterComponent;

