import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import '../styles/seatreservation.css';
import SeatingCapacityInput from './SeatingCapacityInput';
import { employeeId } from '../config';

function SeatReservation() {
  const [selectedStartDate, setSelectedStartDate] = useState(null);
  const [selectedEndDate, setSelectedEndDate] = useState(null);
  const [selectedDates, setSelectedDates] = useState([]);
  const [statusCode, setStatusCode] = useState(null);
  const [errorMsg, setErrorMsg] = useState(null);

  useEffect(() => {
    if (statusCode !== null) {
        if (errorMsg !== null) {
            if (statusCode !== 200) {
                window.confirm(errorMsg);
            }
        }
        setErrorMsg(null);
        setStatusCode(null);
    }
}, [statusCode, errorMsg]);

  const handleDateChange = (date) => {
    if (!selectedStartDate) {
      if (date.getDay() >= 1 && date.getDay() <= 5) {
        setSelectedStartDate(date);
        setSelectedEndDate(null);
        setSelectedDates([]);
      }
    } else if (!selectedEndDate && date > selectedStartDate) {
      const startDate = new Date(selectedStartDate);
      const endDate = new Date(date);
      /*When selecting the end date, 
      it checks if the selected date is a weekday and greater than the start date. 
      It calculates the difference in days between the start and end dates 
      using the (endDate - startDate) / (1000 * 60 * 60 * 24) formula. 
      If the selected date satisfies these conditions 
      and the difference is 4 days or less, it is set as the end date, 
      and the dates within the range are updated.
      */
      const daysDifference = Math.round((endDate - startDate) / (1000 * 60 * 60 * 24));
      //When selecting the start date, 
      //it verifies if the selected date is a weekday (Monday to Friday) using the getDay() method. 
      //If it is, the date is set as the start date.
      if (date.getDay() >= 1 && date.getDay() <= 5 && daysDifference <= 4) {
        setSelectedEndDate(date);
        const datesInRange = getDatesInRange(selectedStartDate, date);
        setSelectedDates(datesInRange);
      }
    } else {
      setSelectedStartDate(date);
      setSelectedEndDate(null);
      setSelectedDates([]);
    }
  };

  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const handleSubmit = (event) => {
    let selectedDatesF = selectedDates.map((date) => formatDate(date));
    selectedDatesF.forEach(element => {
      let url = global.config.url
      + "reservationService/createReservation/" 
      + global.config.employerId + "/"
      + global.config.employeeId + "/"
      + element;
      let iterator = fetch(url, {method: "POST", headers: {"Accept": "application/json"}})
      iterator.then(res => {
        if (!res.ok) {
          setStatusCode(res.status);
          setErrorMsg("Failed to register");
        }
        iterator.then(dat => {});
      });
    })
  }
  
  const getDatesInRange = (startDate, endDate) => {
    const dates = [];
    const currentDate = new Date(startDate);
    while (currentDate <= endDate) {
      const dayOfWeek = currentDate.getDay();
      if (dayOfWeek >= 1 && dayOfWeek <= 5) {
        dates.push(new Date(currentDate));
      }
      currentDate.setDate(currentDate.getDate() + 1);
    }
    return dates;
  };

  const highlightSelectedDates = ({ date }) => {
    const dayOfWeek = date.getDay();
    const isStartSelected = selectedStartDate && date.toDateString() === selectedStartDate.toDateString();
    const isEndSelected = selectedEndDate && date.toDateString() === selectedEndDate.toDateString();

    // Highlight selected start date and end date
    if (isStartSelected || isEndSelected) {
      return (
        <div className="highlighted-date">
          {date.getDate()}
        </div>
      );
    }

    // Highlight selected dates within the range
    if (selectedStartDate && selectedEndDate && dayOfWeek >= 1 && dayOfWeek <= 5) {
      if (date >= selectedStartDate && date <= selectedEndDate) {
        return (
          <div className="highlighted-date">
            {date.getDate()}
          </div>
        );
      }
    }

    return null;
  };
  return (
  

    <>
   {/* Render the Calendar component */}
   <div className='calendar-container' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
     <Calendar onChange={handleDateChange} value={selectedStartDate || selectedEndDate} tileContent={highlightSelectedDates} />
   </div>
   <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: '1rem' }}>
     {selectedStartDate && selectedEndDate && (
       <p className='text-center'>
         <span className='bold'>Start Date:</span> {formatDate(selectedStartDate)}
         <br />
         <span className='bold'>End Date:</span> {formatDate(selectedEndDate)}
       </p>
     )}
   </div>
   <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: '1rem' }}>
     <p className='text-center'>
       <span className='bold'>Selected Dates:</span>
       {' '}
       {selectedDates.map((date) => formatDate(date)).join(', ')}
     </p>
   </div>
   <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: '1rem' }}>
    <button type='submit' onClick={handleSubmit}>Reserve</button>
   </div>
  </>
  );
}

export default SeatReservation;
