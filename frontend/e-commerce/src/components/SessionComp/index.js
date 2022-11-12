import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { Carousel } from 'react-responsive-carousel';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft, faArrowRight } from '@fortawesome/free-solid-svg-icons';
import classNames from 'classnames/bind';
import styles from './SessionComp.module.scss';
import CategoryCard from '../CategoryCard';

const cx = classNames.bind(styles);

function SessionComp({ title, subtitle, sizePercentCard = 45, cards }) {
    return (
        <section id="destinations" className={cx('section__padding')}>
            <div className={cx('wrapper-base')}>
                {/* <!-- SECTION TOP --> */}
                <div className={cx('section__top')}>
                    <div className={cx('section__heading')}>
                        <p className={cx('section__subtitle')}>{subtitle}</p>
                        <h2 className={cx('section__title', 'light')}>{title}</h2>
                    </div>

                    {/* <!-- Buttons --> */}
                    <div className={cx('button__wrapper')}>
                        <div className="carousel__buttons">
                            <div
                                className="carousel__prev__btn carousel__btn light"
                                data-target="#destination-carousel"
                            >
                                <FontAwesomeIcon icon={faArrowLeft} />
                            </div>
                            <div
                                className="carousel__next__btn carousel__btn light"
                                data-target="#destination-carousel"
                            >
                                <FontAwesomeIcon icon={faArrowRight} />
                            </div>
                        </div>
                        <button className={cx('btn', 'btn__2', 'light')}>View all</button>
                    </div>
                </div>

                <Carousel
                    showThumbs={false}
                    showIndicators={false}
                    showStatus={false}
                    centerSlidePercentage={sizePercentCard}
                    centerMode={true}
                    useKeyboardArrows={true}
                    swipeable={true}
                    emulateTouch={true}
                    // autoPlay={true}
                    // infiniteLoop={true}
                    // interval={6000}
                >
                    {cards.map((card) => card)}
                </Carousel>
            </div>
        </section>
    );
}

export default SessionComp;
