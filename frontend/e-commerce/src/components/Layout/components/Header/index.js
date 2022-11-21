import { useEffect, useState } from 'react';
import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faInstagram, faLinkedinIn, faTwitter } from '@fortawesome/free-brands-svg-icons';
import {
    faArrowRight,
    faArrowLeft,
    faCartShopping,
    faUser,
} from '@fortawesome/free-solid-svg-icons';
import styles from './Header.module.scss';
import * as request from '~/utils/http';
import Form from '~/components/Form';
import Image from '~/components/Image';
import images from '~/assets/images';
const cx = classNames.bind(styles);

function Header() {
    const slider = (img) => {
        document.getElementById('main-img').src = img;
    };
    return (
        <div className={cx('wrapper')}>
            <header>
                <a href="/" className={cx('logo')}>
                    <i className={cx('bx bx-headphone')}></i>Ecommerce
                </a>

                <ul className={cx('navbar')}>
                    <li>
                        <a href="/" className={cx('active')}>
                            Home
                        </a>
                    </li>
                    <li>
                        <a href="/">Category</a>
                    </li>
                    <li>
                        <a href="/">Selling</a>
                    </li>
                    <li>
                        <a href="/">FAQ</a>
                    </li>
                </ul>

                <div className={cx('header-icons')}>
                    <FontAwesomeIcon icon={faCartShopping} className={cx('header-icon')} />
                    <FontAwesomeIcon icon={faUser} className={cx('header-icon')} />
                </div>
            </header>

            <section className={cx('home')}>
                <div className={cx('home-img')}>
                    <Image src={images.mainHeader} className={cx('one')} id="main-img" />
                </div>

                <div className={cx('home-text')}>
                    <h1>Super speed kettle</h1>
                    <h5>Smarter way to cook water</h5>
                    <h3>199.000 đ</h3>
                    <a href="/" className={cx('btn')}>
                        Shop Now
                    </a>
                </div>
            </section>

            <div className={cx('main')}>
                <div className={cx('row')}>
                    <li>
                        <Image src={images.thumbHeader} onClick={() => slider(images.mainHeader)} />
                    </li>
                </div>

                <div className={cx('row', 'row2')}>
                    <li>
                        <Image
                            src={images.thumbHeader2}
                            onClick={() => slider(images.mainHeader2)}
                        />
                    </li>
                </div>

                <div className={cx('row', 'row3')}>
                    <li>
                        <Image
                            src={images.thumbHeader3}
                            onClick={() => slider(images.mainHeader3)}
                        />
                    </li>
                </div>
            </div>
        </div>
    );
}
export default Header;
