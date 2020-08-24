import React, {Component} from "react";
import { Link } from "react-router-dom";

import StockContainer from "../components/stocksContainer";

class MainPage extends Component {
    render() {
        return (
            <div>
                <h3>Welcome to our awesome order book</h3>
                <small>Main Page</small>
                <StockContainer />
                <Link to="/404">Show a 404 Error</Link>
            </div>
        )
    }
}

export default MainPage;