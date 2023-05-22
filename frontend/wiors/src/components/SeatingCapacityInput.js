import React, { useState } from 'react';

function SeatingCapacityInput({ onChange }) {
  const [seatingCapacity, setSeatingCapacity] = useState(3);

  const handleInputChange = (event) => {
    const { value } = event.target;
    setSeatingCapacity(value);
    onChange(value);
  };

  return (
    <div>
      <label htmlFor="seatingCapacity">Seating Capacity:</label>
      <input
        type="number"
        id="seatingCapacity"
        name="seatingCapacity"
        min={3}
        max={100}
        value={seatingCapacity}
        onChange={handleInputChange}
      />
    </div>
  );
}

export default SeatingCapacityInput;
