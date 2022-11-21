import styles from './promo.module.scss';
import classNames from 'classnames/bind';
import { useState } from 'react';
import { useRef } from 'react';
import { useCallback } from 'react';

import PromoItem from './l-promo-item';

import { validate, validateNumber } from './js/validation';
import { changeStyleElementByObject } from './js/error';

const cx = classNames.bind(styles);
function Promo() {
    const code = useRef();
    const percent = useRef();
    const amount = useRef();
    const maxAmount = useRef();
    const expire = useRef();
    const status = useRef();

    const [indexPagin, setIndexPagin] = useState(1);
    const [arrPromo, setArrPromo] = useState(new Array(0).fill(1));
    const sizePromo = 10;
    const sizeButton =
        arrPromo.length % sizePromo === 0
            ? arrPromo.length / sizePromo === 0
                ? 1
                : arrPromo.length / sizePromo
            : parseInt(arrPromo.length / sizePromo) + 1;
    const arrButton = new Array(sizeButton).fill(1);

    // add new promotion
    const submit = useCallback(() => {
        const obj = {
            code: code.current.value,
            percent: percent.current.value,
            amount: amount.current.value,
            maxAmount: maxAmount.current.value,
            expire: expire.current.value,
            status: status.current.value,
        };
        changeStyleElementByObject(obj, 'boxShadow', '0 0 0 0.05mm rgb(172, 172, 172)');

        const empty = validate(obj);
        const nAN = validateNumber({
            amount: amount.current.value,
            maxAmount: maxAmount.current.value,
        });
        if (Object.keys(empty).length) {
            changeStyleElementByObject(empty, 'boxShadow', '0 0 0 0.5mm red');
            return;
        } else if (Object.keys(nAN).length) {
            changeStyleElementByObject(nAN, 'boxShadow', '0 0 0 0.5mm red');
            return;
        }

        

        setArrPromo((prev) => {
            const new_ = [...prev, obj];
            return new_;
        });
    }, []);
    return (
        <section className={cx('pesudo-wraper')}>
            <section className={cx('side-bar-wrapper')}></section>
            <section className={cx('promo-wrapper')}>
                <h2 className={cx('heading')}>Create promo with react js</h2>
                <section className={cx('promo-main')}>
                    <section>
                        <form
                            action=""
                            className={cx('promo-form')}
                            method="POST"
                            onSubmit={(e) => {
                                e.preventDefault();
                                submit();
                            }}
                        >
                            <h2 className={cx('heading')}>add promo</h2>
                            <section className={cx('promo-input')}>
                                <section className={cx('promo-input-item')}>
                                    <label htmlFor="code">code</label>
                                    <input
                                        ref={code}
                                        type="text"
                                        placeholder="Enter code..."
                                        id="code"
                                        name="code"
                                    />
                                </section>
                                <section className={cx('promo-input-item')}>
                                    <label htmlFor="percent">%</label>
                                    <select ref={percent} name="percent" id="percent">
                                        {new Array(100).fill(1).map((temp, index) => (
                                            <option key={index + 1 + ' percent'} value={index + 1}>
                                                {index + 1}
                                            </option>
                                        ))}
                                    </select>
                                </section>
                                <section className={cx('promo-input-item')}>
                                    <label htmlFor="amount">amount</label>
                                    <input
                                        ref={amount}
                                        type="text"
                                        placeholder="Enter amount..."
                                        id="amount"
                                        name="amount"
                                    />
                                </section>
                                <section className={cx('promo-input-item')}>
                                    <label htmlFor="amount">max amount</label>
                                    <input
                                        ref={maxAmount}
                                        type="text"
                                        placeholder="Enter max amount..."
                                        id="maxAmount"
                                        name="maxAmount"
                                    />
                                </section>
                                <section className={cx('promo-input-item')}>
                                    <label htmlFor="expire">expire</label>
                                    <input ref={expire} type="date" name="expire" id="expire" />
                                </section>
                                <section className={cx('promo-input-item')}>
                                    <label htmlFor="status">status</label>
                                    <select ref={status} name="status" id="status">
                                        <option value="true">true</option>
                                        <option value="false">false</option>
                                    </select>
                                </section>
                                <section className={cx('promo-input-item')}>
                                    <button className="" type="submit">
                                        submit
                                    </button>
                                </section>
                            </section>
                        </form>
                    </section>
                    <section>
                        <section className={cx('list-promo')}>
                            <h2 className={cx('heading')}>list promo</h2>
                            <section className={cx('pesudo-table')}>
                                <table className={cx('table-promo')}>
                                    <thead>
                                        <tr className={cx('table-title')}>
                                            <th>code</th>
                                            <th>percent</th>
                                            <th>amount</th>
                                            <th>expire</th>
                                            <th>status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {arrPromo.map((item, index) => {
                                            if (
                                                sizePromo * indexPagin - sizePromo <= index &&
                                                index < sizePromo * indexPagin
                                            ) {
                                                return <PromoItem id={index} {...item} />;
                                            }
                                            return <></>;
                                        })}
                                    </tbody>
                                </table>
                            </section>
                            <ul className={cx('promo-pagination')}>
                                {arrButton.map((data, index) => (
                                    <li
                                        key={index + 'pagin'}
                                        className={`${cx(`pagination-item`)} ${
                                            indexPagin - 1 === index ? cx(`active`) : ''
                                        }`}
                                    >
                                        <button
                                            onClick={() => {
                                                setIndexPagin(index + 1);
                                            }}
                                        >
                                            {index + 1}
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        </section>
                    </section>
                </section>
            </section>
        </section>
    );
}

export default Promo;
