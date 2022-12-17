import { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Pagination } from 'antd';
import queryString from 'query-string';

import { CategoryApi } from '~/api/EcommerceApi';
import classNames from 'classnames/bind';
import styles from './CategoryDetails.module.scss';

import ProdCard from '~/components/ProdCard';
import DataEmpty from '~/components/DataEmpty';
const cx = classNames.bind(styles);

function Category() {
    const location = useLocation();
    const navigate = useNavigate();
    const pathName = window.location.pathname;
    const initialParams = queryString.parse(location.search);
    const initialPageNumber = Number(initialParams.page) || 1;
    const { categoryId: initialCategoryId = 1 } = useParams();

    const [totalProducts, setTotalProducts] = useState();
    const [products, setProducts] = useState([]);
    const [category, setCategory] = useState({});
    const [categoryId, setCategoryId] = useState(initialCategoryId);
    const [currentPage, setCurrentPage] = useState(initialPageNumber);
    const [productsPerPage, setProductsPerPage] = useState(6);
    /* Handle call api error */
    const [error, setError] = useState(false);
    /* Handle loading */
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        CategoryApi.getByCategoryId(
            {
                params: {
                    ...initialParams,
                },
            },
            initialCategoryId,
        ).then(
            (res) => {
                if (res.data) {
                    setTotalProducts(res.data.totalItems);
                    setProducts(res.data.products);
                    setCategory(res.data.products[0].category);
                    setCategoryId(initialCategoryId);
                    setLoading(false);
                    setError(false);
                    console.log(res.data.products[0].category);
                }
            },
            (err) => {
                toast.error(err.response.data.message);
                setError(true);
            },
        );
    }, [currentPage, pathName, categoryId]);

    const handleChangePage = (page) => {
        navigate(`${pathName}?page=${page}&limit=${productsPerPage}`);
        setLoading(true);
        setCurrentPage(page);
    };
    const handleShowToast = () => {
        toast('Get product successfully');
    };
    return (
        <div className={cx('wrapper')}>
            {!error ? (
                <>
                    <div className={cx('prods')}>
                        {loading &&
                            products?.map((productVariant) => {
                                return (
                                    <ProdCard.Loading key={productVariant.id}></ProdCard.Loading>
                                );
                            })}
                        {!loading &&
                            products?.map((product) => {
                                return (
                                    <ProdCard
                                        className={cx('size-prod-card')}
                                        key={product.id}
                                        name={product.name}
                                        description={product.description}
                                        price={product.productVariants[0]?.unitPrice}
                                        onClick={handleShowToast}
                                    />
                                );
                            })}
                    </div>
                    <Pagination
                        current={currentPage}
                        pageSize={productsPerPage}
                        total={totalProducts}
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

export default Category;
