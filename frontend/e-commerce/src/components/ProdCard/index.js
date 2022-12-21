import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCartShopping, faHeart, faMoneyCheck } from '@fortawesome/free-solid-svg-icons';
import { ToastContainer, toast } from 'react-toastify';
import styles from './ProdCard.module.scss';
import Image from '~/components/Image';
import images from '~/assets/images';
import LoadingSkeleton from '../LoadingSkeleton';
import { Link } from 'react-router-dom';

const cx = classNames.bind(styles);

const ProdCard = ({ id, name, description, price, className, urlImg, ...props }) => {
    // Handle url img invalid
    if (!urlImg) {
        urlImg = images.noImage;
    }

    return (
        <>
            <div
                id="product-carousel"
                className={cx('product-carousel', 'card-mg-20', className)}
                data-aos="fade-right"
                data-aos-duration="1000"
                {...props}
            >
                <Link to={`/product/details/${id}`}>
                    <div className={cx('product__item')}>
                        <div className={cx('img__box')}>
                            <Image src={urlImg} alt="dest-img-1" />
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
                            <p className={cx('desc')}>{description}</p>
                            <div className={cx('product__details')}>
                                <div className={cx('location')}>
                                    <FontAwesomeIcon icon={faCartShopping} />
                                    <p>Add Cart</p>
                                </div>
                                <div className={cx('price')}>
                                    <FontAwesomeIcon icon={faMoneyCheck} />
                                    <p>
                                        {price} đ{/* <span>/ package</span> */}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </Link>
            </div>
            <ToastContainer />
        </>
    );
};

const Loading = () => {
    return (
        <div
            id="product-carousel"
            className={cx('product-carousel', 'card-mg-20')}
            data-aos="fade-right"
            data-aos-duration="1000"
        >
            <div className={cx('product__item')}>
                <LoadingSkeleton className="w-[300px] h-[168px] rounded-t-[14px]"></LoadingSkeleton>
                <div className={cx('product__item__content')}>
                    <div className={cx('product__details')}>
                        <LoadingSkeleton className="w-full h-[18px] mb-4"></LoadingSkeleton>
                    </div>
                    <p className={cx('desc')}>
                        <LoadingSkeleton className="w-full h-[10px] mb-2"></LoadingSkeleton>
                        <LoadingSkeleton className="w-full h-[10px] mb-2"></LoadingSkeleton>
                        <LoadingSkeleton className="w-full h-[10px] mb-2"></LoadingSkeleton>
                        <LoadingSkeleton className="w-full h-[10px]"></LoadingSkeleton>
                    </p>
                    <div className={cx('product__details')}>
                        <LoadingSkeleton className="w-full h-[18px] mb-2"></LoadingSkeleton>
                    </div>
                </div>
            </div>
        </div>
    );
};

ProdCard.Loading = Loading;

export default ProdCard;
