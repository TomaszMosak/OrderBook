import React from "react";
import {connect} from "react-redux";
import {Card, Container, Row, Col} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/StockStats.css'

function StockStats({ orderData, stockData }){

    return (
        <Card className="border-dark mb-2">
            <Card.Header className="text-center">
                <Card.Title>Stock Information: {stockData.name}</Card.Title>
                    <Card.Subtitle className="text-center">{stockData.username}</Card.Subtitle>
                </Card.Header>
            <Row>
                <Col>
                    <Card.Body>This is some text within a card body.</Card.Body>
                </Col>
                <div className="verticalLine"/>
                <Col>
                    <Card.Body>This is some text within a card body.</Card.Body>
                </Col>
            </Row>
            <Row>
                <Col>
                    <Card.Body>This is some text within a card body.</Card.Body>
                </Col>
                <div className="verticalLine"/>
                <Col>
                    <Card.Body>This is some text within a card body.</Card.Body>
                </Col>
            </Row>
            <Row>
                <Col>
                    <Card.Body>This is some text within a card body.</Card.Body>
                </Col>
                <div className="verticalLine"/>
                <Col>
                    <Card.Body>This is some text within a card body.</Card.Body>
                </Col>
            </Row>
        </Card>
    )
}

const mapStateToProps = state => {
    return {
        orderData: state.order,
        stockData: state.stock.selectedStock
    }
}

export default connect(mapStateToProps, null)(StockStats);