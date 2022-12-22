import { useRef, useCallback, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import classNames from 'classnames/bind';
import styles from './Form.module.scss';
import { LoginApi } from '~/api/EcommerceApi';
import { UserApi } from '~/api/EcommerceApi';


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

    const login = (event) => {
        event.preventDefault();
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;

        //Prevent page reload
        const data = {
            email: username,
            password: password,
        };
        console.log(data);
        LoginApi.userLogin(data)
            .then((res) => {
                console.log(res.data);
                localStorage.setItem('token', 'Bearer ' + res.data);
                UserApi.userInfo().then((resp)=>{
                    console.log(resp.data)
                    localStorage.setItem("userId",resp.data.id);
                    localStorage.setItem("userEmail",resp.data.email);
                    localStorage.setItem("userPhone",resp.data.phone);
                    localStorage.setItem("userName",resp.data.firstName + resp.data.lastName);
                    localStorage.setItem("userImage",resp.data.image);
                    window.location.reload()
                }).catch((err)=>{
                    console.log(err)
                });
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
                                <input required="required" id = "username"/>
                                <span>Username</span>
                                <i></i>
                            </div>
                            <div className={cx('input-box')}>
                                <input type="password" required="required" id = "password"/>
                                <span>Password</span>
                                <i></i>
                            </div>
                            <div className={cx('links')}>
                                <a href="/">Forgot Password</a>
                                <a href="/">Signup</a>
                            </div>
                            <input type="submit" value="Login" onClick={login}/>
                        </div>
                    </div>
                </div>
            ) : null}
        </>
    );
}

export default FormCustom;
