import classNames from 'classnames/bind';
import styles from './CategoryProdCard.module.scss';
import Image from '~/components/Image';
const cx = classNames.bind(styles);

function CategoryProdCard({ categoryName, amountProd, amountBuyer, amountFav }) {
    return (
        <div className={cx('wrapper')}>
            <div className={cx('card')}>
                <div className={cx('imgBx')}>
                    <Image
                        src={'https://hc.com.vn/i/ecommerce/media/ckeditor_3674236.jpg'}
                        alt="img"
                    />
                </div>
                <div className={cx('content')}>
                    <div className={cx('details')}>
                        <h2>
                            {categoryName}
                            <br />
                            <span>Best experience for you</span>
                        </h2>
                        <div className={cx('data')}>
                            <h3>
                                {amountProd}
                                <br />
                                <span>Product</span>
                            </h3>
                            <h3>
                                {amountBuyer}
                                <br />
                                <span>Buyer</span>
                            </h3>
                            <h3>
                                {amountFav}
                                <br />
                                <span>Following</span>
                            </h3>
                        </div>
                        <div className={cx('actionBtn')}>
                            <button>More</button>
                            <button>Favorite</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CategoryProdCard;
