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
 
return (
 <>
    {/* Render the SeatingCapacityInput component */}
    <div style={{ display: 'flex', justifyContent: 'center' }}>
              <h2>Set Seat Capacity</h2>
        <SeatingCapacityInput onChange={handleSeatingCapacityChange} defaultValue={seatingCapacity} />
  </div>
  </>
  );
}

export default SetSeatCapacity;
