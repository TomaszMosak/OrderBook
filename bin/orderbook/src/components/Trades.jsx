import React, { useEffect } from "react";
import {connect} from "react-redux";
import {fetchTrades, fetchSingleTrade} from "../redux";
import {Table} from "react-bootstrap";
import { Link } from "react-router-dom";

function Trades(props){
    useEffect(() => {
        props.fetchTrades()
    }, [])
    return props.tradeData.loading ? (
        <h2>Loading Text</h2>
    ) : props.tradeData.error ? (
        <h2>{props.tradeData.error}</h2>
    ) : (
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>Stock</th>
                <th>Price</th>
                <th>Size</th>
                <th>Timestamp</th>
                <th>Buy/Sell Party</th>
                <th>View Trade</th>
            </tr>
            </thead>
            <tbody>
            {
                props.tradeData.trades.reverse().map(trade =>
                    <tr>
                        <td>{trade.buyOrder.stock.symbol}</td>
                        <td>{trade.price}</td>
                        <td>{trade.quantity}</td>
                        <td>{trade.executionTime}</td>
                        <td>{trade.buyOrder.stock.centralParty.symbol}</td>
                        <td onClick={() => props.selectSingleTrade(trade.id)}><Link to="/singleTrade">Trade Details</Link></td>
                    </tr>)
            }
            </tbody>
        </Table>
    )
}

const mapStateToProps = state => {
    return {
        tradeData: state.trade
    }
}

const mapDispatchToProps = dispatch => {
    return {
        fetchTrades: () => dispatch(fetchTrades()),
        selectSingleTrade: (id) => dispatch(fetchSingleTrade(id))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Trades);