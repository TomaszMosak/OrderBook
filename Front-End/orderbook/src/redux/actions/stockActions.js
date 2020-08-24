import Redux, { dispatch } from "redux";
import axios from "axios";
import * as actions from './actionTypes'

/*
const fetchStocks = () => {
    return function(dispatch) {
        dispatch(fetchRequest())
        axios.get("/get/stocks")
            .then(r => {
                const stocks = r.data.map(stock => stock.id)
                dispatch(fetchStocksSuccess(stocks))
                //response data is the array of stocks
            })
            .catch(err => {
                dispatch(fetchFailure(err.message))
                // error.message is the error description
            })
    }
}*/

export const addStock = () => {
    return {
        type: actions.GET_STOCK_LIST
    }
}