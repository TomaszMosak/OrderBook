import React, { useEffect } from "react";
import {connect} from "react-redux";
import { fetchTrades } from "../redux";
import {Table} from "react-bootstrap";

function StockContainer({ tradeData, fetchTrades }){

    useEffect(() => {
        fetchTrades()
    }, [])

    return tradeData.loading ? (
        <h2>Loading Text</h2>
    ) : tradeData.error ? (
        <h2>{tradeData.error}</h2>
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
                tradeData.trades.map(trade =>
                    <tr>
                        <td>{trade.address.city}</td>
                        <td>{trade.address.city}</td>
                        <td>{trade.address.city}</td>
                        <td>{trade.address.city}</td>
                        <td>{trade.address.city}</td>
                        <td><a href={"/orderbook/id=" + trade.address.city}>Link to Orderbook</a></td>
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
        fetchTrades: () => dispatch(fetchTrades())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(StockContainer);