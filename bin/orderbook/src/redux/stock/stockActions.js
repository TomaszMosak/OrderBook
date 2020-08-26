import axios from "axios";
import {FETCH_STOCKS_FAILURE, FETCH_STOCKS_REQUEST, FETCH_STOCKS_SUCCESS, SELECT_SINGLE_STOCK} from "./stockTypes";

const fetchStocksRequest = () => {
    return {
        type: FETCH_STOCKS_REQUEST
    }
}

const fetchStocksSuccess = stocks => {
    return {
        type: FETCH_STOCKS_SUCCESS,
        payload: stocks
    }
}

const fetchStocksFailure = error => {
    return {
        type: FETCH_STOCKS_FAILURE,
        payload: error
    }
}

export const selectStock = (id, index) => {
    return {
        type: SELECT_SINGLE_STOCK,
        payload: {
            id,
            index
        }
    }
}

export const fetchStocks = () => {
    return (dispatch) => {
        dispatch(fetchStocksRequest)
        axios.get("http://localhost:8080/stock")
            .then(r => {
                const stocks = r.data
                dispatch(fetchStocksSuccess(stocks))
                //response data is the array of stocks
            })
            .catch(err => {
                const errorMsg = err.message + "! Please Reload the webpage";
                console.log(err.message);
                dispatch(fetchStocksFailure(errorMsg))
                // error.message is the error description
            })
    }
}