import React from "react";
import {connect} from "react-redux";
import {Table} from "react-bootstrap";

function BuyOrders({ orderData }){
    return (
        <React.Fragment>
            <h5 className="buyText text-right">Bids</h5>
        <Table striped bordered hover size="sm" className="mr-4">
            <thead>
            <tr>
                <th>Version</th>
                <th>Stock Symbol</th>
                <th>Price Remaining</th>
                <th>Size</th>
                <th>Side</th>
                <th>Status</th>
                <th>CP</th>
                <th>Stamp</th>
            </tr>
            </thead>
            <tbody>
            {
                orderData.buyOrders.map(order =>
                    <tr>
                        <td>{order.id}</td>
                        <td>{order.id}</td>
                        <td>{order.id}</td>
                        <td>{order.id}</td>
                        <td>{order.id}</td>
                        <td>{order.id}</td>
                        <td>{order.id}</td>
                        <td>{order.id}</td>
                    </tr>)
            }
            </tbody>
        </Table>
        </React.Fragment>
    )
}

const mapStateToProps = state => {
    return {
        orderData: state.order,
        tradeData: state.trades
    }
}

export default connect(mapStateToProps)(BuyOrders);