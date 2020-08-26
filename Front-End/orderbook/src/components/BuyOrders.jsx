import React, {useEffect} from "react";
import {connect} from "react-redux";
import {Table} from "react-bootstrap";

function BuyOrders({ orderData }){
    return (
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>Version</th>
                <th>Stock Symbol</th>
                <th>Price Remaining</th>
                <th>Size</th>
                <th>Side</th>
                <th>Status</th>
                <th>CP</th>
                <th>Timestamp</th>
            </tr>
            </thead>
            <tbody>
            {
                orderData.buyOrders.map(order =>
                    <tr>
                        <td>{order.id}</td>
                        <td>{order.name}</td>
                        <td>{order.username}</td>
                        <td>{order.address.city}</td>
                        <td>{order.address.city}</td>
                        <td>{order.address.city}</td>
                        <td>{order.address.city}</td>
                        <td>{order.address.city}</td>
                    </tr>)
            }
            </tbody>
        </Table>
    )
}

const mapStateToProps = state => {
    return {
        orderData: state.order,
        tradeData: state.trades
    }
}

export default connect(mapStateToProps)(BuyOrders);