import React from "react";
import {connect} from "react-redux";
import {Table} from "react-bootstrap";

function SingleOrder({ orderData }){

    function side(boolean){
        if(boolean){
            return "Buy"
        } else {
            return "Sell"
        }
    }

    return (
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>Version</th>
                <th>Stock Symbol</th>
                <th>Price</th>
                <th>Size</th>
                <th>Side</th>
                <th>Status</th>
                <th>CP</th>
                <th>Timestamp</th>
            </tr>
            </thead>
            <tbody>
            {
                orderData.orderHistory.map(order =>
                <tr>
                    <td>{order.version}</td>
                    <td>{order.stock.symbol}</td>
                    <td>{order.price}</td>
                    <td>{order.size}</td>
                    <td>{side(order.buy)}</td>
                    <td>{order.status}</td>
                    <td>{order.stock.symbol}</td>
                    <td>{order.versionTime}</td>
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

export default connect(mapStateToProps)(SingleOrder);