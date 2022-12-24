import moment from 'moment/moment';
import { useEffect, useState, useRef } from 'react';
import { useParams, Link } from 'react-router-dom';
import { Swiper, SwiperSlide } from 'swiper/react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart, faShare, faStar } from '@fortawesome/free-solid-svg-icons';
import { ToastContainer, toast } from 'react-toastify';
import { useStore, actions } from '~/store';
import 'swiper/css';

import classNames from 'classnames/bind';
import styles from './ProductDetails.module.scss';
import Image from '~/components/Image';
import images from '~/assets/images';
import { ProductApi } from '~/api/EcommerceApi';
import DataEmpty from '~/components/DataEmpty';

const cx = classNames.bind(styles);
function ProductDetails() {
    const swipperRef = useRef();
    const pathName = window.location.pathname;
    const [category, setCategory] = useState({});
    const [product, setProduct] = useState({});
    const [productSelect, setProductSelect] = useState({});
    const [productVariant, setProductVariant] = useState({});
    const [productVariants, setProductVariants] = useState([]);
    const [indexSelectVariant, setIndexSelectVariant] = useState(0);
    const [quantityBuy, setQuantityBuy] = useState(1);
    const [keyDescription, setKeyDescription] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    const { productId } = useParams();

    /* Handle show details info product */
    useEffect(() => {
        ProductApi.getById(productId)
            .then(
                (res) => {
                    if (res.data) {
                        let indexSelect = Math.min(
                            indexSelectVariant,
                            res.data.productVariants.length - 1,
                        );
                        let variants = res.data.productVariants;
                        setKeyDescription(variants[0].description.title.trim());
                        setCategory(res.data.category);
                        setProduct(res.data);
                        setProductVariant(variants[indexSelect]);
                        setProductVariants(variants);
                        setLoading(false);
                    }
                    console.log(res);
                },
                (error) => {
                    toast.error(error.message);
                    setError(true);
                },
            )
            .catch((e) => {
                console.log(e);
                setError(true);
                toast.error(e);
            });
    }, [pathName, productId, indexSelectVariant]);
    /* Handle add cart item */
    const [state, dispatch] = useStore();
    const handleAddCartItem = () => {
        dispatch(
            actions.addCartItem({
                id: product?.id,
                productVariantId: productVariant?.id,
                name: product?.name,
                nameVariant: productVariant?.displayName,
                quantity: Math.min(quantityBuy, productVariant.quantity),
                price: productVariant?.unitPrice,
                createdAt: productVariant?.createdAt,
                categoryName: category?.name,
            }),
        );
        toast.success('Product has been added to cart successfully');
    };
    // Handle select variant
    // useEffect(() => {
    //     let indexSelect = Math.min(indexSelectVariant, productVariants.length - 1);
    //     let productVariant = productVariants[indexSelect];
    //     setProductVariant(productVariant);
    //     console.log('Product variant', productVariant);
    //     setLoading(false);
    // }, [indexSelectVariant]);

    const handleSelectVariant = (index) => {
        setIndexSelectVariant(index);
        setLoading(true);
    };
    // Handle show/hide
    useEffect(() => {
        const submenu = document.querySelectorAll('.js-has-child .icon-small');
        submenu.forEach((menu) => {
            menu.addEventListener('click', toggle);
        });

        function toggle(e) {
            submenu.forEach((item) =>
                item !== this ? item.closest('.js-has-child').classList.remove('expand') : null,
            );
            if (this.closest('.js-has-child').classList !== 'expand') {
                this.closest('.js-has-child').classList.toggle('expand');
            }
        }
    }, [indexSelectVariant]);
    return (
        <div>
            {!error ? (
                <>
                    {!loading ? (
                        <div className={cx('single-product')}>
                            <div className={cx('custom-container')}>
                                <div className={'custom-wrapper'}>
                                    <div className={cx('breadcrumb')}>
                                        <ul className={cx('custom-flexitem', 'pl-[12px]')}>
                                            <li className={cx('breadcrumb-item')}>
                                                <Link to={'/'}>Home</Link>
                                            </li>
                                            <li className={cx('breadcrumb-item')}>
                                                <Link
                                                    to={`/category/${category.id}?page=1&limit=6`}
                                                >
                                                    {category?.name}
                                                </Link>
                                            </li>
                                            <li className={cx('breadcrumb-item')}>
                                                {product?.name}
                                            </li>
                                        </ul>
                                    </div>
                                    {/* custom-column */}
                                    <div className={cx('')}>
                                        <div className={cx('products', 'one')}>
                                            <div className={cx('custom-flexwrap')}>
                                                <div className={cx('custom-row', 'position-row')}>
                                                    <div className={cx('custom-item')}>
                                                        <div className={cx('price')}>
                                                            <span className={cx('discount')}>
                                                                32%
                                                                <br />
                                                                OFF
                                                            </span>
                                                        </div>
                                                        <Swiper
                                                            loop={true}
                                                            autoHeight={true}
                                                            // navigation={{
                                                            //     nextEl: prev,
                                                            //     prevEl: next,
                                                            // }}
                                                            // thumbs={{
                                                            //     swiper: swipperRef.current,
                                                            // }}
                                                            allowSlideNext={true}

                                                            // onSlideChange={() => console.log('slide change')}
                                                            // onSwiper={(swiper) => console.log(swiper)}
                                                        >
                                                            <SwiperSlide>
                                                                <div className={cx('big-image')}>
                                                                    <div
                                                                        className={cx(
                                                                            'big-image-wrapper',
                                                                            'swiper-wrapper',
                                                                        )}
                                                                    >
                                                                        <div
                                                                            className={cx(
                                                                                'image-show',
                                                                                'swiper-slide',
                                                                            )}
                                                                        >
                                                                            <a href="/">
                                                                                <Image
                                                                                    src={
                                                                                        images.dest1
                                                                                    }
                                                                                />
                                                                            </a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </SwiperSlide>
                                                            <SwiperSlide>
                                                                <div className={cx('big-image')}>
                                                                    <div
                                                                        className={cx(
                                                                            'big-image-wrapper',
                                                                            'swiper-wrapper',
                                                                        )}
                                                                    >
                                                                        <div
                                                                            className={cx(
                                                                                'image-show',
                                                                                'swiper-slide',
                                                                            )}
                                                                        >
                                                                            <a href="/">
                                                                                <Image
                                                                                    src={
                                                                                        images.thumbHeader2
                                                                                    }
                                                                                />
                                                                            </a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </SwiperSlide>
                                                            <SwiperSlide>
                                                                <div className={cx('big-image')}>
                                                                    <div
                                                                        className={cx(
                                                                            'big-image-wrapper',
                                                                            'swiper-wrapper',
                                                                        )}
                                                                    >
                                                                        <div
                                                                            className={cx(
                                                                                'image-show',
                                                                                'swiper-slide',
                                                                            )}
                                                                        >
                                                                            <a href="/">
                                                                                <Image
                                                                                    src={
                                                                                        images.thumbHeader3
                                                                                    }
                                                                                />
                                                                            </a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </SwiperSlide>
                                                            {/* <div
                                                    className="swiper-button-prev"
                                                    ref={swipperPrev}
                                                ></div>
                                                <div
                                                    className="swiper-button-next"
                                                    ref={swipperNext}
                                                ></div> */}
                                                        </Swiper>

                                                        {/* slides for products thumb*/}
                                                        {/* <Swiper
                                                            loop={true}
                                                            spaceBetween={10}
                                                            slidesPerView={3}
                                                            freeMode={true}
                                                            watchSlidesProgress={true}
                                                            breakpoints={{
                                                                481: {
                                                                    spaceBetween: 32,
                                                                },
                                                            }}
                                                            ref={swipperRef}
                                                        >
                                                            <SwiperSlide>
                                                                <div className={cx('small-image')}>
                                                                    <ul
                                                                        className={cx(
                                                                            'small-image-wrapper',
                                                                            'custom-flexitem',
                                                                            'swipper-wrapper',
                                                                        )}
                                                                    >
                                                                        <li
                                                                            className={cx(
                                                                                'thumbnail-show',
                                                                                'swiper-slide',
                                                                            )}
                                                                        >
                                                                            <Image
                                                                                src={images.dest1}
                                                                            />
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </SwiperSlide>
                                                            <SwiperSlide>
                                                                <div className={cx('small-image')}>
                                                                    <ul
                                                                        className={cx(
                                                                            'small-image-wrapper',
                                                                            'custom-flexitem',
                                                                            'swipper-wrapper',
                                                                        )}
                                                                    >
                                                                        <li
                                                                            className={cx(
                                                                                'thumbnail-show',
                                                                                'swiper-slide',
                                                                            )}
                                                                        >
                                                                            <Image
                                                                                src={
                                                                                    images.thumbHeader2
                                                                                }
                                                                            />
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </SwiperSlide>
                                                        </Swiper> */}
                                                    </div>
                                                </div>
                                                <div
                                                    className={cx('custom-row')}
                                                    style={{
                                                        position: 'relative',
                                                    }}
                                                >
                                                    <div className={cx('custom-item')}>
                                                        <h1 className={cx('mini-text')}>
                                                            {productVariant?.displayName}
                                                        </h1>
                                                        <div className={cx('content')}>
                                                            <div
                                                                className={cx(
                                                                    'rating',
                                                                    'flex',
                                                                    'my-8',
                                                                )}
                                                            >
                                                                <div
                                                                    className={cx('starts', 'mr-4')}
                                                                >
                                                                    <FontAwesomeIcon
                                                                        icon={faStar}
                                                                        className={cx('icon-item')}
                                                                    />
                                                                    <FontAwesomeIcon
                                                                        icon={faStar}
                                                                        className={cx('icon-item')}
                                                                    />
                                                                    <FontAwesomeIcon
                                                                        icon={faStar}
                                                                        className={cx('icon-item')}
                                                                    />
                                                                    <FontAwesomeIcon
                                                                        icon={faStar}
                                                                        className={cx('icon-item')}
                                                                    />
                                                                    <FontAwesomeIcon
                                                                        icon={faStar}
                                                                        className={cx('icon-item')}
                                                                    />
                                                                </div>
                                                                <a
                                                                    href="/"
                                                                    className={cx(
                                                                        'mini-text',
                                                                        'mr-4',
                                                                    )}
                                                                >
                                                                    2.251 reviews
                                                                </a>
                                                                <a
                                                                    href="/"
                                                                    className={cx(
                                                                        'add-review',
                                                                        'mini-text',
                                                                    )}
                                                                >
                                                                    Add Your Review
                                                                </a>
                                                            </div>
                                                            <div
                                                                className={cx('stock-sku', 'my-10')}
                                                            >
                                                                <span
                                                                    className={cx(
                                                                        'available',
                                                                        'mini-text',
                                                                    )}
                                                                >
                                                                    {category.name}
                                                                </span>
                                                                <span
                                                                    className={cx(
                                                                        'sku',
                                                                        'mini-text',
                                                                    )}
                                                                >
                                                                    PRD-{product.id}
                                                                </span>
                                                            </div>
                                                            <div className={cx('price')}>
                                                                <h3 className={cx('mini-text')}>
                                                                    PRICE
                                                                </h3>
                                                                <span className={cx('current')}>
                                                                    {new Intl.NumberFormat(
                                                                        'vi-VN',
                                                                        {
                                                                            style: 'currency',
                                                                            currency: 'VND',
                                                                        },
                                                                    ).format(
                                                                        productVariant?.unitPrice,
                                                                    )}
                                                                </span>
                                                                {/* <span className={cx('normal')}>
                                                                    $119.90
                                                                </span> */}
                                                            </div>
                                                            <div className={cx('color')}>
                                                                <p
                                                                    className={cx(
                                                                        'mini-text',
                                                                        'mb-2.5',
                                                                    )}
                                                                >
                                                                    {keyDescription}
                                                                </p>
                                                                <div
                                                                    className={cx(
                                                                        'descriptions',
                                                                        'flex',
                                                                    )}

                                                                    style={{display:"flex", flexFlow:"wrap", gap:"10px"}}
                                                                >
                                                                    {productVariants.map(
                                                                        (productVariant, index) => (
                                                                            <p
                                                                                onClick={() =>
                                                                                    handleSelectVariant(
                                                                                        index,
                                                                                    )
                                                                                }
                                                                                key={
                                                                                    productVariant.id
                                                                                }
                                                                                className={cx(
                                                                                    'description-item',
                                                                                    index ===
                                                                                        indexSelectVariant
                                                                                        ? 'active'
                                                                                        : null,
                                                                                )}
                                                                            >
                                                                                {
                                                                                    productVariant
                                                                                        .description[
                                                                                        keyDescription
                                                                                    ]
                                                                                }
                                                                            </p>
                                                                        ),
                                                                    )}
                                                                </div>
                                                                {/* <div className={cx('variant')}>
                                                                    <form action="">
                                                                        <p>
                                                                            <input
                                                                                type="radio"
                                                                                name="color"
                                                                                id="cogrey"
                                                                                onChange={() => {}}
                                                                            />
                                                                            <label
                                                                                htmlFor="cogrey"
                                                                                className={cx(
                                                                                    'circle',
                                                                                )}
                                                                            ></label>
                                                                        </p>
                                                                        <p>
                                                                            <input
                                                                                type="radio"
                                                                                name="color"
                                                                                id="coblue"
                                                                                onChange={() => {}}
                                                                            />
                                                                            <label
                                                                                htmlFor="coblue"
                                                                                className={cx(
                                                                                    'circle',
                                                                                )}
                                                                            ></label>
                                                                        </p>
                                                                        <p>
                                                                            <input
                                                                                type="radio"
                                                                                name="color"
                                                                                id="cogreen"
                                                                                onChange={() => {}}
                                                                            />
                                                                            <label
                                                                                htmlFor="cogreen"
                                                                                className={cx(
                                                                                    'circle',
                                                                                )}
                                                                            ></label>
                                                                        </p>
                                                                    </form>
                                                                </div> */}
                                                            </div>
                                                            {/* <div className={cx('sizes')}>
                                                                <p className={cx('mini-text')}>
                                                                    Size
                                                                </p>
                                                                <div className={cx('variant')}>
                                                                    <form action="">
                                                                        <p>
                                                                            <input
                                                                                type="radio"
                                                                                name="size"
                                                                                id="size-40"
                                                                            />
                                                                            <label
                                                                                for="size-40"
                                                                                className={cx(
                                                                                    'circle',
                                                                                )}
                                                                            >
                                                                                <span>40</span>
                                                                            </label>
                                                                        </p>
                                                                        <p>
                                                                            <input
                                                                                type="radio"
                                                                                name="size"
                                                                                id="size-41"
                                                                            />
                                                                            <label
                                                                                for="size-41"
                                                                                className={cx(
                                                                                    'circle',
                                                                                )}
                                                                            >
                                                                                <span>41</span>
                                                                            </label>
                                                                        </p>
                                                                        <p>
                                                                            <input
                                                                                type="radio"
                                                                                name="size"
                                                                                id="size-42"
                                                                            />
                                                                            <label
                                                                                for="size-42"
                                                                                className={cx(
                                                                                    'circle',
                                                                                )}
                                                                            >
                                                                                <span>42</span>
                                                                            </label>
                                                                        </p>
                                                                        <p>
                                                                            <input
                                                                                type="radio"
                                                                                name="size"
                                                                                id="size-43"
                                                                            />
                                                                            <label
                                                                                for="size-43"
                                                                                className={cx(
                                                                                    'circle',
                                                                                )}
                                                                            >
                                                                                <span>43</span>
                                                                            </label>
                                                                        </p>
                                                                    </form>
                                                                </div>
                                                            </div> */}
                                                            <div className={cx('actions')}>
                                                                <div
                                                                    className={cx(
                                                                        'qty-control',
                                                                        'custom-flexitem',
                                                                    )}
                                                                >
                                                                    <button
                                                                        className={cx(
                                                                            'minus',
                                                                            'circle',
                                                                        )}
                                                                        style={{
                                                                            color: 'black',
                                                                        }}
                                                                        onClick={() =>
                                                                            setQuantityBuy(
                                                                                (oldQuantity) => {
                                                                                    return oldQuantity -
                                                                                        1 ===
                                                                                        0
                                                                                        ? 1
                                                                                        : oldQuantity -
                                                                                              1;
                                                                                },
                                                                            )
                                                                        }
                                                                    >
                                                                        -
                                                                    </button>
                                                                    <input
                                                                        type="text"
                                                                        value={quantityBuy}
                                                                        style={{
                                                                            color: 'black',
                                                                        }}
                                                                        min={1}
                                                                        max={
                                                                            productVariant.quantity
                                                                        }
                                                                        onChange={(e) => {
                                                                            setQuantityBuy(
                                                                                Number(
                                                                                    e.target.value,
                                                                                ),
                                                                            );
                                                                        }}
                                                                    />
                                                                    <button
                                                                        className={cx(
                                                                            'plus',
                                                                            'circle',
                                                                        )}
                                                                        style={{
                                                                            color: 'black',
                                                                        }}
                                                                        onClick={() =>
                                                                            setQuantityBuy(
                                                                                (oldQuantity) => {
                                                                                    console.log(
                                                                                        oldQuantity,
                                                                                    );
                                                                                    return (
                                                                                        oldQuantity +
                                                                                        1
                                                                                    );
                                                                                },
                                                                            )
                                                                        }
                                                                    >
                                                                        +
                                                                    </button>
                                                                </div>
                                                                <div className={cx('button-cart')}>
                                                                    <button
                                                                        className="secondary-button"
                                                                        onClick={handleAddCartItem}
                                                                    >
                                                                        Add to cart
                                                                    </button>
                                                                </div>
                                                                <div className={cx('wish-share')}>
                                                                    <ul
                                                                        className={cx(
                                                                            'custom-flexitem',
                                                                            'second-links',
                                                                        )}
                                                                    >
                                                                        <li>
                                                                            <a href="/">
                                                                                <span
                                                                                    className={cx(
                                                                                        'icon-large',
                                                                                    )}
                                                                                >
                                                                                    <FontAwesomeIcon
                                                                                        icon={
                                                                                            faHeart
                                                                                        }
                                                                                    />
                                                                                </span>
                                                                                <span>
                                                                                    Wishlist
                                                                                </span>
                                                                            </a>
                                                                        </li>
                                                                        <li>
                                                                            <a href="/">
                                                                                <span
                                                                                    className={cx(
                                                                                        'icon-large',
                                                                                    )}
                                                                                >
                                                                                    <FontAwesomeIcon
                                                                                        icon={
                                                                                            faShare
                                                                                        }
                                                                                    />
                                                                                </span>
                                                                                <span>Share</span>
                                                                            </a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                            <div
                                                                className={cx(
                                                                    'description',
                                                                    'custom-collapse',
                                                                )}
                                                            >
                                                                <ul>
                                                                    <li
                                                                        className={cx(
                                                                            'has-child',
                                                                            'js-has-child',
                                                                            'expand',
                                                                        )}
                                                                    >
                                                                        <p
                                                                            className={cx(
                                                                                'icon-small',
                                                                            )}
                                                                        >
                                                                            Information
                                                                        </p>
                                                                        <ul
                                                                            className={cx(
                                                                                'content',
                                                                                'js-content',
                                                                            )}
                                                                        >
                                                                            <li>
                                                                                <span>
                                                                                    Category
                                                                                </span>
                                                                                <span>
                                                                                    {category?.name}
                                                                                </span>
                                                                            </li>
                                                                            <li>
                                                                                <span>
                                                                                    Quantity
                                                                                </span>
                                                                                <span>
                                                                                    {
                                                                                        productVariant?.quantity
                                                                                    }
                                                                                </span>
                                                                            </li>
                                                                            <li>
                                                                                <span>
                                                                                    Created Day
                                                                                </span>
                                                                                <span>
                                                                                    {moment(
                                                                                        productVariant?.createdAt,
                                                                                    )
                                                                                        .utc()
                                                                                        .format(
                                                                                            'DD-MM-YYYY',
                                                                                        )}
                                                                                </span>
                                                                            </li>
                                                                            <li>
                                                                                <span>Brands</span>
                                                                                <span>JZTR</span>
                                                                            </li>
                                                                        </ul>
                                                                    </li>
                                                                    {/* <li
                                                                        className={cx(
                                                                            'has-child',
                                                                            'js-has-child',
                                                                            'expand',
                                                                        )}
                                                                    >
                                                                        <p
                                                                            className={cx(
                                                                                'icon-small',
                                                                            )}
                                                                        >
                                                                            Details
                                                                        </p>
                                                                        <div
                                                                            className={cx(
                                                                                'content',
                                                                                'js-content',
                                                                            )}
                                                                        >
                                                                            <p>
                                                                                lorem ipsum dolor
                                                                                sit amet
                                                                                consectetuer
                                                                                adipiscing elit
                                                                            </p>
                                                                            <p>
                                                                                lorem ipsum dolor
                                                                                sit amet
                                                                                consectetuer
                                                                                adipiscing elit
                                                                            </p>
                                                                        </div>
                                                                    </li> */}
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ) : (
                        <></>
                    )}
                </>
            ) : (
                <DataEmpty isPadding={'pt-[100px]'} />
            )}
            <ToastContainer />
        </div>
    );
}

export default ProductDetails;
