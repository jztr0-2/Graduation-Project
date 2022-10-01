import axios from 'axios';

const http = axios.create({
    baseURL: 'http://localhost:8080/api/v1/',
});

export const get = async (path, options = {}) => {
    const response = await http.get(path, options);
    return response.data;
};

export const post = async (path, options = {}) => {
    const response = await http.post(path, options);
    return response;
};
export default http;
