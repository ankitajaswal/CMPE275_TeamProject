import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/navbar.css';

function NavBar() {
  return (
    <nav className='navbar'>
      <ul className='navbar-nav'>
        <li className='nav-item'>
          <Link to='/' className='nav-link'>
            Home
          </Link>
        </li>
        <li className='nav-item'>
          <Link to='/register' className='nav-link'>
            Register
          </Link>
        </li>
        <li className='nav-item'>
          <Link to='/login' className='nav-link'>
            Login
          </Link>
        </li>
        <li className='nav-item'>
          <Link to='/bulk' className='nav-link'>
            Bulk
          </Link>
        </li>
        <li className='nav-item'>
          <Link to='/seatreservation' className='nav-link'>
            Seat Reservation
          </Link>
        </li>
        <li className='nav-item'>
          <Link to='/attendancerequirements' className='nav-link'>
            Attendance Requirements
          </Link>
        </li>
      </ul>
    </nav>
  );
}

export default NavBar;
