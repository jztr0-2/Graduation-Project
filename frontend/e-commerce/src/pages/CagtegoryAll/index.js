import { useState, useEffect } from 'react';
import { useLocation, useNavigate, Link, useParams } from 'react-router-dom';
import { Pagination } from 'antd';
import queryString from 'query-string';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import classNames from 'classnames/bind';
import styles from './CategoryAll.module.scss';
import { CategoryApi } from '~/api/EcommerceApi';
import SessionComp from '~/components/SessionComp';
import DataEmpty from '~/components/DataEmpty';
import images from '~/assets/images';
import Image from '~/components/Image';
const cx = classNames.bind(styles);
function CategoryAll() {
    const pathName = window.location.pathname;
    const location = useLocation();
    const navigate = useNavigate();
    const initialParams = queryString.parse(location.search);
    const initialPageNumber = Number(initialParams.page) || 1;

    const [products, setProducts] = useState([]);
    const [category, setCategory] = useState({});
    const [categories, setCategories] = useState([]);
    const [currentPage, setCurrentPage] = useState(initialPageNumber);
    const [totalCategories, setTotalCategories] = useState();

    /* Handle loading */
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        // CategoryApi.getAllIncludeProducts({
        //     params: {
        //         ...initialParams,
        //     },
        // })
        //     .then(
        //         (res) => {
        //             setProducts(res.data.products);
        //             setCategory(res.data.category);
        //             setTotalCategories(res.data.totalItems);
        //             setLoading(false);
        //             setError(false);
        //             console.log('Res check', res);
        //             console.log('Data check', res.data.products);
        //         },
        //         (error) => {
        //             setError(true);
        //             toast.error(error.response.data.message);
        //         },
        //     )
        //     .catch((e) => {});
        CategoryApi.getAllPage({
            params: {
                ...initialParams,
            },
        })
            .then(
                (res) => {
                    setCategories(res.data.categories);
                    setTotalCategories(res.data.totalItems);
                    setLoading(false);
                    console.log('Page Category:', res);
                },
                (error) => {
                    setError(true);
                    toast.error(error.response.data.message);
                },
            )
            .catch((e) => {});
    }, [currentPage]);
    const handleChangePage = (page) => {
        navigate(`${pathName}?page=${page}`);
        setLoading(true);
        setCurrentPage(page);
    };
    return (
        <div style={{ paddingTop: '100px' }}>
            {!error ? (
                <>
                    {!loading ? (
                        // <SessionComp
                        //     title={category.name}
                        //     subtitle="category"
                        //     items={products}
                        //     type="product"
                        //     sizePercentCard={29}
                        //     linkView={`/category/${category.id}?page=1&limit=6`}
                        // />

                        <div
                            className={cx(
                                'cards-temp',
                                'flex',
                                'items-center',
                                'flex-wrap',
                                'justify-start',
                                'pl-11',
                            )}
                        >
                            {categories.map((category) => {
                                return (
                                    <Link to={`/category/${category.id}?page=1&limit=6`}>
                                        <div
                                            className={cx(
                                                'card-item',
                                                'bg-gray-100',
                                                'rounded-3xl',
                                                'p-3',
                                                'flex',
                                                'justify-between',
                                                'items-center',
                                                'flex-col',
                                                'm-6',
                                            )}
                                        >
                                            <Image
                                                src={images.background}
                                                className={cx('img-item')}
                                            />
                                            <p>{category.name}</p>
                                        </div>
                                    </Link>
                                );
                            })}
                        </div>
                    ) : (
                        <></>
                    )}
                    {/* <div className={cx('card')}>
                        <div className={cx('imgBx')}>
                            <Image src={images.dest1} />
                        </div>
                        <div className={cx('content')}>
                            <div className={cx('details')}>
                                <h2>Category</h2>
                                <div className={cx('data')}>
                                    <h3>
                                        <br />
                                        <span></span>
                                    </h3>
                                </div>
                                <div className={cx('actionBtn')}>
                                    <button>More</button>
                                </div>
                            </div>
                        </div>
                    </div> */}
                    <Pagination
                        current={currentPage}
                        pageSize={10}
                        total={totalCategories}
                        onChange={(page) => handleChangePage(page)}
                    />
                </>
            ) : (
                <DataEmpty />
            )}
            <ToastContainer />
        </div>
    );
}

export default CategoryAll;
