import { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faInstagram } from '@fortawesome/free-brands-svg-icons';
import { faBars, faCartShopping, faTimes } from '@fortawesome/free-solid-svg-icons';
import classNames from 'classnames/bind';
import styles from './Header.module.scss';
import FormCustom from '~/components/Form';
const cx = classNames.bind(styles);

function Header() {
    const [statusBars, setStatusBars] = useState({ clicked: false });
    const handleStatusBars = () => {
        setStatusBars((status) => ({ clicked: !status.clicked }));
    };
    /* handle show form */
    const [showForm, setShowForm] = useState(false);
    return (
        <div className={cx('wrapper')}>
            <div className={cx('navbar')}>
                <a href="/" className={cx('logo')}>
                    Logo
                </a>
                <ul className={cx('navbar-nav', statusBars.clicked ? 'active-nav' : null)}>
                    <li className={cx('nav-item')}>
                        <a className={cx('active')} href="/">
                            Home
                        </a>
                    </li>
                    <li className={cx('nav-item')}>
                        <a href="/">Category</a>
                    </li>
                    <li className={cx('nav-item')}>
                        <a href="/">Selling</a>
                    </li>
                    <li className={cx('nav-item')}>
                        <a href="/">About</a>
                    </li>
                    <li className={cx('nav-item')}>
                        <a href="/">Contact</a>
                    </li>
                </ul>
                <div className={cx('mobile')} onClick={handleStatusBars}>
                    <FontAwesomeIcon icon={statusBars.clicked ? faTimes : faBars} />
                </div>
                <ul className={cx('profile')}>
                    <li
                        className={cx('profile-item', 'profile-button')}
                        onClick={() => {
                            setShowForm(!showForm);
                        }}
                    >
                        Sign in
                    </li>
                    <li className={cx('profile-item')}>
                        <a href="/">
                            <FontAwesomeIcon icon={faCartShopping} />
                        </a>
                    </li>
                </ul>
            </div>
            <FormCustom showForm={showForm} setShowForm={setShowForm} />
        </div>
    );
}
export default Header;
