import classNames from 'classnames/bind';
import styles from './About.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faMobileAlt,
    faLaptop,
    faLightbulb,
    faFan,
    faTv,
    faListUl,
} from '@fortawesome/free-solid-svg-icons';

const cx = classNames.bind(styles);

function About() {
    return (
        <div className={cx('container-others')}>
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
                        </p>
                    </div>
                </div>
            </div>
            <div className={cx('about-us-wrapper')}>
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
                        </p>
                    </div>
                </div>
            </div>
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
                    </div>
                </div>
            </div>
        </div>
    );
}

export default About;
