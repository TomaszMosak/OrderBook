import React, {Component} from "react";
import {Container, Row, Col, Form, Button} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/MainPage.css'

import MyNavBar from "../components/MyNavBar"
import SingleOrder from "../components/singleOrder";
import {createNewOrder, editExistingOrder} from "../redux";
import {connect} from "react-redux";

class EditOrder extends Component {

    constructor(props) {
        super(props)
    }

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
            <Container className="editOrderContainer">
                <Row className="title">
                    <Col>
                        <h1 className="text-center">Edit Order</h1>
                    </Col>
                </Row>

                <MyNavBar />

                <Container>
                    <Form>
                        <Form.Group>
                            <Form.Label>Stock</Form.Label>
                            <Form.Control placeholder={this.props.orderData.orderHistory[0].stockId} disabled />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Party</Form.Label>
                            <Form.Control placeholder={this.props.orderData} disabled />
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
                            })}/>
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
                            })}/>
                        </Form.Group>
                        <Form.Group>
                            <Button type="submit" onClick={() => this.props.createOrder(this.state.order)}>Submit</Button>
                        </Form.Group>
                    </Form>
                </Container>
            </Container>
        )
    }
    
}

const mapStateToProps = state => {
    return {
        orderData: state.order
    }
}

const mapDispatchToProps = dispatch => {
    return {
        editOrder: order => dispatch(editExistingOrder(order))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(EditOrder);