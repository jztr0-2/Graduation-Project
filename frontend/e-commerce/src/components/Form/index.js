import React, { useState } from 'react';
import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBox, faUser, faLock } from '@fortawesome/free-solid-svg-icons';
import styles from './Form.module.scss';
import "./styles.css";
import * as request from '~/utils/http';

const cx = classNames.bind(styles);

function Form({ types }) {

  function getCategory(){
    request.get("admin/categories")
      .then((response) => {
        const data = response.data;
        const dataNew = ["laptop", "tv", "pin"];
        console.log();

        const jsx = (
          <select name="selectCategory" id="" className={cx('products-category')}>
              <option disabled selected>Select Category</option>
              {dataNew.map((data) => {
                <option value="1">${data}</option>
              })}
          </select>
        );

        ReactDOM.render(jsx, document.getElementById("productStatus"));
      })
      .catch((error) => {
        console.log(error);
      })
  }

  getCategory();
  const createNewProduct = (e) => {
    e.preventDefault();
    var { productName, selectStatus, selectCategory, description } = document.forms[1];

    const product = {
      name: productName.value,
      status: Number(selectStatus.value),
      description: description.value,
      categoryId: Number(selectCategory.value),
    }

    console.log(product);
    request.post("/admin/products", product)
      .then(function (response) {
        console.log(response)
      })
      .catch(function (error) {
        console.log(error);
      })
  }

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
          <div className="top">
          
        </div>
        <form onSubmit={handleSubmit}>
          <div className="input-container">
        
            <label><FontAwesomeIcon icon={faUser} className={cx('icon-item')} />       USERNAME </label>
            <input type="text" name="uname" required />
            {renderErrorMessage("uname")}
          </div>
          <div className="input-container">
            <label><FontAwesomeIcon icon={faLock} className={cx('icon-item')} />       PASSWORD </label>
            <input type="password" name="pass" required />
            {renderErrorMessage("pass")}
          </div>
          <div className="button-container">
            <input type="submit" value="LOGIN"/>
          </div>
        </form>
      </div>
    );

    const renderFormReg = (
      <div className="form">
        <div className="top">
          
        </div>
        <form onSubmit={handleSubmit}>
          <div className="input-container">
            <label><FontAwesomeIcon icon={faBox} className={cx('icon-item')} /> YOUR EMAIL </label>
            <input type="text" name="email" required />
            {renderErrorMessage("email")}
          </div>

          <div className="input-container">
            <label><FontAwesomeIcon icon={faUser} className={cx('icon-item')} />  USERNAME </label>
            <input type="text" name="uname" required />
            {renderErrorMessage("uname")}
          </div>

          <div className="input-container">
            <label><FontAwesomeIcon icon={faLock} className={cx('icon-item')} /> PASSWORD </label>
            <input type="password" name="pass" required />
            {renderErrorMessage("pass")}
          </div>

          <div className="input-container">
            <label><FontAwesomeIcon icon={faLock} className={cx('icon-item')} /> COMFIRM PASSWORD </label>
            <input type="password" name="cfpass" required />
            {renderErrorMessage("cfpass")}
          </div>

          <div className="button-container">
            <input type="submit" value="Đăng Ký"/>
          </div>
        </form>
      </div>
    );

    const renderFormAddProducts = (
      <form action="" className={cx('form-products')}>
        <div className={cx('form-information')}>
          <div className={cx('form-content')}>
            <div className={cx('form-group')}>
              <input type="text" className={cx('products-name')} name="productName" placeholder="Product's Name"/>
            </div>
    
            <div className={cx('form-group')} id="productStatus">
              
            </div>
    
            <div className={cx('form-group')}>
              <select name="selectCategory" id="" className={cx('products-category')}>
                <option disabled selected>Select Category</option>
                <option value="1">B</option>
                <option value="3">C</option>
                <option value="4">D</option>
              </select>
            </div>

            <div className={cx('form-group')}>
              <textarea name="description" type="text" className={cx('products-description')} placeholder="Product's Description"/>
            </div>
          </div>

          <div className={cx('form-img')}>
            <div className={cx('form-group')}>
              <div className={cx('images')}></div>
              <input name='image' type="file" className={cx('products-name')} />
            </div>
          </div>
        </div>

        <div className={cx('form-cta')}>
          <button type="button" className={cx('btn', 'btn-create')} onClick={createNewProduct}>Create</button>
          <button type="button" className={cx('btn', 'btn-update')}>Update</button>
          <button type="button" className={cx('btn', 'btn-delete')}>Delete</button>
          <button type="button" className={cx('btn', 'btn-reset')}>Reset</button>
        </div>
      </form>
    );
    
    if (types === "addProducts") {
      return <div className={cx('wrapper')}>
                <div className="app">
                  {renderFormAddProducts}
                </div>
              </div>
    }

    if(types === "log") {    
      return  <div className={cx('wrapper')}>
                <div className="app">
                  <div className="login-form">
                    <div className="title">LogIn System</div>
                    {isSubmitted ? <div>Noti: Login is successfully!</div> : renderFormLogin}
                  </div>
                </div>
              </div>
    } 
    
    if(types === "reg") {
      return  <div className={cx('wrapper')}>
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