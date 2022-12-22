// Layouts

// Pages
import Home from '~/pages/Home';
import CategoryDetails from '~/pages/CategoryDetails';
import CategoryAll from '~/pages/CagtegoryAll';
import About from '~/pages/About';
import Contact from '~/pages/Contact';
import ProductDetails from '~/pages/ProductDetails';
import CartDetails from '~/pages/CartDetails';
import HistoryOrders from '~/pages/HistoryOrders';

// Not requires login
const publicRoutes = [
    { path: '/', component: Home },
    { path: '/categories/views', component: CategoryAll },
    { path: '/category/:categoryId', component: CategoryDetails },
    { path: '/product/details/:productId', component: ProductDetails },
    { path: '/about', component: About },
    { path: '/contact', component: Contact },
    { path: '/cart', component: CartDetails },
    { path: '/history/orders', component: HistoryOrders },
    // { path: '/product/:productId' },
];

// Requires login
const privateRoutes = [];

export { publicRoutes, privateRoutes };
