import React, {Component} from "react";
import { Container, Row, Col } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/MainPage.css'

import MyNavBar from "../components/MyNavBar"
import SingleTrade from "../components/singleTrade";

class viewSingleTrade extends Component {
    render() {
        return (
            <Container className="singleTradeContainer">
                <Row className="title">
                    <Col>
                        <h1 className="text-center">The Fantastic OrderBook</h1>
                    </Col>
                </Row>

                <MyNavBar />
                <Container>
                    <Col className="tableContent align-content-center">
                        <h4>Trade Summary</h4>
                        <SingleTrade />
                    </Col>
                </Container>
            </Container>
        )
    }
}

export default viewSingleTrade;