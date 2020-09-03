import React from "react";
import {connect} from "react-redux";
import {Button, Table} from "react-bootstrap";
import {cancelExistingOrder, fetchOrderHistory} from "../redux";

function SingleOrder({ orderData, cancelOrder }){

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
            <Button onClick={() => { cancelOrder(orderData.orderHistory[0]); window.location.reload() } }>Cancel order</Button>
        </Table>
    )
}

const mapStateToProps = state => {
    return {
        orderData: state.order,
        tradeData: state.trades
    }
}

const mapDispatchToProps = dispatch => {
    return {
        cancelOrder: order => dispatch(cancelExistingOrder(order.id))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(SingleOrder);