import React from "react";
import {connect} from "react-redux";
import {Table} from "react-bootstrap";
import {Link} from "react-router-dom";
import {fetchOrderHistory} from "../redux";

function SellOrders({ orderData, fetchOrderHistory }){
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
                        <td className="text-center"><i className="arrow down"></i></td>
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
        fetchOrderHistory: (id) => dispatch(fetchOrderHistory(id))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(SellOrders);