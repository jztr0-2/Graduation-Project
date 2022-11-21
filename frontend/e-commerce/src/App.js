import { Fragment } from 'react';
import { BrowserRouter as Router, Routes, Route, Form } from 'react-router-dom';
import { publicRoutes,privateRoutes } from '~/routes';
import { DefaultLayout } from '~/components/Layout';
function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    {publicRoutes.map((route, index) => {
                        const Page = route.component;
                        let Layout = DefaultLayout;

                        if (route.layout) {
                            Layout = route.layout;
                        } else if (route.layout === null) {
                            Layout = Fragment;
                        }

                        return (
                            <Route
                                key={index}
                                path={route.path}
                                element={
                                    <Layout>
                                        <Page />
                                    </Layout>
                                }
                            />
                        );
                    })}
                       {privateRoutes.map((route, index) => {
                        const Page = route.component;

                        return (
                            <Route
                                key={index}
                                path={route.path}
                                element={ <Page />}
                            />
                        );
                    })}
                     <Route
                                path='*'
                                element={
                                    <h1>Not found</h1>
                                }
                            />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
