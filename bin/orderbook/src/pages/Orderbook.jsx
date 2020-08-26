import React, {useEffect} from "react";
import { Link } from "react-router-dom";
import { Button, Container, Row, Col } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/Orderbook.css'

import BuyOrders from "../components/BuyOrders";
import SellOrders from "../components/SellOrders";
import MyNavBar from "../components/MyNavBar"
import StockStats from "../components/StockStats";

import {fetchAllOrders} from "../redux";
import {connect} from "react-redux";

function Orderbook (props){
    useEffect(() => {
        props.fetchAllOrders()
    }, [])
        return (
            <Container className="mainPageContainer">
                <Row className="title">
                    <Col>
                        <h1 className="text-center">The Fantastic OrderBook</h1>
                    </Col>
                </Row>

                <MyNavBar />
                <Container>
                    <Col className="stockStats align-content-center">
                        <StockStats/>
                    </Col>
                    <Row>
                        <Button href="/createOrder">Create order</Button>
                    </Row>
                    <Row className="justify-content-md-center">
                        <Col className="">
                            <BuyOrders/>
                        </Col>
                        <div className="orderSplit"/>
                        <Col>
                            <SellOrders/>
                        </Col>
                    </Row>
                </Container>
                <Link to="/404">Show a 404 Error</Link>
            </Container>
        )
}

const mapDispatchToProps = (dispatch) => {
    return {
        fetchAllOrders: (id) => dispatch(fetchAllOrders(id))
    }
}

export default connect(null, mapDispatchToProps)(Orderbook);