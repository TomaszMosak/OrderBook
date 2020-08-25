import React, { useEffect } from "react";
import {connect} from "react-redux";
import { fetchStocks } from "../redux";
import {Table} from "react-bootstrap";

function StockContainer({ stockData, fetchStocks }){

    useEffect(() => {
        fetchStocks()
    }, [])

    return stockData.loading ? (
        <h2>Loading Text</h2>
    ) : stockData.error ? (
        <h2>{stockData.error}</h2>
    ) : (
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>ID</th>
                <th>Symbol</th>
                <th>Exchange</th>
                <th>View OrderBook</th>
            </tr>
            </thead>
            <tbody>
                {
                    stockData.stocks.map(stock =>
                        <tr>
                            <td>{stock.address.city}</td>
                            <td>{stock.address.city}</td>
                            <td>{stock.address.city}</td>
                            <td><a href={"/orderbook/id=" + stock.address.city}>Link to Orderbook</a></td>
                        </tr>)
                }
            </tbody>
        </Table>

    )
}

const mapStateToProps = state => {
    return {
        stockData: state.stock
    }
}

const mapDispatchToProps = dispatch => {
    return {
        fetchStocks: () => dispatch(fetchStocks())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(StockContainer);