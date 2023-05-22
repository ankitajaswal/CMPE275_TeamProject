import React, { useState } from 'react';
import '../styles/login.css';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('employee');

  const handleSubmit = (event) => {
    event.preventDefault();
    let url = global.config.url + "login?";
    url += "email=" + email
        + "&password=" + password;
    if (role === "employer") {
        url += "&isEmployer=true";
    }
    let iterator = fetch(url, {
        method: "POST",
        headers: {
            "Accept": "application/json"
        }
    });
    iterator
        .then(res => res.json())
        .then(dat => {
            console.log(dat);
        })
        .catch(error => {
            console.log(error);
        });
    console.log(`Email: ${email}\nPassword: ${password}\nRole: ${role}`);
    // handle form submission here
  };

  return (
    <div className='login-form'>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor='email'>Email:</label>
        <input
          type='text'
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
