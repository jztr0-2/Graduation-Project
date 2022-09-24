import Home from '~/pages/Home';

// Not requires login
const publicRoutes = [{ path: '/', component: Home }];

// Requires login
const privateRoutes = [];

export { publicRoutes, privateRoutes };
