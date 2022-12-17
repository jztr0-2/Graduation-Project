import { useState, useEffect } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Pagination } from 'antd';
import queryString from 'query-string';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { CategoryApi } from '~/api/EcommerceApi';
import SessionComp from '~/components/SessionComp';
import DataEmpty from '~/components/DataEmpty';

function CategoryAll() {
    const pathName = window.location.pathname;
    const location = useLocation();
    const navigate = useNavigate();
    const initialParams = queryString.parse(location.search);
    const initialPageNumber = Number(initialParams.page) || 1;

    const [products, setProducts] = useState([]);
    const [category, setCategory] = useState({});
    const [currentPage, setCurrentPage] = useState(initialPageNumber);
    const [totalCategories, setTotalCategories] = useState();

    /* Handle loading */
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        CategoryApi.getAllIncludeProducts({
            params: {
                ...initialParams,
            },
        })
            .then(
                (res) => {
                    setProducts(res.data.products);
                    setCategory(res.data.category);
                    setTotalCategories(res.data.totalItems);
                    setLoading(false);
                    setError(false);
                    console.log('Res check', res);
                    console.log('Data check', res.data.products);
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
                        <SessionComp
                            title={category.name}
                            subtitle="category"
                            items={products}
                            type="product"
                            sizePercentCard={30}
                            linkView={`/category/${category.id}?page=1&limit=6`}
                        />
                    ) : (
                        <></>
                    )}
                    <Pagination
                        current={currentPage}
                        pageSize={1}
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
