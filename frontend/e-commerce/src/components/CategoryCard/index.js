import moment from 'moment';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClock, faStar } from '@fortawesome/free-solid-svg-icons';
import classNames from 'classnames/bind';
import styles from './CategoryCard.module.scss';
import Image from '~/components/Image';
import images from '~/assets/images';

const cx = classNames.bind(styles);
function CategoryCard({ title, description, postDay, discount = false, isNew = false }) {
    return (
        <div
            id="category-carousel"
            className={cx('category-carousel', 'card-mg-20')}
            data-aos="fade-right"
            data-aos-duration="1000"
        >
            <div className={cx('category__slide')}>
                <div className={cx('overlay')}></div>
                <Image src={images.dest1} alt="dest-img-1" />
                <div className={cx('tag__list')}>
                    {discount ? <div className={cx('tags', 'discount')}>20% off</div> : <></>}
                    {isNew ? <div className={cx('tags', 'new')}>new</div> : <></>}
                </div>
                <div className={cx('content')}>
                    <div className={cx('rating')}>
                        <FontAwesomeIcon icon={faStar} />
                        <FontAwesomeIcon icon={faStar} />
                        <FontAwesomeIcon icon={faStar} />
                        <FontAwesomeIcon icon={faStar} />
                        <FontAwesomeIcon icon={faStar} className={cx('inactive')} />
                        <span>(2 Reviews)</span>
                    </div>
                    <h3 className={cx('title')}>{title}</h3>
                    <p className={cx('description')}>{description}</p>
                    <div className={cx('time')}>
                        <FontAwesomeIcon icon={faClock} className={cx('clock')} />
                        <span className={cx('days')}>
                            {moment(postDay).utc().format('DD-MM-YYYY')}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CategoryCard;
