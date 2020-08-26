import React from "react";
import {connect} from "react-redux";
import {Card, Container, Row, Col} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/StockStats.css'

function StockStats({ orderData, stockData }){

    return (
        <Card className="border-dark mb-2">
                    <Card.Title className="text-center">Stock Information: {stockData.symbol}</Card.Title>
                    <Card.Subtitle className="subTitle text-center mb-2">{stockData.companyName}</Card.Subtitle>
            <Row>
                <Col>
                    <Card className="innerCard mb-2 ml-2">
                        <Card.Title className="innerCardTitle ml-3">Orders</Card.Title>
                    <Card.Body>
                        <p>Total Accepted: </p>
                        <p>Accepted Today: </p>
                    </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card className="innerCard mb-2 mr-2">
                        <Card.Title className="innerCardTitle text-right mr-3">Trades</Card.Title>
                        <Card.Body>
                            <p>Total Volume Traded: </p>
                            <p>Most Recent Traded Price: </p>
                        </Card.Body>
                    </Card>
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