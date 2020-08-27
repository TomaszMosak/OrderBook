import React from "react";
import {connect} from "react-redux";
import {Table} from "react-bootstrap";
import {Link, Redirect} from "react-router-dom";
import {fetchOrderHistory} from "../redux";

function SingleTrade({ tradeData, fetchOrderHistory }){

    if(tradeData.selectedTrade === undefined){
        return(
        <Redirect to="/404"/>
        )
    }

    return (
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>Trade ID</th>
                <th>Size</th>
                <th>Price</th>
                <th>Timestamp</th>
                <th>View Buy Order</th>
                <th>View Sell Order</th>
            </tr>
            </thead>
            <tbody>
            {
                    <tr>
                        <td>{tradeData.selectedTrade.id}</td>
                        <td>{tradeData.selectedTrade.quantity}</td>
                        <td>{tradeData.selectedTrade.price}</td>
                        <td>{tradeData.selectedTrade.executionTime}</td>
                        <td onClick={() => fetchOrderHistory(tradeData.selectedTrade.buyOrder.id)}><Link to="/singleOrder">Buy Order History</Link></td>
                        <td onClick={() => fetchOrderHistory(tradeData.selectedTrade.sellOrder.id)}><Link to="/singleOrder">Sell Order History</Link></td>
                    </tr>
            }
            </tbody>
        </Table>

    )
}

const mapStateToProps = state => {
    return {
        tradeData: state.trade,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        fetchOrderHistory: (id) => dispatch(fetchOrderHistory(id))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(SingleTrade);