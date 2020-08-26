import React from 'react';
import { Provider } from 'react-redux';
import store from './redux/store';

import { BrowserRouter as Router, Route, Switch, Redirect} from "react-router-dom";

//Pages
import MainPage from "./pages/MainPage";
import Orderbook from "./pages/Orderbook";
import CreateOrder from "./pages/CreateOrder";
import EditOrder from "./pages/EditOrder";
import UserPage from "./pages/UserPage";
import ViewAllTrades from "./pages/ViewAllTrades";
import ViewOrder from "./pages/ViewSingleOrder";
import ViewSingleTrade from "./pages/ViewSingleTrade";
import NotFoundPage from "./pages/NotFoundPage";

function App() {
    return (
        <Provider store={store}>
             <Router>
                <Switch>
                    <Route exact path="/" component={MainPage}/>
                    <Route exact path="/singleTrade" component={ViewSingleTrade}/>
                    <Route exact path="/orderbook" component={Orderbook}/>
                    <Route exact path="/createOrder" component={CreateOrder}/>
                    <Route exact path="/editOrder" component={EditOrder}/>
                    <Route exact path="/userPage" component={UserPage}/>
                    <Route exact path="/allTrades" component={ViewAllTrades}/>
                    <Route exact path="/singleOrder" component={ViewOrder}/>

                    {/* Only runs if they type in a weird url manually */}
                    <Route exact path="/404" component={NotFoundPage}/>
                    <Redirect to="/404" />
                </Switch>
             </Router>
        </Provider>
    )
}

export default App;
