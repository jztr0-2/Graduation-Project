import axios from 'axios';
import queryString from 'query-string';

const axiosClient = axios.create({
    baseURL: `${process.env.REACT_APP_PUBLIC_API}/api/v1/`,
    headers: {
        'content-type': 'application/json',
        'Authorization': localStorage.getItem("token"),
    },
    // withCredentials: false,
    // paramsSerializer: params => queryString.stringify(params),
});

axiosClient.interceptors.request.use(async (config) => {
    const token = localStorage.getItem('auth_token');
    const refeshtoken = localStorage.getItem('auth_token_refesh');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
        config.headers.refeshAuthorization = `Bearer ${refeshtoken}`;
    }
    return config;
});

axiosClient.interceptors.response.use(
    (response) => {
        if (response && response.data) {
            return response.data;
        }
        return response;
    },
    (error) => {
        throw error;
    },
);

export default axiosClient;
