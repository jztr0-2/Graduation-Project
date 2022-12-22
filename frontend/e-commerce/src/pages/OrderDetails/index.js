// Hooks
// styles
import classNames from 'classnames/bind';
import styles from './OderDetails.module.scss';

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
                    <input type="text" id="fullname" name="fullname" placeholder="Full Name" />

                    <input type="text" id="phone" name="phone" placeholder="Phone" />
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
