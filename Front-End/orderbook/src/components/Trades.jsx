import React, { useEffect } from "react";
import {connect} from "react-redux";
import {fetchTrades, fetchSingleTrade} from "../redux";
import {Table} from "react-bootstrap";
import { Link } from "react-router-dom";

function StockContainer(props){
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
                props.tradeData.trades.map(trade =>
                    <tr>
                        <td>{trade.address.city}</td>
                        <td>{trade.address.city}</td>
                        <td>{trade.address.city}</td>
                        <td>{trade.address.city}</td>
                        <td>{trade.address.city}</td>
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

export default connect(mapStateToProps, mapDispatchToProps)(StockContainer);