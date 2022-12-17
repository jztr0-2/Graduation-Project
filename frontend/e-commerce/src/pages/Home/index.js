// Hooks
import { useEffect } from 'react';
// styles
import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faTruckFast,
    faHeadphonesSimple,
    faWallet,
    faThumbsUp,
    faUsers,
    faArrowRotateRight,
} from '@fortawesome/free-solid-svg-icons';
import styles from './Home.module.scss';
// component static
// disable - import CategoryProdCard from '~/components/CategoryProdCard';
import SessionComp from '~/components/SessionComp';
import CategoryCard from '~/components/CategoryCard';
import ProdCard from '~/components/ProdCard';

const cx = classNames.bind(styles);

function Home() {
    useEffect(() => {
        const collageImages = [...document.getElementsByName('collage-img')];
        collageImages.map((item, i) => {
            item.addEventListener('mouseover', () => {
                collageImages.map((image, index) => {
                    if (index !== i) {
                        image.style.filter = `blur(2px)`;
                        item.style.zIndex = 2;
                    }
                });
            });

            item.addEventListener('mouseleave', () => {
                collageImages.map((image, index) => {
                    image.style = null;
                });
            });
        });
    }, []);
    /* Category cards*/
    const categoryCards = [
        <CategoryCard
            title="Fridge"
            description="Freezer units are used in households as well as in industry and commerce"
            postDay="7"
            key={0}
        />,
        <CategoryCard
            title="Fridge"
            description="Freezer units are used in households as well as in industry and commerce"
            postDay="7"
            key={1}
        />,
        <CategoryCard
            title="Fridge"
            description="Freezer units are used in households as well as in industry and commerce"
            postDay="7"
            key={2}
        />,
        <CategoryCard
            title="Fridge"
            description="Freezer units are used in households as well as in industry and commerce"
            postDay="7"
            key={3}
        />,
    ];
    /* Product cards */
    const productCards = [
        <ProdCard key={1} />,
        <ProdCard key={2} />,
        <ProdCard key={3} />,
        <ProdCard key={4} />,
    ];
    return (
        <div className={cx('wrapper')}>
            <div className={cx('header-content')}></div>
            {/* <!--==================== CATEGORY CARD ====================--> */}
            {/* <SessionComp
                title="Most popular category"
                subtitle="category"
                cards={categoryCards}
                sizePercentCard={45}
            /> */}
            {/* <!--==================== CATEGORY CARD END ====================--> */}

            {/* <!--==================== PRODUCT CARD ====================--> */}
            {/* <SessionComp
                title="Most popular product"
                subtitle="product"
                cards={productCards}
                sizePercentCard={30}
            /> */}
            {/* <!--==================== PRODUCT CARD END ====================--> */}
            {/* <!-- services section starts  --> */}

            <section className={cx('services')} id="services">
                <div className={cx('heading')}>
                    <h1>Countless Expericences</h1>
                </div>

                <div className={cx('box-container')}>
                    <div className={cx('box')} data-aos="zoom-in-up" data-aos-delay="300">
                        <FontAwesomeIcon icon={faUsers} />
                        <h3>adventures</h3>
                        <p>
                            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Numquam,
                            cumque.
                        </p>
                    </div>

                    <div className={cx('box')} data-aos="zoom-in-up" data-aos-delay="450">
                        <FontAwesomeIcon icon={faTruckFast} />
                        <h3>food & drinks</h3>
                        <p>
                            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Numquam,
                            cumque.
                        </p>
                    </div>

                    <div className={cx('box')} data-aos="zoom-in-up" data-aos-delay="600">
                        <FontAwesomeIcon icon={faThumbsUp} />
                        <h3>affordable hotels</h3>
                        <p>
                            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Numquam,
                            cumque.
                        </p>
                    </div>

                    <div className={cx('box')} data-aos="zoom-in-up" data-aos-delay="750">
                        <FontAwesomeIcon icon={faWallet} />
                        <h3>affordable price</h3>
                        <p>
                            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Numquam,
                            cumque.
                        </p>
                    </div>

                    <div className={cx('box')} data-aos="zoom-in-up" data-aos-delay="900">
                        <FontAwesomeIcon icon={faHeadphonesSimple} />
                        <h3>24/7 support</h3>
                        <p>
                            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Numquam,
                            cumque.
                        </p>
                    </div>

                    <div className={cx('box')} data-aos="zoom-in-up" data-aos-delay="150">
                        <FontAwesomeIcon icon={faArrowRotateRight} />
                        <h3>worldwide</h3>
                        <p>
                            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Numquam,
                            cumque.
                        </p>
                    </div>
                </div>
            </section>

            {/* <!-- services section ends --> */}

            {/* image collage section */}
            <section className={cx('image-mid-section')}>
                <div className={cx('image-collage')}>
                    <div className={cx('image-collection')}>
                        <img
                            src="https://images.unsplash.com/photo-1622473541207-c59995fa6b4c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YWlyJTIwY29uZGl0aW9uZXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"
                            className={cx('collage-img')}
                            alt=""
                            name="collage-img"
                        />
                        <img
                            src="https://images.unsplash.com/photo-1622473541207-c59995fa6b4c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YWlyJTIwY29uZGl0aW9uZXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"
                            className={cx('collage-img')}
                            alt=""
                            name="collage-img"
                        />
                        <img
                            src="https://images.unsplash.com/photo-1622473541207-c59995fa6b4c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YWlyJTIwY29uZGl0aW9uZXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"
                            className={cx('collage-img')}
                            alt=""
                            name="collage-img"
                        />
                    </div>
                </div>
            </section>
        </div>
    );
}

export default Home;
