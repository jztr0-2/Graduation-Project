import classNames from 'classnames/bind';
import styles from './Contact.module.scss';
const cx = classNames.bind(styles);

function Contact() {
    return (
        <div className={cx('container-others')}>
            <div className={cx('container-wapper')}>
                <div className={cx('contact-left')}>
                    <label>Write Us</label>
                    <div className={cx('contact-details')}>
                        <input type="tel" id="phone" name="phone" placeholder="Name" />

                        <input type="text" placeholder="Email" />

                        <input type="text" placeholder="Subject" />
                        <textarea type="text" placeholder="Messenger" />
                        <button>Send Messenger</button>
                    </div>
                </div>
                <div className={cx('contact-right')}>
                    <img src="https://preview.colorlib.com/theme/bootstrap/contact-form-16/images/undraw-contact.svg" />
                </div>
            </div>
        </div>
    );
}
export default Contact;
