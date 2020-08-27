import React from "react";
import {connect} from "react-redux";
import {Table} from "react-bootstrap";
import {Link} from "react-router-dom";
import {editExistingOrder, fetchOrderHistory} from "../redux";

function SellOrders({ orderData, fetchOrderHistory, tickOrderDown }){
    return (
        <React.Fragment>
            <h5 className="sellText text-left">Asks</h5>
        <Table striped bordered hover size="sm" className="mr-4">
            <thead>
            <tr>
                <th>Tick</th>
                <th>Price</th>
                <th>Size</th>
                <th>TimeStamp</th>
                <th>View Order</th>
            </tr>
            </thead>
            <tbody>
            {
                orderData.sellOrders.map(order =>
                    <tr>
                        <td><Link className="text-center" onClick={() => { tickOrderDown(order) } } to="/orderBook"><i className="arrow down"></i></Link></td>
                        <td>{order.price}</td>
                        <td>{order.size}</td>
                        <td>{order.versionTime}</td>
                        <td onClick={() => fetchOrderHistory(order.id)}><Link to="/singleOrder">Order History</Link></td>
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

const mapDispatchToProps = dispatch => {
    return {
        fetchOrderHistory: (id) => dispatch(fetchOrderHistory(id)),
        tickOrderDown: (order) => dispatch(editExistingOrder({
            id: order.id,
            userId: order.userId,
            price: (order.price - order.stock.tickSize).toFixed(2),
            size: order.size
        }))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(SellOrders);