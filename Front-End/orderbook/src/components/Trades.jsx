import React, { useEffect, useState } from "react";
import {connect} from "react-redux";
import {fetchTrades, selectSingleTrade} from "../redux";
import {Table} from "react-bootstrap";
import { Link } from "react-router-dom";

function StockContainer({ tradeData, fetchTrades, selectSingleTrade }){

    useEffect(() => {
        fetchTrades()
    }, [])
    const [id, setId] = useState(tradeData.trades.id);
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
                        <td onClick={() => selectSingleTrade(trade.id)}><Link to="/singleTrade">Link to Trade</Link></td>
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
        selectSingleTrade: (id) => dispatch(selectSingleTrade(id))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(StockContainer);