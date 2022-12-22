import { useStore, actions } from '~/store';
import { useEffect, useState } from 'react';
import queryString from 'query-string';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBars, faCartShopping, faTimes } from '@fortawesome/free-solid-svg-icons';
import classNames from 'classnames/bind';
import styles from './Header.module.scss';
import FormCustom from '~/components/Form';
import * as request from '~/utils/http';
import { CategoryApi } from '~/api/EcommerceApi';
import { Link } from 'react-router-dom';
import logo from '../../../../assets/images/logo.png';

const cx = classNames.bind(styles);

function Header() {
    const [state, dispatch] = useStore();
    const { carts, cartItem } = state;

    const [statusBars, setStatusBars] = useState({ clicked: false });
    const handleStatusBars = () => {
        setStatusBars((status) => ({ clicked: !status.clicked }));
    };
    /* CATEGORY LIST */
    const [categories, setCategories] = useState([]);
    useEffect(() => {
        CategoryApi.getAll().then(
            (res) => {
                if (res.data) {
                    setCategories(res.data);
                }
            },
            (err) => {
                console.log('error', err);
            },
        );
    }, []);
    /* handle show form */
    const [showForm, setShowForm] = useState(false);

    return (
        <div className={cx('wrapper')}>
            <div className={cx('navbar')}>
                <Link to="/" className={cx('logo')}>
                    <img src={logo} />
                </Link>
                <ul className={cx('navbar-nav', statusBars.clicked ? 'active-nav' : null)}>
                    <li className={cx('nav-item')}>
                        <Link className={cx('active')} to="/">
                            Home
                        </Link>
                    </li>
                    <li className={cx('nav-item')}>
                        <Link to="/public/products/category/1">Category</Link>
                        <ul className={cx('dropdown__list')}>
                            {categories.map((category, index) => {
                                return index < 3 ? (
                                    <li className={cx('dropdown__item')} key={category.id}>
                                        <Link
                                            to={`/category/${category.id}?page=1&limit=6`}
                                            className={cx('dropdown__text')}
                                        >
                                            {category.name.toLowerCase()}
                                        </Link>
                                        <FontAwesomeIcon icon={faBars} />
                                    </li>
                                ) : null;
                            })}
                            {categories.length > 3 ? (
                                <li className={cx('dropdown__item')}>
                                    <Link
                                        to="/categories/views?page=1"
                                        className={cx('dropdown__text')}
                                    >
                                        View more
                                    </Link>
                                    <FontAwesomeIcon icon={faBars} />
                                </li>
                            ) : (
                                <></>
                            )}
                            {/* Display show more  */}
                        </ul>
                    </li>
                    <li className={cx('nav-item')}>
                        <a href="/">Selling</a>
                    </li>
                    <li className={cx('nav-item')}>
                        <Link to="/about">About</Link>
                    </li>
                    <li className={cx('nav-item')}>
                        <Link to="/contact">Contact</Link>
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
                    <li className={cx('profile-item', 'counter-item')}>
                        <Link to="/">
                            <FontAwesomeIcon icon={faCartShopping} />
                        </Link>
                        {carts.length > 0 ? <span>{carts.length}</span> : null}
                    </li>
                </ul>
            </div>
            <FormCustom showForm={showForm} setShowForm={setShowForm} />
        </div>
    );
}
export default Header;
