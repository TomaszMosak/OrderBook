import React, {Component} from "react";
import { Link } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/MainPage.css'

import StockContainer from "../components/stocksContainer";
import MyNavBar from "../components/MyNavBar"
import MostRecentTrades from "../components/recentTrades";

class MainPage extends Component {
    render() {
        return (
            <Container className="mainPageContainer">
                <Row className="title">
                    <Col>
                        <h1 className="text-center">The Fantastic OrderBook</h1>
                    </Col>
                </Row>
                <MyNavBar />
                <Container>
                    <Col className="tableContent align-content-center">
                        <h5>Recent Trades</h5>
                        <MostRecentTrades />
                    </Col>
                    <Col className="align-content-center">
                        <h5>Stocks Available</h5>
                        <StockContainer />
                    </Col>
                </Container>
                <Link to="/404">Show a 404 Error</Link>
            </Container>
        )
    }
}

export default MainPage;