import React, { useState } from 'react';
import classNames from 'classnames/bind';
import styles from './Form.module.scss';
import "./styles.css";

const cx = classNames.bind(styles);

function Form({ types }) {

     // React States
  const [errorMessages, setErrorMessages] = useState({});
  const [isSubmitted, setIsSubmitted] = useState(false);

  // User Login info
  const database = [
    { email: "thuyen@gmail.com",
      username: "user1",
      password: "pass1",
      cffassword: "pass1"
    },
    {
      username: "user2",
      password: "pass2"
    }
  ];

  const errors = {
    uname: "invalid username",
    pass: "invalid password"
  };

  const handleSubmit = (event) => {
    //Prevent page reload
    event.preventDefault();

    var { uname, pass } = document.forms[0];

    // Find user login info
    const userData = database.find((user) => user.username === uname.value);

    // Compare user info
    if (userData) {
      if (userData.password !== pass.value) {
        // Invalid password
        setErrorMessages({ name: "pass", message: errors.pass });
      } else {
        setIsSubmitted(true);
      }
    } else {
      // Username not found
      setErrorMessages({ name: "uname", message: errors.uname });
    }
  };

  // Generate JSX code for error message
  const renderErrorMessage = (name) =>
    name === errorMessages.name && (
      <div className="error">{errorMessages.message}</div>
    );
    const renderFormLogin = (
        <div className="form">
          <form onSubmit={handleSubmit}>
            <div className="input-container">
              <label>Username </label>
              <input type="text" name="uname" required />
              {renderErrorMessage("uname")}
            </div>
            <div className="input-container">
              <label>Password </label>
              <input type="password" name="pass" required />
              {renderErrorMessage("pass")}
            </div>
            <div className="button-container">
              <input type="submit" />
            </div>
          </form>
        </div>
      );
      const renderFormReg = (
        <div className="form">
          <form onSubmit={handleSubmit}>
          <div className="input-container">
              <label>Email </label>
              <input type="text" name="email" required />
              {renderErrorMessage("email")}
            </div>
            <div className="input-container">
              <label>Username </label>
              <input type="text" name="uname" required />
              {renderErrorMessage("uname")}
            </div>
            <div className="input-container">
              <label>Password </label>
              <input type="password" name="pass" required />
              {renderErrorMessage("pass")}
            </div>
            <div className="input-container">
              <label>Comfirm Password </label>
              <input type="password" name="cfpass" required />
              {renderErrorMessage("cfpass")}
            </div>
            <div className="button-container">
              <input type="submit" />
            </div>
          </form>
        </div>
      );
    if(types === "log") {    
        return <div className={cx('wrapper')}>
            <div className="app">
      <div className="login-form">
        <div className="title">LogIn System</div>
        {isSubmitted ? <div>Noti: Login is successfully!</div> : renderFormLogin}
      </div>
    </div>
        </div>
    } if(types === "reg"){
        return <div className={cx('wrapper')}>
               <div className="app">
      <div className="login-form">
        <div className="title">Reg Account</div>
        {isSubmitted ? <div>Noti: Reg is successfully!</div> : renderFormReg}
      </div>
    </div>
        </div>
    }
    else{
return<div></div>
    }
   
}

export default Form;