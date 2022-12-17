// Layouts

// Pages
import Home from '~/pages/Home';
import CategoryDetails from '~/pages/CategoryDetails';
import CategoryAll from '~/pages/CagtegoryAll';

// Not requires login
const publicRoutes = [
    { path: '/', component: Home },
    { path: '/categories/views', component: CategoryAll },
    { path: '/category/:categoryId', component: CategoryDetails },
    // { path: '/product/:productId' },
];

// Requires login
const privateRoutes = [];

export { publicRoutes, privateRoutes };
