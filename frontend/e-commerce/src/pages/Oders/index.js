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
                <div className={cx('table-oders-list')}></div>
                <div className={cx('form-info-oder')}></div>
            </div>
        </div>
    );
}
export default Oders;
