import React, { useEffect } from "react";
import {connect} from "react-redux";
import { fetchStocks } from "../redux";

function StockContainer({ stockData, fetchStocks }){

    useEffect(() => {
        fetchStocks()
    }, [])
    
    return stockData.loading ? (
        <h2>Loading Text</h2>
    ) : stockData.error ? (
        <h2>{stockData.error}</h2>
    ) : (
        <div>
            <h2>Stock List</h2>
            <div>
                {
                    stockData && stockData.stocks && stockData.stocks.map(stock => <p>{stock.address.city}</p>)
                }
            </div>
        </div>
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