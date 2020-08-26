import React, {Component} from "react";
import {Button, Container, Row, Col, Form} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/MainPage.css'

import MyNavBar from "../components/MyNavBar"

class CreateOrder extends Component {
    
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
                            <Form.Check inline label="Buy" type="radio" />
                            <Form.Check inline label="Sell" type="radio" />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Price</Form.Label>
                            <Form.Control />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Size</Form.Label>
                            <Form.Control />
                        </Form.Group>
                        <Form.Group>
                            <Button type="submit">Submit</Button>
                        </Form.Group>
                    </Form>
                </Container>
            </Container>
        )
    }

}

export default CreateOrder