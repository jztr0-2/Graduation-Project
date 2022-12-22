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
<<<<<<< HEAD
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
=======
            <div className={cx('our-services')}>
                <label className={cx('title')}>Our Services</label>
                <div className={cx('services-content')}>
                    <div className={cx('service')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faMobileAlt} />
                        </figure>
                        <p className={cx('title-service')}>Smartphones</p>
                        <p className={cx('description-service')}>
                            A smartphone is a cellular telephone with an integrated computer and
                            other features not originally associated with telephones such as an
                            operating system, web browsing, and the ability to run software
                            applications.
                        </p>
                    </div>
                    <div className={cx('service')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faTv} />
                        </figure>
                        <p className={cx('title-service')}>Televisions</p>
                        <p className={cx('description-service')}>
                            A smartphone is a cellular telephone with an integrated computer and
                            other features not originally associated with telephones such as an
                            operating system, web browsing, and the ability to run software
                            applications.
                        </p>
                    </div>
                    <div className={cx('service')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faFan} />
                        </figure>
                        <p className={cx('title-service')}>Fans</p>
                        <p className={cx('description-service')}>
                            A smartphone is a cellular telephone with an integrated computer and
                            other features not originally associated with telephones such as an
                            operating system, web browsing, and the ability to run software
                            applications.
                        </p>
                    </div>
                    <div className={cx('service')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faLaptop} />
                        </figure>
                        <p className={cx('title-service')}>Laptops</p>
                        <p className={cx('description-service')}>
                            A smartphone is a cellular telephone with an integrated computer and
                            other features not originally associated with telephones such as an
                            operating system, web browsing, and the ability to run software
                            applications.
                        </p>
                    </div>
                    <div className={cx('service')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faLightbulb} />
                        </figure>
                        <p className={cx('title-service')}>Lights</p>
                        <p className={cx('description-service')}>
                            A smartphone is a cellular telephone with an integrated computer and
                            other features not originally associated with telephones such as an
                            operating system, web browsing, and the ability to run software
                            applications.
                        </p>
                    </div>
                    <div className={cx('service')}>
                        <figure className={cx('icon-wrapper')}>
                            <FontAwesomeIcon icon={faListUl} />
                        </figure>
                        <p className={cx('title-service')}>Others</p>
                        <p className={cx('description-service')}>
                            A smartphone is a cellular telephone with an integrated computer and
                            other features not originally associated with telephones such as an
                            operating system, web browsing, and the ability to run software
                            applications.
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366
                        </p>
                    </div>
                </div>
            </div>
            <div className={cx('about-us-wrapper')}>
<<<<<<< HEAD
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
=======
                <div className={cx('about-us-content')}>
                    <label className={cx('title')}>Overview</label>
                    <div className={cx('about-us-text')}>
                        <p className={cx('title')}>E-commerce</p>
                        <p className={cx('description')}>
                            E-commerce is a company specializing in the retailing of electronic
                            products. Here we provide our customers with the best shopping
                            experience. With a variety of models, goods and trusted by big brands,
                            products are entrusted. We always find every solution to make the
                            product reach our customers safely, securely and always with the most
                            reasonable price.
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366
                        </p>
                    </div>
                </div>
            </div>
<<<<<<< HEAD
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
=======
            <div className={cx('location-wrapper')}>
                <div className={cx('location-content')}>
                    <label className={cx('title')}>Location</label>
                    <div className={cx('location-text')}>
                        <p className={cx('title-text')}>
                            137 Nguyen Thi Thap, Thanh Khe Tay, Lien Chieu, Đa Nang
                        </p>
                        <p className={cx('description-text')}>
                            <span>Email: ecommerce@gmail.com</span>
                            <span>Phone: +84 7283859378</span>
                            <span>Fax: +84 7283859378</span>
                        </p>
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366
                    </div>
                </div>
            </div>
        </div>
    );
}

export default About;
