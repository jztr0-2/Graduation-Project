import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faTwitter,
    faDribbble,
    faFacebookF,
    faGooglePlusG,
    faPinterestP,
} from '@fortawesome/free-brands-svg-icons';
import { faAt, faHomeLg, faMobileAlt } from '@fortawesome/free-solid-svg-icons';
import { faEnvelope } from '@fortawesome/free-regular-svg-icons';
import logo from '../../../../assets/images/logo.png';

import classNames from 'classnames/bind';
import styles from './Footer.module.scss';

const cx = classNames.bind(styles);

function Footer() {
    return (
        <footer className={cx('wrapper')}>
            <div className={cx('footer-information')}>
                <a className={cx('logo')} href="/">
                    <img src={logo} />
                </a>
                <div className={cx('container')}>
                    <div className={cx('footer-list')}>
                        <p className={cx('title')}>About Us</p>
                        <p className={cx('description')}>
                            Lorem ipsum dolor sit amet consectetur adipisicing elit. Non, nostrum!
                            Tempora minima ducimus laudantium saepe sapiente! Omnis consequuntur
                            incidunt amet sequi qui, accusamus aliquam eos rerum doloremque
                            cupiditate dolore dolores.
                        </p>
                    </div>

                    <div className={cx('footer-list')}>
                        <p className={cx('title')}>Keep Connected</p>
                        <div className={cx('list-social', 'list-item')}>
                            <div className={cx('social-icon')}>
                                <a href="/">
                                    <span className={cx('icons', 'icon-facebook')}>
                                        {' '}
                                        <FontAwesomeIcon
                                            icon={faFacebookF}
                                            className={cx('icon-item')}
                                        />{' '}
                                    </span>
                                    Facebook
                                </a>
                            </div>

                            <div className={cx('social-icon')}>
                                <a href="/">
                                    <span className={cx('icons', 'icon-twitter')}>
                                        {' '}
                                        <FontAwesomeIcon
                                            icon={faTwitter}
                                            className={cx('icon-item')}
                                        />{' '}
                                    </span>
                                    Twitter
                                </a>
                            </div>

                            <div className={cx('social-icon')}>
                                <a href="/">
                                    <span className={cx('icons', 'icon-google-plus')}>
                                        {' '}
                                        <FontAwesomeIcon
                                            icon={faGooglePlusG}
                                            className={cx('icon-item')}
                                        />{' '}
                                    </span>
                                    Google Plus
                                </a>
                            </div>

                            <div className={cx('social-icon')}>
                                <a href="/">
                                    <span className={cx('icons', 'icon-dribbble')}>
                                        {' '}
                                        <FontAwesomeIcon
                                            icon={faDribbble}
                                            className={cx('icon-item')}
                                        />{' '}
                                    </span>
                                    Dribbble
                                </a>
                            </div>

                            <div className={cx('social-icon')}>
                                <a href="/">
                                    <span className={cx('icons', 'icon-piterest')}>
                                        {' '}
                                        <FontAwesomeIcon
                                            icon={faPinterestP}
                                            className={cx('icon-item')}
                                        />{' '}
                                    </span>
                                    Pinterest
                                </a>
                            </div>
                        </div>
                    </div>

                    <address className={cx('footer-list')}>
                        <p className={cx('title')}>Contact Information</p>
                        <div className={cx('list-item')}>
                            <div className={cx('social-icon')}>
                                <span className={cx('icons')}>
                                    <FontAwesomeIcon icon={faHomeLg} className={cx('icon-item')} />
                                </span>

                                <span>4517 Washington Ave.</span>
                            </div>

                            <div className={cx('social-icon')}>
                                <a href="tel:+(480)555-0103">
                                    <span className={cx('icons')}>
                                        <FontAwesomeIcon
                                            icon={faMobileAlt}
                                            className={cx('icon-ig', 'icon-item')}
                                        />
                                    </span>
                                    (480) 555-0103
                                </a>
                            </div>

                            <div className={cx('social-icon')}>
                                <a href="mailto:debra.holt@example.com">
                                    <span className={cx('icons')}>
                                        <FontAwesomeIcon icon={faAt} className={cx('icon-item')} />
                                    </span>
                                    debra.holt@example.com
                                </a>
                            </div>
                        </div>
                    </address>

                    <div className={cx('footer-list', 'news-letter')}>
                        <p className={cx('title')}>News Letter</p>
                        <form className={cx('form-news-letter')}>
                            <div className={cx('form-group')}>
                                <FontAwesomeIcon icon={faEnvelope} className={cx('icon-item')} />
                                <input
                                    type="email"
                                    className={cx('email-news-letter')}
                                    placeholder="Enter email here"
                                />
                                <p className={cx('line')}></p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div className={cx('footer-terms')}>
                <div className={cx('container')}>
                    <p>© 2022 - All Rights Reserved by Jztr</p>
                    <div className={cx('footer-privacy')}>
                        <a href="/">Company Information</a>
                        <span className={cx('line')}></span>
                        <a href="/">Privacy Policy</a>
                        <span className={cx('line')}></span>
                        <a href="/">Terms &amp; Conditions</a>
                    </div>
                </div>
            </div>
        </footer>
    );
}
export default Footer;
