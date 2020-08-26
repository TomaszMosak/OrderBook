import React from "react";
import {connect} from "react-redux";
import {Card, Container, Row, Col} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';

function StockStats({ orderData, stockData }){

    return (
        <Card>
            <Card.Title className="text-center">Stock Information: {orderData.numOfOrders}</Card.Title>
            <Card.Body>This is some text within a card body.</Card.Body>
        </Card>
    )
}

const mapStateToProps = state => {
    return {
        orderData: state.order,
        stockData: state.stocks.selectedStock
    }
}

export default connect(mapStateToProps, null)(StockStats);