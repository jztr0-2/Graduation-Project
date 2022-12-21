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
import styles from './Oders.module.scss';
import SessionComp from '~/components/SessionComp';
import images from '~/assets/images';
import { CategoryApi, ProductApi } from '~/api/EcommerceApi';

const cx = classNames.bind(styles);

function Oders() {
    return (
        <div className={cx('container-others')}>
            <div className={cx('container-oders')}>
                <div className={cx('table-oders-list')}>
                    <label>List Oders</label>
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Name Products</th>
                            <th>Count</th>
                            <th>Price</th>
                        </tr>
                        <tr>
                            <td>#2</td>
                            <td>Iphone 14 ProMax</td>
                            <td>50</td>
                            <td>50.000</td>
                        </tr>
                        <tr>
                            <td>#3</td>
                            <td>Tủ Lạnh Tosiba</td>
                            <td>94</td>
                            <td>94.000</td>
                        </tr>
                        <tr>
                            <td>#4</td>
                            <td>Máy Giặt Panasonic</td>
                            <td>67</td>
                            <td>67.000</td>
                        </tr>
                    </table>
                </div>
                <h2>Total: 1.523.000</h2>
                <div className={cx('form-info-oder')}>
                    <input type="tel" id="phone" name="phone" placeholder="Full Name" />

                    <input type="text" placeholder="Phone" />
                    <select name="cars" id="cars">
                        <option value="volvo">Tinh/TP</option>
                        <option value="saab">Saab</option>
                        <option value="mercedes">Mercedes</option>
                        <option value="audi">Audi</option>
                    </select>
                    <select name="cars" id="cars">
                        <option value="volvo">Quan/Huyen</option>
                        <option value="saab">Saab</option>
                        <option value="mercedes">Mercedes</option>
                        <option value="audi">Audi</option>
                    </select>
                    <select name="cars" id="cars">
                        <option value="volvo">Phuong/Xa</option>
                        <option value="saab">Saab</option>
                        <option value="mercedes">Mercedes</option>
                        <option value="audi">Audi</option>
                    </select>
                    <input type="text" placeholder="So Nha, Ten Duong" />
                    <button value="Oder">Oder All</button>
                </div>
            </div>
        </div>
    );
}
export default Oders;
