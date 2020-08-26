import React, {useEffect} from "react";
import { Link } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";
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
                <hr className="my-3 divider"/>
                <MyNavBar />
                <Container>
                    <Row className="stockStats align-content-center">
                        <StockStats/>
                    </Row>
                    <Row className="justify-content-md-center">
                        <Col className="m-sm-2">
                            <BuyOrders/>
                        </Col>
                        <Col className="col-md">
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