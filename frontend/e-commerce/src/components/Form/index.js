import classNames from 'classnames/bind';
import styles from './Form.module.scss';

const cx = classNames.bind(styles);

function Form({ types }) {
    if(types === "sign-in") {
        return <div className={cx('wrapper')}>
            form sign-in
        </div>
    } else{
        return <div className={cx('wrapper')}>
            form signup
        </div>
    }
   
}

export default Form