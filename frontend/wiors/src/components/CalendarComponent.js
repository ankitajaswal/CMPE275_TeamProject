import { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

function CalendarComponent() {
  const [date, setDate] = useState(new Date());

  return (
    <>
    <div className='calendar-container' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      <Calendar onChange={setDate} value={date} />
    </div>
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
    <p className='text-center' >
        <span className='bold' >Selected Date:</span>
        {' '}
        {date.toDateString()}
      </p>
      </div>
      </>
  );
}

export default CalendarComponent;
