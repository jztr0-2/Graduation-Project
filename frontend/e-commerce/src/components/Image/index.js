import { useState, forwardRef } from 'react';
import images from '~/assets/images';
const Image = forwardRef(
    ({ src, alt, fallback: custonFallback = images.noImage, ...props }, ref) => {
        const [fallback, setFallback] = useState('');

        const handleErrLoadingImg = () => {
            setFallback(custonFallback);
        };

        return (
            <img
                ref={ref}
                src={fallback || src}
                alt={alt}
                {...props}
                onError={handleErrLoadingImg}
            />
        );
    },
);

export default Image;
