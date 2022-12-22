import { useRef, useCallback, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import classNames from 'classnames/bind';
import styles from './Form.module.scss';
import { LoginApi } from '~/api/EcommerceApi';
<<<<<<< HEAD
import http from '~/utils/http';

//ADMIN
=======
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366

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

<<<<<<< HEAD
    const click = (event) => {
        event.preventDefault();
        var username = document.getElementById('user').value;
        var password = document.getElementById('pass').value;
=======
    const login = (event) => {
        event.preventDefault();
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366

        //Prevent page reload
        const data = {
            email: username,
            password: password,
        };
        console.log(data);
<<<<<<< HEAD
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
=======
        LoginApi.userLogin(data)
            .then((res) => {
                console.log(res.data);
                localStorage.setItem('token', 'Bearer ' + res.data);

                // setTimeout(() => window.location.reload(), 3000);
            })
            .catch((err) => {
                console.log(err.response.data)
                console.log(err.response.data.message)
            });
    };

    useEffect(() => {
        document.addEventListener('keydown', keyPress);
        // call clean up func to clean event avoid out of memory;
        return () => document.removeEventListener('keydown', keyPress);
    }, [keyPress]);
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366

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
<<<<<<< HEAD
                                <input id="user" required="required" />
=======
                                <input required="required" id = "username"/>
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366
                                <span>Username</span>
                                <i></i>
                            </div>
                            <div className={cx('input-box')}>
<<<<<<< HEAD
                                <input id="pass" type="password" required="required" />
=======
                                <input type="password" required="required" id = "password"/>
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366
                                <span>Password</span>
                                <i></i>
                            </div>
                            <div className={cx('links')}>
                                <a href="/">Forgot Password</a>
                                <a href="/">Signup</a>
                            </div>
<<<<<<< HEAD
                            <input type="submit" onClick={click} value="Login" />
=======
                            <input type="submit" value="Login" onClick={login}/>
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366
                        </div>
                    </div>
                </div>
            ) : null}
        </>
    );
}

export default FormCustom;
