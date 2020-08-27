import React from "react";
import {connect} from "react-redux";
import {Table} from "react-bootstrap";
import {Link} from "react-router-dom";
import {editExistingOrder, fetchOrderHistory} from "../redux";

function BuyOrders({ orderData, fetchOrderHistory, tickOrderUp }){
    return (
        <React.Fragment>
            <h5 className="buyText text-right">Bids</h5>
        <Table striped bordered hover size="sm" className="mr-4">
            <thead>
            <tr>
                <th>View Order</th>
                <th>TimeStamp</th>
                <th>Size</th>
                <th>Price</th>
                <th>Tick</th>
            </tr>
            </thead>
            <tbody>
            {
                orderData.buyOrders.map(order =>
                    <tr>
                        <td onClick={() => fetchOrderHistory(order.id)}><Link to="/singleOrder">Order History</Link></td>
                        <td>{order.versionTime}</td>
                        <td>{order.size}</td>
                        <td>{order.price}</td>
                        <td><Link className="text-center" onClick={() => tickOrderUp(order)} to="/orderBook"><i className="arrow up"></i></Link></td>
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
        tickOrderUp: (order) => dispatch(editExistingOrder({
            id: order.id,
            userId: order.userId,
            price: (order.price + order.stock.tickSize).toFixed(2),
            size: order.size
        }))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(BuyOrders);