import React from "react";
import {connect} from "react-redux";
import {Table} from "react-bootstrap";
import { Redirect} from "react-router-dom";

function SingleTrade({ tradeData, singleTrade }){

    function search(tradeId, allTrades){
        for(var i=0; i < allTrades.length; i++){
            if(allTrades[i].id === tradeId){
                return allTrades[i]
            }
        }
    }

    const found = search(singleTrade, tradeData.trades)
    if(found == undefined){
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
                    <td>{found.id}</td>
                    <td>{found.name}</td>
                    <td>{found.username}</td>
                    <td>{found.address.city}</td>
                    <td><a href={"/singleTrade"}>Buy Order History</a></td>
                    <td><a href={"/singleTrade"}>Sell Order History</a></td>
                </tr>
            }
            </tbody>
        </Table>

    )
}

const mapStateToProps = state => {
    return {
        tradeData: state.trade,
        singleTrade: state.trade.selectedTradeId
    }
}

export default connect(mapStateToProps)(SingleTrade);