// Layouts

// Pages
import Home from '~/pages/Home';
import Promo from '~/pages/Promo';

// Not requires login
const publicRoutes = [{ path: '/', component: Home }];

// Requires login
const privateRoutes = [{ path: '/admin', component: Promo }];

export { publicRoutes, privateRoutes };
