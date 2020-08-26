import React, {Component} from "react";
import { Container, Row, Col } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/MainPage.css'

import MyNavBar from "../components/MyNavBar"
import SingleOrder from "../components/singleOrder";

class viewSingleOrder extends Component {
    render() {
        return (
            <Container className="singleTradeContainer">
                <Row className="title">
                    <Col>
                        <h1 className="text-center">The Fantastic OrderBook</h1>
                    </Col>
                </Row>
                <hr className="my-3 divider"/>
                <MyNavBar />
                <Container>
                    <Col className="tableContent align-content-center">
                        <h4>Order History</h4>
                        <SingleOrder />
                    </Col>
                </Container>
            </Container>
        )
    }
}

export default viewSingleOrder;