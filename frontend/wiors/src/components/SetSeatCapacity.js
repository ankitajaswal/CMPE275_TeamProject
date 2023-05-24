import { useState } from 'react';
import React from 'react';
import SeatingCapacityInput from './SeatingCapacityInput';

function SetSeatCapacity() {

    const [seatingCapacity, setSeatingCapacity] = useState(3);

    const handleSeatingCapacityChange = (value) => {
        const parsedValue = parseInt(value, 10);
        if (!isNaN(parsedValue) && parsedValue >= 3 && parsedValue <= 100) {
          setSeatingCapacity(parsedValue);
        }
      };

    const handleSubmit = (event) => {
      console.log(seatingCapacity);
      let i = 0;
      while(i < seatingCapacity) {
      let url = global.config.url
      + "seatService/createSeat/" 
      + global.config.employerId;
      let iterator = fetch(url, {method: "POST", headers: {"Accept": "application/json"}})
      iterator.then(res => {
        if (!res.ok) {
          window.confirm("Failed to create seats");
        }
      })
      i = i + 1;
    }
    };
 
return (
 <>
    {/* Render the SeatingCapacityInput component */}
    <div style={{ display: 'flex', justifyContent: 'center' }}>
              <h2>Set Seat Capacity</h2>
        <SeatingCapacityInput onChange={handleSeatingCapacityChange} defaultValue={seatingCapacity} />
        <button type='submit' onClick={handleSubmit}>Submit</button>
  </div>
  </>
  );
}

export default SetSeatCapacity;
