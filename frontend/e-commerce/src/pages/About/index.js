import classNames from 'classnames/bind';
import styles from './About.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faMobileAndroidAlt,
    faTv,
    faFan,
    faLaptop,
    faLightbulb,
    faListUl,
} from '@fortawesome/free-solid-svg-icons';
const cx = classNames.bind(styles);

function About() {
    const changeTabs = () => {};

    return (
        <div className={cx('container-others')}>
            <div className={cx('services-about')}>
                <h2 className={cx('services-title')}>Our Servies</h2>
                <div className={cx('services-content')}>
                    <div className={cx('services-item')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faMobileAndroidAlt} />
                        </figure>
                        <p className={cx('title')}>SmartPhones</p>
                        <p className={cx('description')}>
                            Have all kinds of smartphones of all famous brands that you need
                        </p>
                    </div>
                    <div className={cx('services-item')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faTv} />
                        </figure>
                        <p className={cx('title')}>Televisions</p>
                        <p className={cx('description')}>
                            A system for transmitting visual images and sound that are reproduced on
                            screens, chiefly used to broadcast programs for entertainment,
                            information, and education.
                        </p>
                    </div>
                    <div className={cx('services-item')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faFan} />
                        </figure>
                        <p className={cx('title')}>Electric Fans</p>
                        <p className={cx('description')}>
                            All types of modern fans at affordable prices
                        </p>
                    </div>
                    <div className={cx('services-item')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faLaptop} />
                        </figure>
                        <p className={cx('title')}>Laptops</p>
                        <p className={cx('description')}>
                            Laptops with many designs, configurations, diverse colors, and 100%
                            genuine guarantee
                        </p>
                    </div>
                    <div className={cx('services-item')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faLightbulb} />
                        </figure>
                        <p className={cx('title')}>Lights</p>
                        <p className={cx('description')}>
                            Have all types of electric lights that are trendy, stylish and suitable
                            for your home
                        </p>
                    </div>
                    <div className={cx('services-item')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faListUl} />
                        </figure>
                        <p className={cx('title')}>Others</p>
                        <p className={cx('description')}>
                            We also have other types of home electronics such as refrigerators, air
                            conditioners, heaters, ... Come by !
                        </p>
                    </div>
                </div>
            </div>
            <div className={cx('about-us-wrapper')}>
                <h2 className={cx('services-title')}>Overview</h2>
                <div className={cx('tab-wrapper')}>
                    <div className={cx('tabcontent')}>
                        <h3>E-commerce</h3>
                        <p>
                            E-commerce is a retail company in Vietnam with the main business of
                            retailing mobile phones, digital devices and consumer electronics. With
                            the desire to bring the best experience of shopping at our store. With a
                            warehouse of goods with many types, colors and from many other brands,
                            people trust to entrust products. We are always ready to give you the
                            best prices possible.
                        </p>
                    </div>
                </div>
            </div>
            <div className={cx('locations-wrapper')}>
                <h2 className={cx('services-title')}>Location</h2>
                <div className={cx('location-content')}>
                    <p className={cx('title')}>
                        121 Rock Sreet, 21 Avenue, New York, NY 92103-9000
                    </p>
                    <div className={cx('description')}>
                        <div className={cx('description-content')}>
                            <span>Email: ecommerce@gmail.com</span>
                            <span>Phone: +84 728592748</span>
                            <span>Fax: +84 728592748</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default About;
