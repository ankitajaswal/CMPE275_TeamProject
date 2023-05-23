import React, { useRef, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/login.css';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('employee');
  const [statusCode, setStatusCode] = useState(null);
  const [errorMsg, setErrorMsg] = useState(null);

  const nav = useNavigate();

  useEffect(() => {
      if (statusCode !== null) {
          if (errorMsg !== null) {
              if (statusCode === 403) {
                  window.confirm(errorMsg);
              } else if (statusCode === 404) {
                  window.confirm("Not found");
              }
          }
          if (statusCode === 200) {
              nav("/seatreservation");
          }
          setErrorMsg(null);
          setStatusCode(null);
      }
  }, [statusCode, errorMsg, nav]);

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
        .then(res => { 
            setStatusCode(res.status);
            if (res.status === 200) {
                return res.json();
            } else {
                return;
            }
        })
        .then(dat => {
            if (statusCode !== 200) {
                setErrorMsg("Invalid user");
            } else {
                if (!dat.isEmployer) {
                    global.config.employeeId = dat.employeeId;
                }
                global.config.employerId = dat.employerId;
                global.config.isEmployer = dat.isEmployer;
                console.log(global.config.employerId);
            }
        })
        .catch(error => {
            console.log(error);
        });
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
