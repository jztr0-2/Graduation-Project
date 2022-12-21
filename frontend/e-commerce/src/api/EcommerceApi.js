import axiosClient from './ClientAxios';
import queryString from 'query-string';
/* Category */
export const CategoryApi = {
    getAll: (params = {}) => {
        const url = '/public/categories';
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
};
export const LoginApi = {
    adminLogin: (data = {}) => {
        const url = `public/users/login`;
        return axiosClient.post(url, data);
    },
};
