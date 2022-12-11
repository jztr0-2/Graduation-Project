import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faCartShopping,
    faDollar,
    faHeart,
    faMap,
    faMoneyCheck,
    faStar,
} from '@fortawesome/free-solid-svg-icons';
import styles from './ProdCard.module.scss';
import Image from '~/components/Image';
import images from '~/assets/images';

const cx = classNames.bind(styles);

function ProdCard({ name, description, price }) {
    return (
        <div
            id="product-carousel"
            className={cx('product-carousel', 'card-mg-20')}
            data-aos="fade-right"
            data-aos-duration="1000"
        >
            <div className={cx('product__item')}>
                <div className={cx('img__box')}>
                    <Image src={images.dest1} alt="dest-img-1" />
                </div>
                <div className={cx('product__item__content')}>
                    <div className={cx('product__details')}>
                        <a href="#" className={cx('product__name')}>
                            {name}
                        </a>
                        <div className={cx('rating')}>
                            <FontAwesomeIcon icon={faHeart} />
                        </div>
                    </div>
                    <p className={cx('desc')}>
                        {description}
                    </p>
                    <div className={cx('product__details')}>
                        <div className={cx('location')}>
                            <FontAwesomeIcon icon={faCartShopping} />
                            <p>Add Cart</p>
                        </div>
                        <div className={cx('price')}>
                            <FontAwesomeIcon icon={faMoneyCheck} />
                            <p>{price}{/* <span>/ package</span> */}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProdCard;
