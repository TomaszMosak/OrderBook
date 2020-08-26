import React, {Component} from "react";
import { Link } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/Orderbook.css'

import BuyOrders from "../components/BuyOrders";
import SellOrders from "../components/SellOrders";
import MyNavBar from "../components/MyNavBar"
import StockStats from "../components/StockStats";

class MainPage extends Component {
    render() {
        return (
            <Container className="mainPageContainer">
                <Row className="title">
                    <Col>
                        <h1 className="text-center">The Fantastic OrderBook</h1>
                    </Col>
                </Row>
                <hr className="my-3 divider"/>
                <MyNavBar />
                <Container>
                    <Col className="stockStats align-content-center">
                        <StockStats/>
                    </Col>
                    <Col className="align-content-center">
                        <Row>
                            <BuyOrders/>
                        </Row>
                        <Row>
                            <SellOrders/>
                        </Row>
                    </Col>
                </Container>
                <Link to="/404">Show a 404 Error</Link>
            </Container>
        )
    }
}

export default MainPage;