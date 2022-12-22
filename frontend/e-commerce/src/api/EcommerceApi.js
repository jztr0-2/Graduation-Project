import axiosClient from './ClientAxios';
import queryString from 'query-string';
/* Category */
export const CategoryApi = {
    getAll: (params = {}) => {
        const url = '/public/categories';
        return axiosClient.get(url, { params });
    },
    getAllPage: (params = {}) => {
        const url = `public/categories/views/page`;
        return axiosClient.get(url, { params });
    },
    getByCategoryId: (params = {}, pathVariable = '') => {
        const url = `/public/categories/${pathVariable}`;
        return axiosClient.get(url, params);
    },
    getAllIncludeProducts: (params = {}) => {
        const url = `/public/categories/views`;
        return axiosClient.get(url, params);
    },
    getSaleCategory: (params = {}) => {
        const url = `/public/products/top-category`;
        return axiosClient.get(url, params);
    },
};
export const ProductApi = {
    getSaleProducts: (params = {}) => {
        const url = `/public/products/top-sale`;
        return axiosClient.get(url, params);
    },
    getById: (pathVariable) => {
        const url = `/public/products/${pathVariable}`;
        return axiosClient.get(url, {});
    },
};
<<<<<<< HEAD
export const LoginApi = {
    adminLogin: (data = {}) => {
=======
export const AddressApi = {
    createAddress: (params = {}) => {
        const url = `/public/address`;
        return axiosClient.post(url, params);
    },
};
export const OrderApi = {
    createOrder: (params = {}) => {
        const url = `/public/orders`;
        return axiosClient.post(url, params);
    },
};

export const LoginApi = {
    userLogin: (data = {}) => {
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366
        const url = `public/users/login`;
        return axiosClient.post(url, data);
    },
};
