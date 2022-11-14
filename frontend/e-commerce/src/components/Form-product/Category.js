import { useEffect, useState } from "react";
import * as request from '~/utils/http';
import classNames from 'classnames/bind';
import styles from './Form.module.scss';

const cx = classNames.bind(styles);

function Category() {
    const [categories, setCategories] = useState([]);
    useEffect(() => {
        request.get("public/categories")
            .then((response) => {
                setCategories(response.data);
            })
            .catch((error) => {
                console.log(error);
            })
    }, []);

    return (
        <select name="selectCategory" id="" className={cx('products-category')}>
          <option disabled selected>Select Category</option>
          {categories.map(category => (
            <option key={category.id} value={category.id}>{category.name}</option>
          ))}
        </select>
    )
}

export default Category;