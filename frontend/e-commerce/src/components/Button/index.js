import { Link } from 'react-router-dom';
import classNames from 'classnames/bind';
import styles from './Button.module.scss';

const cx = classNames.bind(styles);

function Button({ to, href, children, primary = false, size = 'medium', disabled = false, onClick, ...passProps }) {
    let Component = 'button';

    const props = {
        onClick,
        ...passProps,
    };

    // Remove event listener if button is disabled
    if (disabled) {
        Object.keys(props).forEach((key) => {
            if (key.startsWith('on') && typeof props[key] === 'function') {
                delete props[key];
            }
        });
    }

    if (to) {
        props.to = to;
        Component = Link;
    } else if (href) {
        props.href = href;
        Component = 'a';
    }

    const classes = cx('wrapper', {
        primary,
        size,
        disabled,
    });

    return (
        <Component classNames={classes} {...props}>
            <span>{children}</span>
        </Component>
    );
}

export default Button;
