import classNames from 'classnames/bind';
import { useEffect, useState } from 'react';
import { useDebounce } from '~/hooks';
import { OrderApi } from '~/api/EcommerceApi';
import { ToastContainer, toast } from 'react-toastify';
import styles from './HistoryOrders.module.scss';
const cx = classNames.bind(styles);

function HistoryOrders() {
    const [searchPhone, setSearchPhone] = useState('');
    const [orders, setOrders] = useState([]);
    const debounced = useDebounce(searchPhone, 600);
    useEffect(() => {
        if (!Number(debounced)) {
            console.log('false');
            return;
        }
        console.log(debounced);
        OrderApi.findOrder({
            params: {
                phone: debounced,
            },
        })
            .then(
                (res) => {
                    if (res.data) {
                        setOrders(res.data);
                        if (res.data.length > 0) {
                            toast.success('Find orders successfully');
                        }
                    }
                },
                (err) => {
                    console.log(err);
                },
            )
            .catch((e) => {
                console.log(e);
            });
    }, [debounced]);
    return (
        <div className={cx('custom-container')}>
            <div className={cx('container-oders')}>
                <h1 className={cx('search-heading')}>Enter your phone to find orders</h1>
                <div className={cx('form-info-oder')}>
                    <input
                        type="text"
                        id="phone"
                        name="phone"
                        placeholder="Your phone"
                        onChange={(e) => setSearchPhone(e.target.value)}
                    />

                    <button className={cx('secondary-button', 'mr-[55px]')} value="Oder">
                        Find
                    </button>
                </div>
                {setOrders.length > 0 ? (
                    <div className={cx('table-oders-list')}>
                        <label>List Oders</label>
                        <table>
                            <tr>
                                <th>IdOrder</th>
                                <th>IdProduct</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Amount</th>
                                <th>Total</th>
                                <th>Status</th>
                            </tr>
                            {orders.map((order, index) => {
                                if (order?.status === 0) {
                                    let parseStatus = 'Pending';
                                    order.status = parseStatus;
                                } else if (order?.status === 1) {
                                    let parseStatus = 'Success';
                                    order.status = parseStatus;
                                } else {
                                    let parseStatus = 'Cancel';
                                    order.status = parseStatus;
                                }
                                return order.orderItems.map((orderItem) => {
                                    return (
                                        <tr key={order.id}>
                                            <td>#{order?.id}</td>
                                            <td>{orderItem?.productVariant?.id}</td>
                                            <td>{orderItem?.productVariant?.displayName}</td>
                                            <td>{orderItem?.productVariant?.unitPrice}</td>
                                            <td>{orderItem?.quantity}</td>
                                            <td>
                                                {new Intl.NumberFormat('vi-VN', {
                                                    style: 'currency',
                                                    currency: 'VND',
                                                }).format(
                                                    orderItem?.productVariant?.unitPrice *
                                                        orderItem?.quantity,
                                                )}
                                            </td>
                                            <td>{order?.status}</td>
                                        </tr>
                                    );
                                });
                            })}
                        </table>
                        <h2>Total</h2>
                    </div>
                ) : (
                    <></>
                )}
            </div>
            <ToastContainer />
        </div>
    );
}

export default HistoryOrders;
