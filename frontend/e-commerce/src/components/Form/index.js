import { useRef, useCallback, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import classNames from 'classnames/bind';
import styles from './Form.module.scss';
import { LoginApi } from '~/api/EcommerceApi';
import http from '~/utils/http';

//ADMIN

const cx = classNames.bind(styles);

function FormCustom({ showForm, setShowForm }) {
    const formRef = useRef();
    const closeForm = (e) => {
        console.log('Ref', formRef.current);
        console.log('Target', e.target);
        if (formRef.current === e.target) setShowForm(false);
    };

    const keyPress = useCallback(
        (e) => {
            if (e.key === 'Escape' && showForm) setShowForm(false);
        },
        [setShowForm, showForm],
    );

    const click = (event) => {
        event.preventDefault();
        var username = document.getElementById('user').value;
        var password = document.getElementById('pass').value;

        //Prevent page reload
        const data = {
            email: username,
            password: password,
        };
        console.log(data);
        LoginApi.adminLogin(data)
            .then((res) => {
                localStorage.setItem('token', 'Bearer ' + res.data.data);
                //succesfuly
                setTimeout(() => window.location.reload(), 3000);
            })
            .catch((err) => {
                //faild
            });
    };

    return (
        <>
            {showForm ? (
                <div className={cx('wrapper')} ref={formRef} onClick={closeForm}>
                    <div className={cx('box')}>
                        <FontAwesomeIcon
                            icon={faXmark}
                            className={cx('close')}
                            onClick={() => setShowForm((prev) => !prev)}
                        />
                        <div className={cx('form')}>
                            <h2>Sign in</h2>
                            <div className={cx('input-box')}>
                                <input id="user" required="required" />
                                <span>Username</span>
                                <i></i>
                            </div>
                            <div className={cx('input-box')}>
                                <input id="pass" type="password" required="required" />
                                <span>Password</span>
                                <i></i>
                            </div>
                            <div className={cx('links')}>
                                <a href="/">Forgot Password</a>
                                <a href="/">Signup</a>
                            </div>
                            <input type="submit" onClick={click} value="Login" />
                        </div>
                    </div>
                </div>
            ) : null}
        </>
    );
}

export default FormCustom;
