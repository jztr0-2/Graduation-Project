import { Fragment } from 'react';
import { BrowserRouter as Router, Routes, Route, Form } from 'react-router-dom';
import { publicRoutes } from '~/routes';
import { DefaultLayout } from '~/components/Layout';
function App() {
    // useEffect(() => {
    //     isAdmin().then((res) => {
    //         if (res.statusCode == 401) {
    //             setToken(false);
    //         } else {
    //             setToken(true);
    //         }
    //     });
    // }, []);

    // const [token, setToken] = useState();
    // if (!token) {
    //     return <LoginForm setToken={setToken} />;
    // }
    // const logout = () => {
    //     localStorage.clear();
    //     alert('Logout succsecfuly');
    //     window.location.reload(false);
    // };
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
                </Routes>
            </div>
        </Router>
    );
}

export default App;
