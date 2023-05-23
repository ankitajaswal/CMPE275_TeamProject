import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavBarComponent from './components/NavBarComponent';
import HomeComponent from './components/HomeComponent';
import Register from './components/RegisterComponent';
import Login from './components/LoginComponent';
import CSVImporterComponent from './components/CSVImporterComponent';
import SeatReservation from './components/SeatReservationComponent';
import AttendanceRequirements from './components/AttendanceRequirements';

function App() {
  return (
    <Router>
      <div className='app'>
      <NavBarComponent />

      <Routes>        
          <Route exact path='/' element={<HomeComponent />} />
          <Route path='/register' element={<Register />} />
          <Route path='/login' element={<Login />} />
          <Route path='/seatreservation' element={<SeatReservation />} />
          <Route path='/bulk' element={<CSVImporterComponent />} />
          <Route path='/attendancerequirements' element={<AttendanceRequirements />} />

      </Routes>

      </div>

    </Router>
  );
}

export default App;

