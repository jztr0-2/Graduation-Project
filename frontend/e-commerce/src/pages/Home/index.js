// Hooks
import { useState, useEffect } from 'react';
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
    faTag,
    faAngleDown,
} from '@fortawesome/free-solid-svg-icons';
import { faInstagram, faFacebook, faGoogle } from '@fortawesome/free-brands-svg-icons';
import { ToastContainer, toast } from 'react-toastify';
import styles from './Home.module.scss';
import SessionComp from '~/components/SessionComp';
import images from '~/assets/images';
import { CategoryApi, ProductApi } from '~/api/EcommerceApi';

const cx = classNames.bind(styles);

function Home() {
    const [categories, setCategories] = useState([]);
    const [saleProducts, setSaleProducts] = useState([]);
    const [saleCategory, setSaleCategory] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    // useEffect(() => {
    //     const collageImages = [...document.getElementsByName('collage-img')];
    //     collageImages.map((item, i) => {
    //         item.addEventListener('mouseover', () => {
    //             collageImages.map((image, index) => {
    //                 if (index !== i) {
    //                     image.style.filter = `blur(2px)`;
    //                     item.style.zIndex = 2;
    //                 }
    //             });
    //         });

    //         item.addEventListener('mouseleave', () => {
    //             collageImages.map((image, index) => {
    //                 image.style = null;
    //             });
    //         });
    //     });
    // }, []);

    useEffect(() => {
        /* Get sale category */
        CategoryApi.getSaleCategory()
            .then(
                (res) => {
                    if (res.data) {
                        setSaleCategory(res.data);
                        setLoading(false);
                        console.log('Category Selling: ', res.data);
                    }
                },
                (error) => {
                    console.log(error);
                    setError(true);
                },
            )
            .catch((e) => {
                console.log(e);
                setError(true);
                toast.error(e);
            });
        /* Get sale products (TOP 10) */
        ProductApi.getSaleProducts()
            .then(
                (res) => {
                    if (res.data) {
                        setSaleProducts(res.data);
                        setLoading(false);
                        console.log('Products Top 10 Selling: ', res.data);
                    }
                },
                (error) => {
                    console.log(error);
                    toast.error(error.message);
                },
            )
            .catch((e) => {
                console.log(e);
                setError(true);
                toast.error(e);
            });
        // Get All Categories
        CategoryApi.getAll()
            .then(
                (res) => {
                    if (res.data) {
                        setCategories(res.data);
                    }
                },
                (error) => {
                    toast.error(error.message);
                },
            )
            .catch((e) => {
                toast.error(e);
            });
    }, []);

    return (
        <div className={cx('wrapper')}>
            <div className={cx('header-content')}>
                <section className={cx('hero')}>
                    <div
                        className={cx('hero-text')}
                        data-aos="fade-down"
                        data-aos-easing="linear"
                        data-aos-duration="1000"
                    >
                        <h5>#1 Selling</h5>
                        <h4>Electronic Device</h4>
                        <h1>E-Commerce</h1>
                        <p>
                            Join millions of players worlwide as you build your village, <br />{' '}
                            raise a clan,and compete in epic Clan Wars!!
                        </p>
                        <a href="#">More</a>
                        <a href="#" className={cx('ctaa')}>
                            <FontAwesomeIcon icon={faTag} />
                            Discover
                        </a>
                    </div>
                    {/* 
                    <div className={cx('hero-img')}>
                        <img src={images.background} />
                    </div> */}
                </section>

                <div
                    className={cx('icons')}
                    data-aos="fade-right"
                    data-aos-easing="linear"
                    data-aos-duration="1500"
                >
                    <a href="#">
                        <FontAwesomeIcon icon={faInstagram} />
                    </a>
                    <a href="#">
                        <FontAwesomeIcon icon={faFacebook} />
                    </a>
                    <a href="#">
                        <FontAwesomeIcon icon={faGoogle} />
                    </a>
                </div>

                <div className={cx('scroll-down')}>
                    <a href="#">
                        <FontAwesomeIcon icon={faAngleDown} />
                    </a>
                </div>
            </div>
            {/* <!--==================== CATEGORIES ====================--> */}
            {!error ? (
                <>
                    {!loading && categories.length > 0 ? (
                        <SessionComp
                            title={'Categories'}
                            items={categories}
                            type={'category'}
                            linkView={`/categories/views?page=1`}
                            // imgOnly
                            // showArrow={false}
                            // sizePercentCard={29}
                        />
                    ) : (
                        <></>
                    )}
                </>
            ) : (
                <></>
            )}

            {/* <!--==================== CATEGORY BEST SELLING ====================--> */}
            {!error ? (
                <>
                    {!loading && saleProducts.length > 0 ? (
                        <SessionComp
                            title={saleCategory[0].category.name}
                            subtitle="Most popular category"
                            items={saleCategory}
                            type={'product'}
                            imgOnlyProduct
                            showArrow
                            sizePercentCard={29}
                            linkView={`/category/${saleCategory[0].category.id}?page=1&limit=6`}
                        />
                    ) : (
                        <></>
                    )}
                </>
            ) : (
                <></>
            )}

            {/* <!--==================== PRODUCTS TOP 10 SELLING CARD ====================--> */}
            {!error ? (
                <>
                    {!loading && saleProducts.length > 0 ? (
                        <SessionComp
                            title={'PRODUCTS'}
                            subtitle="BEST SELLING PRODUCTS"
                            items={saleProducts}
                            type={'product'}
                            imgOnlyProduct
                            showArrow
                            sizePercentCard={29}
                            // linkView={`/category/${saleCategory[0].category.id}?page=1&limit=6`}
                        />
                    ) : (
                        <></>
                    )}
                </>
            ) : (
                <></>
            )}
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
            {/* <section className={cx('image-mid-section')}>
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
            </section> */}
            <ToastContainer />
        </div>
    );
}

export default Home;
