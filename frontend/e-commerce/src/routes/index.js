// Layouts

// Pages
import Home from '~/pages/Home';
import CategoryDetails from '~/pages/CategoryDetails';
import CategoryAll from '~/pages/CagtegoryAll';
import About from '~/pages/About';
import Contact from '~/pages/Contact';
<<<<<<< HEAD
import Oders from '~/pages/Oders';
=======
import ProductDetails from '~/pages/ProductDetails';
import CartDetails from '~/pages/CartDetails';

>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366
// Not requires login
const publicRoutes = [
    { path: '/', component: Home },
    { path: '/categories/views', component: CategoryAll },
    { path: '/category/:categoryId', component: CategoryDetails },
    { path: '/product/details/:productId', component: ProductDetails },
    { path: '/about', component: About },
    { path: '/contact', component: Contact },
<<<<<<< HEAD
    { path: '/oder', component: Oders },
=======
    { path: '/cart', component: CartDetails },
    // { path: '/product/:productId' },
>>>>>>> 40ac54493aa995b4101e8a19e041cabcd8e54366
];

// Requires login
const privateRoutes = [];

export { publicRoutes, privateRoutes };
