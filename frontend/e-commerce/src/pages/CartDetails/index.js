import { useRef } from 'react';
import classNames from 'classnames/bind';
import styles from './CartDetails.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { useStore, actions } from '~/store';
import DataEmpty from '~/components/DataEmpty';
import { AddressApi, OrderApi } from '~/api/EcommerceApi';
const cx = classNames.bind(styles);

function CartDetails() {
    const [state, dispatch] = useStore();
    const { carts, cartItem } = state;
    const infoRef = useRef();
    const handleAddQuantity = (amount, cart) => {
        let cartNew = {
            ...cart,
            quantity: amount - cart.quantity,
        };
        dispatch(actions.addCartItem(cartNew));
    };
    const handleRemoveItem = (productVariantId) => {
        dispatch(actions.removeCartItem({ productVariantId }));
    };
    let totalPriceItems = 0;
    const handleOrder = () => {
        const getElementInfo = infoRef.current;
        const fullname = getElementInfo.querySelector(':nth-child(1)').value;
        const phone = getElementInfo.querySelector(':nth-child(2)').value;
        const province = getElementInfo.querySelector(':nth-child(3)').value;
        const district = getElementInfo.querySelector(':nth-child(4)').value;
        const ward = getElementInfo.querySelector(':nth-child(5)').value;
        const appartmentNo = getElementInfo.querySelector(':nth-child(6)').value;
        const address = {
            fullname,
            phone,
            province,
            district,
            ward,
            appartmentNo,
        };
        let order;
        // Send Address
        AddressApi.createAddress(address)
            .then(
                (res) => {
                    if (res.data) {
                        address.id = res.data.id;
                        order = {
                            addressId: address.id,
                            items: [...carts],
                        };
                        // Send Order
                        OrderApi.createOrder(order)
                            .then(
                                (res) => {
                                    if (res.data) {
                                        console.log('Order', res.data);
                                    }
                                },
                                (error) => {
                                    console.log(error);
                                },
                            )
                            .catch((e) => {
                                console.log(e);
                            });
                    }
                },
                (err) => {
                    console.log(err);
                },
            )
            .catch((e) => {
                console.log(e);
            });
    };
    return (
        <div className={cx('container-others')}>
            {carts.length > 0 ? (
                <div className={cx('container-oders')}>
                    <div className={cx('table-oders-list')}>
                        <label>List Oders</label>
                        <table>
                            <tr>
                                <th>STT</th>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Amount</th>
                                <th>Price</th>
                                <th>Category</th>
                                <th>Order date</th>
                                <th>Delete</th>
                            </tr>
                            {Array.from(carts).map((cart, index) => {
                                let totalPriceItem = Number(cart.quantity) * Number(cart.price);
                                totalPriceItems += totalPriceItem;
                                return (
                                    <tr key={cart.productVariantId}>
                                        <td>{index + 1}</td>
                                        <td>#{cart.productVariantId}</td>
                                        <td>{cart.nameVariant}</td>
                                        <td>
                                            <input
                                                type="number"
                                                value={cart.quantity}
                                                min={1}
                                                className={cx('input-quantity')}
                                                onChange={(e) =>
                                                    handleAddQuantity(e.target.value, cart)
                                                }
                                            />
                                        </td>
                                        <td>
                                            {new Intl.NumberFormat('vi-VN', {
                                                style: 'currency',
                                                currency: 'VND',
                                            }).format(totalPriceItem)}
                                        </td>
                                        <td>{cart.categoryName}</td>
                                        <th>{cart.orderDate}</th>
                                        <th
                                            className={cx('pl')}
                                            onClick={() => {
                                                handleRemoveItem(cart.productVariantId);
                                            }}
                                        >
                                            <FontAwesomeIcon icon={faXmark} />
                                        </th>
                                    </tr>
                                );
                            })}
                        </table>
                        <h2>
                            Total:{' '}
                            {new Intl.NumberFormat('vi-VN', {
                                style: 'currency',
                                currency: 'VND',
                            }).format(totalPriceItems)}
                        </h2>
                    </div>
                    <div className={cx('form-info-oder')} ref={infoRef}>
                        <input type="text" id="fullname" name="fullname" placeholder="Full Name" />

                        <input type="text" id="phone" name="phone" placeholder="Phone" />
                        <input
                            type="text"
                            id="province"
                            name="province"
                            placeholder="Province/City"
                        />
                        <input type="text" id="district" name="district" placeholder="District" />
                        <input type="text" id="ward" name="ward" placeholder="Ward" />
                        <input type="text" placeholder="AppartmentNo" />
                        <button value="Oder" onClick={handleOrder}>
                            Oder All
                        </button>
                    </div>
                </div>
            ) : (
                <DataEmpty message={'No products in your cart'} />
            )}
        </div>
    );
}
export default CartDetails;
