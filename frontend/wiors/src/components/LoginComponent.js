import React, { useState } from 'react';
import '../styles/login.css';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('employee');

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(`Username: ${username}\nPassword: ${password}\nRole: ${role}`);
    // handle form submission here
  };

  return (
    <div className='login-form'>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor='username'>Username:</label>
        <input
          type='text'
          id='username'
          value={username}
          onChange={(event) => setUsername(event.target.value)}
        />
        <label htmlFor='password'>Password:</label>
        <input
          type='password'
          id='password'
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />
        <label htmlFor='role'>Role:</label>
        <select id='role' value={role} onChange={(event) => setRole(event.target.value)}>
          <option value='employee'>Employee</option>
          <option value='employer'>Employer</option>
        </select>
        <button type='submit'>Login</button>
      </form>
    </div>
  );
}

export default Login;
