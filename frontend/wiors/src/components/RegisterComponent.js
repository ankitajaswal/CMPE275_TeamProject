import React, { useState } from 'react';
import '../styles/register.css';

function Register() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('employee');

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(`First Name: ${firstName}\nLast Name: ${lastName}\nEmail: ${email}\nPassword: ${password}\nRole: ${role}`);
    // handle form submission here
  };

  const handleRoleChange = (event) => {
    setRole(event.target.value);
  };

  return (
    <div className='register-form'>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor='firstName'>First Name:</label>
        <input
          type='text'
          id='firstName'
          value={firstName}
          onChange={(event) => setFirstName(event.target.value)}
        />
        <label htmlFor='lastName'>Last Name:</label>
        <input
          type='text'
          id='lastName'
          value={lastName}
          onChange={(event) => setLastName(event.target.value)}
        />
        <label htmlFor='email'>Email:</label>
        <input
          type='email'
          id='email'
          value={email}
          onChange={(event) => setEmail(event.target.value)}
        />
        <label htmlFor='password'>Password:</label>
        <input
          type='password'
          id='password'
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />
        <label htmlFor='role'>I am a:</label>
        <div>
          <label>
            <input
              type='radio'
              value='employee'
              checked={role === 'employee'}
              onChange={handleRoleChange}
            />
            Employee
          </label>
        </div>
        <div>
          <label>
            <input
              type='radio'
              value='employer'
              checked={role === 'employer'}
              onChange={handleRoleChange}
            />
            Employer
          </label>
        </div>
        <button type='submit'>Register</button>
      </form>
    </div>
  );
}

export default Register;
