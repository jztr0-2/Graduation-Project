import { useRef, useCallback, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import classNames from 'classnames/bind';
import styles from './Form.module.scss';

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
                                <input required="required" />
                                <span>Username</span>
                                <i></i>
                            </div>
                            <div className={cx('input-box')}>
                                <input type="password" required="required" />
                                <span>Password</span>
                                <i></i>
                            </div>
                            <div className={cx('links')}>
                                <a href="/">Forgot Password</a>
                                <a href="/">Signup</a>
                            </div>
                            <input type="submit" value="Login" />
                        </div>
                    </div>
                </div>
            ) : null}
        </>
    );
}

export default FormCustom;
