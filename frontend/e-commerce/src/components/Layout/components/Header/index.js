import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faInstagram, faLinkedinIn, faTwitter } from '@fortawesome/free-brands-svg-icons';
import styles from './Header.module.scss';
import * as request from '~/utils/http';
import Form from '~/components/Form';
import { type } from '@testing-library/user-event/dist/type';

const cx = classNames.bind(styles);

function Header() {
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
                                    Jobs
                                </a>
                            </li>
                            <li className={cx('nav-item')}>
                                <a className={cx('nav-link')} href="/">
                                    Talens
                                </a>
                            </li>
                            <li className={cx('nav-item')}>
                                <a className={cx('nav-link')} href="/">
                                    About
                                </a>
                            </li>
                            <li className={cx('nav-item')}>
                                <a className={cx('nav-link')} href="/">
                                    Company
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
                        <FontAwesomeIcon icon={faInstagram} className={cx('icon-ig', 'icon-item')} />
                        <FontAwesomeIcon icon={faTwitter} className={cx('icon-twitter', 'icon-item')} />
                        <FontAwesomeIcon icon={faLinkedinIn} className={cx('icon-in', 'icon-item')} />
                    </div>
                </div>
                <div className={cx('right')}>
                    <div className={cx('right-pagination')}>2</div>
                </div>
                
            </div>
            <Form types="reg"></Form>
            <Form types="log"></Form>

        </header>
    );
}

export default Header;
