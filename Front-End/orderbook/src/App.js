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
import ViewOrder from "./pages/ViewOrder";
import ViewSingleTrade from "./pages/ViewSingleTrade";
import NotFoundPage from "./pages/NotFoundPage";

function App(){
    return (
        <Provider store={store}>
             <Router>
                <Switch>
                {/*Home Page */}
                <Route exact path="/" component={MainPage}/>
                <Route exact path="/singleTrade" component={ViewSingleTrade}/>
                {/* Only runs if they type in a weird url manually */}
                <Route exact path="/404" component={NotFoundPage}/>
                <Redirect to="/404" />
                </Switch>
             </Router>
        </Provider>
    )
}

export default App;
