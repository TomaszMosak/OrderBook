import React, {Component} from "react";
import {Button, Container, Row, Col, Form} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/MainPage.css'

import MyNavBar from "../components/MyNavBar"
import {connect} from "react-redux";
import {createNewOrder} from "../redux";

class CreateOrder extends Component {

    componentDidMount() {
        this.state = {
            order: {
                stockId: 1,
                partyId: 1,
                userId: 1,
                buy: false,
                price: 100.00,
                size: 1
            }
        }
    }

    render() {
        return (
            <Container className="createOrderContainer">
                <Row className="title">
                    <Col>
                        <h1 className="text-center">Create Order</h1>
                    </Col>
                </Row>

                <MyNavBar />

                <Container>
                    <Form>
                        <Form.Group>
                            <Form.Label>Stock</Form.Label>
                            <Form.Control placeholder="pee" disabled />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Party</Form.Label>
                            <Form.Control placeholder="party haha" disabled />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Side</Form.Label>
                            <Form.Check inline label="Buy" type="radio" name="side" onClick={() => this.setState({
                                order: {
                                    stockId: this.state.order.stockId,
                                    partyId: this.state.order.partyId,
                                    userId: this.state.order.userId,
                                    buy: true,
                                    price: this.state.order.price,
                                    size: this.state.order.size
                                }
                            })} />
                            <Form.Check inline label="Sell" type="radio" name="side" onClick={() => this.setState({
                                order: {
                                    stockId: this.state.order.stockId,
                                    partyId: this.state.order.partyId,
                                    userId: this.state.order.userId,
                                    buy: false,
                                    price: this.state.order.price,
                                    size: this.state.order.size
                                }
                            })} />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Price</Form.Label>
                            <Form.Control onChange={(event) => this.setState({
                                order: {
                                    stockId: this.state.order.stockId,
                                    partyId: this.state.order.partyId,
                                    userId: this.state.order.userId,
                                    buy: this.state.order.buy,
                                    price: event.target.value,
                                    size: this.state.order.size
                                }
                            })} />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Size</Form.Label>
                            <Form.Control onChange={(event) => this.setState({
                                order: {
                                    stockId: this.state.order.stockId,
                                    partyId: this.state.order.partyId,
                                    userId: this.state.order.userId,
                                    buy: this.state.order.buy,
                                    price: this.state.order.price,
                                    size: event.target.value
                                }
                            })} />
                        </Form.Group>
                        <Form.Group>
                            <Button onClick={() => this.props.createOrder(this.state.order)}>Submit</Button>
                        </Form.Group>
                    </Form>
                </Container>
            </Container>
        )
    }

}

const mapDispatchToProps = dispatch => {
    return {
        createOrder: order => dispatch(createNewOrder(order))
    }
}

export default connect(null, mapDispatchToProps)(CreateOrder);