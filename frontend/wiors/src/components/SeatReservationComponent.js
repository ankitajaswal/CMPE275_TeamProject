import React, { useState } from 'react';
import Calendar from 'react-calendar';
//import 'react-calendar/dist/Calendar.css';
import '../styles/seatreservation.css';
function SeatReservation() {
  const [selectedDates, setSelectedDates] = useState([]);

  const handleDateChange = (date) => {
    const dayOfWeek = date.getDay();

    // Allow selection only on Monday to Friday (0 = Sunday, 6 = Saturday)
    if (dayOfWeek >= 1 && dayOfWeek <= 5) {
      const selectedDateIndex = selectedDates.findIndex((selectedDate) =>
        selectedDate.toDateString() === date.toDateString()
      );

      if (selectedDateIndex > -1) {
        const updatedDates = [...selectedDates];
        updatedDates.splice(selectedDateIndex, 1);
        setSelectedDates(updatedDates);
      } else {
        setSelectedDates([...selectedDates, date]);
      }
    }
  };

  const highlightSelectedDates = ({ date }) => {
    const dayOfWeek = date.getDay();
  
    // Check if the date is included in the selectedDates array
    const isSelected = selectedDates.some((selectedDate) => selectedDate.toDateString() === date.toDateString());
  
    // Highlight selected dates (Monday to Friday) with a blue background color
    if (dayOfWeek >= 1 && dayOfWeek <= 5 && isSelected) {
      return (
        <div className="highlighted-date">
          {date.getDate()}
        </div>
      );
    }
  
    return null;
  };
  
  

  return (
    <>
      <div className='calendar-container' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <Calendar onChange={handleDateChange} value={selectedDates} tileContent={highlightSelectedDates} />
      </div>
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <p className='text-center'>
          <span className='bold'>Selected Dates:</span>
          {' '}
          {selectedDates.map((date) => date.toDateString()).join(', ')}
        </p>
      </div>
    </>
  );
}

export default SeatReservation;
