import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/register.css';

function Register() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [title, setTitle] = useState('');
  const [managerEmail, setManagerEmail] = useState('');
  const [employerId, setEmployerId] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [street, setStreet] = useState('');
  const [city, setCity] = useState('');
  const [state, setState] = useState('');
  const [zip, setZip] = useState('');
  const [role, setRole] = useState('employee');
  const [statusCode, setStatusCode] = useState(null);
  const [errorMsg, setErrorMsg] = useState(null);

  const nav = useNavigate();

  useEffect(() => {
      if (statusCode !== null) {
          if (errorMsg !== null) {
              if (statusCode === 400) {
                  window.confirm(errorMsg);
              } else if (statusCode === 404) {
                  window.confirm("Not found");
              }
          }
          if (statusCode === 200) {
              nav("/");
          }
          setErrorMsg(null);
          setStatusCode(null);
      }
  }, [statusCode, errorMsg, nav]);

  const handleSubmit = (event) => {
    event.preventDefault();
    let url = global.config.url;
    if (role === "employer") {
        url += "employer?" 
            + "name=" + name
            + "&id=" + employerId;
        if (description !== "") {
            url += "&description=" + description;
        }
    } else {
        url += "employer/" + employerId + "/employee?"
            + "name=" + firstName + " " + lastName;
        if (title !== "") {
            url += "&title=" + title;
        }
        if (managerEmail !== "") {
            url += "&managerEmail=" + managerEmail;
        }
    }
    url += "&email=" + email
              + "&password=" + password;
    if (street !== "") {
        url += "&street=" + street;
    }
    if (city !== "") {
        url += "&city=" + city;
    }
    if (state !== "") {
        url += "&state=" + state;
    }
    if (zip !== "") {
        url += "&zip=" + zip;
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
            return res.json();
        })
        .then(dat => {
            if (statusCode !== 200) {
                setErrorMsg(dat.msg);
            } else {
                nav("/");
            }
        })
        .catch(error => {
            console.log(error);
        });
  };

  const handleRoleChange = (event) => {
    setRole(event.target.value);
  };

  return (
    <div className='register-form'>
      <h2>Register</h2>
        <label htmlFor='role'>I am a:</label>
        <div>
          <label>
            <input
              type='radio'
              value='employee'
              id='role'
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
      <form onSubmit={handleSubmit}>
        <label htmlFor='email'>Email:</label>
        <input
          type='email'
          id='email'
          value={email}
          required
          onChange={(event) => setEmail(event.target.value)}
        />
        <label htmlFor='password'>Password:</label>
        <input
          type='password'
          id='password'
          value={password}
          required
          onChange={(event) => setPassword(event.target.value)}
        />
        <label htmlFor='employerId'>Employer ID:</label>
        <input
          type='text'
          id='employerId'
          value={employerId}
          required
          onChange={(event) => setEmployerId(event.target.value)}
        />

      {role === "employer" && <div>
        <label htmlFor='name'>Employer Name:</label><br/>
        <input
          type='text'
          id='name'
          value={name}
          required
          onChange={(event) => setName(event.target.value)}
        /><br/>
        <label htmlFor='description'>Description:</label><br/>
        <input
          type='text'
          id='description'
          value={description}
          onChange={(event) => setDescription(event.target.value)}
        /><br/>
      </div>}

      {role === "employee" && <div>
        <label htmlFor='firstName'>First Name:</label><br/>
        <input
          type='text'
          id='firstName'
          value={firstName}
          required
          onChange={(event) => setFirstName(event.target.value)}
        /><br/>
        <label htmlFor='lastName'>Last Name:</label><br/>
        <input
          type='text'
          id='lastName'
          value={lastName}
          required
          onChange={(event) => setLastName(event.target.value)}
        /><br/>
        <label htmlFor='title'>Title:</label><br/>
        <input
          type='text'
          id='title'
          value={title}
          onChange={(event) => setTitle(event.target.value)}
        /><br/>
        <label htmlFor='managerEmail'>Manager Email:</label><br/>
        <input
          type='string'
          id='managerEmail'
          value={managerEmail}
          onChange={(event) => setManagerEmail(event.target.value)}
        /><br/>
      </div>}

        <label htmlFor='street'>Street:</label>
        <input
          type='text'
          id='street'
          value={street}
          onChange={(event) => setStreet(event.target.value)}
        />
        <label htmlFor='city'>City:</label>
        <input
          type='text'
          id='city'
          value={city}
          onChange={(event) => setCity(event.target.value)}
        />
        <label htmlFor='state'>State:</label>
        <input
          type='text'
          id='state'
          value={state}
          onChange={(event) => setState(event.target.value)}
        />
        <label htmlFor='zip'>Zip:</label>
        <input
          type='text'
          id='zip'
          value={zip}
          onChange={(event) => setZip(event.target.value)}
        />
        <button type='submit'>Register</button>
      </form>
    </div>
  );
}

export default Register;
