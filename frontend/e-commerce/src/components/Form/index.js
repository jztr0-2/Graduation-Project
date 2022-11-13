import React, { useState } from 'react';
import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBox, faUser, faLock } from '@fortawesome/free-solid-svg-icons';
import styles from './Form.module.scss';
import { createNewProduct, resetProduct, checkRelated, addDetails, CreateDetailsElement } from './handleProducts';
import "./styles.css";
import Category from './Category';

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
              <select name="selectStatus" id="" className={cx('products-status')}>
                  <option disabled selected>Select Status</option>
                  <option value="0">Available</option>
                  <option value="1">Sold Out</option>
              </select>
            </div>
    
            <div className={cx('form-group')} id="productCategory">
              {<Category />}
            </div>


            <div className={cx('form-group')}>
              <textarea name="description" type="text" className={cx('products-description')} placeholder="Product's Description"/>
            </div>

            <div className={cx('form-group')}>
              <div className={cx('checkbox-related')}>
                <input onClick={checkRelated} type="checkbox" className={cx('products-related')} placeholder="Product's Description"/>
                <span> No related products</span>
              </div>
            </div>

            <div className={cx('form-group')}>
              <div className={cx('group-details')}>
                <div className={cx('list-details')}>
                  {<CreateDetailsElement/>}
                </div>
                <div className={cx('products-details')}>
                  <input type="text" onKeyDown={addDetails} name="detailsKey" className={cx('details-key')} placeholder="Key"/>
                  <input type="text" onKeyDown={addDetails} name="detailsDescription" className={cx('details-value')} placeholder="Value"/>
                </div>
              </div>
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
          <button type="button" className={cx('btn', 'btn-reset')} onClick={resetProduct}>Reset</button>
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