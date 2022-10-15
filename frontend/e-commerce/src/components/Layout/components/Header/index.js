import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faInstagram, faLinkedinIn, faTwitter } from '@fortawesome/free-brands-svg-icons';
import { faArrowRight, faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import styles from './Header.module.scss';
import * as request from '~/utils/http';
import Form from '~/components/Form';
import { useState } from 'react';

const cx = classNames.bind(styles);

function Header() {
    /* Handle slide img */
    const imgs = [
        'https://images.unsplash.com/photo-1622473541207-c59995fa6b4c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YWlyJTIwY29uZGl0aW9uZXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60',
        'https://www.cnet.com/a/img/resize/2d2e0487bdd32b0892ba5538b1b1219ba72f5be0/hub/2016/10/27/a11c03cc-bc86-427c-b200-fa5c9f4e2f20/lginstaviewproductphotos-8.jpg?auto=webp&width=1200',
        'https://images.unsplash.com/photo-1593305841991-05c297ba4575?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=757&q=80',
    ];
    const [slide, setSlide] = useState(imgs[0]);
    const handleMoveSlide = (action) => {
        const sizeImgs = imgs.length - 1;
        if (action === 'right') {
            setSlide((prevImg) => {
                let prevIndexImg = imgs.findIndex((img) => img === prevImg);
                return prevIndexImg === sizeImgs ? imgs[0] : imgs[prevIndexImg + 1];
            });
        } else {
            setSlide((prevImg) => {
                let prevIndexImg = imgs.findIndex((img) => img === prevImg);
                return prevIndexImg === 0 ? imgs[sizeImgs] : imgs[prevIndexImg - 1];
            });
        }
    };

    return (
        <header className={cx('wrapper')}>
            <div className={cx('container')}>
                <div className={cx('left')}>
                    <nav className={cx('navbar', 'f-box')}>
                        <a className={cx('navbar-brand')} href="/">
                            Logo
                        </a>
                        <ul className={cx('navbar-nav', 'f-box')}>
                            <li className={cx('nav-item')}>
                                <a className={cx('nav-link')} href="/">
                                    Category
                                </a>
                            </li>
                            <li className={cx('nav-item')}>
                                <a className={cx('nav-link')} href="/">
                                    Selling
                                </a>
                            </li>
                            <li className={cx('nav-item')}>
                                <a className={cx('nav-link')} href="/">
                                    About US
                                </a>
                            </li>
                            <li className={cx('nav-item')}>
                                <a className={cx('nav-link')} href="/">
                                    Login
                                </a>
                            </li>
                        </ul>
                    </nav>
                    <div className={cx('slogan')}>
                        <h1>
                            The skydiving's <br /> rush
                        </h1>
                        <p>
                            We believe adventure has no bounds, no <br />
                            definition. It's a frame of mind, a spirit, a choice
                        </p>
                    </div>
                    <div className={cx('icons')}>
                        <FontAwesomeIcon
                            icon={faInstagram}
                            className={cx('icon-ig', 'icon-item')}
                        />
                        <FontAwesomeIcon
                            icon={faTwitter}
                            className={cx('icon-twitter', 'icon-item')}
                        />
                        <FontAwesomeIcon
                            icon={faLinkedinIn}
                            className={cx('icon-in', 'icon-item')}
                        />
                    </div>
                    <div className={cx('left-pagination')} onClick={() => handleMoveSlide('left')}>
                        <FontAwesomeIcon icon={faArrowLeft} />
                    </div>
                </div>
                <div
                    className={cx('right')}
                    style={{
                        background: `url(${slide}) no-repeat center / cover`,
                    }}
                >
                    <div
                        className={cx('right-pagination')}
                        onClick={() => handleMoveSlide('right')}
                    >
                        <FontAwesomeIcon icon={faArrowRight} />
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Header;
