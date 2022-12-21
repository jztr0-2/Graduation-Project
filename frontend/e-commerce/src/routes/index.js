// Layouts

// Pages
import Home from '~/pages/Home';
import CategoryDetails from '~/pages/CategoryDetails';
import CategoryAll from '~/pages/CagtegoryAll';
import About from '~/pages/About';
import Contact from '~/pages/Contact';
import ProductDetails from '~/pages/ProductDetails';

// Not requires login
const publicRoutes = [
    { path: '/', component: Home },
    { path: '/categories/views', component: CategoryAll },
    { path: '/category/:categoryId', component: CategoryDetails },
    { path: '/about', component: About },
    { path: '/contact', component: Contact },
    { path: '/product/details/:productId', component: ProductDetails },
    // { path: '/product/:productId' },
];

// Requires login
const privateRoutes = [];

export { publicRoutes, privateRoutes };
