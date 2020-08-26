import React, { useEffect } from "react";
import {connect} from "react-redux";
import { fetchStocks, selectStock } from "../redux";
import {Table} from "react-bootstrap";
import {Link} from "react-router-dom";

function StockContainer(props){

    useEffect(() => {
        props.fetchStocks()
    }, [])

    return props.stockData.loading ? (
        <h2>Loading Text</h2>
    ) : props.stockData.error ? (
        <h2>{props.stockData.error}</h2>
    ) : (
        <Table striped bordered hover size="sm">
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
                    props.stockData.stocks.map(stock =>
                        <tr>
                            <td>{stock.address.city}</td>
                            <td>{stock.address.city}</td>
                            <td>{stock.address.city}</td>
                            <td onClick={() => props.selectStock(stock.id, props.stockData.stocks.indexOf(stock))}><Link to="/Orderbook">Orderbook Link</Link></td>
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
        fetchStocks: () => dispatch(fetchStocks()),
        selectStock: (id, index) => dispatch(selectStock(id,index))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(StockContainer);