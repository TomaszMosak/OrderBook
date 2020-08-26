import axios from "axios";
import {
    FETCH_SINGLE_TRADE,
    FETCH_TRADES_FAILURE,
    FETCH_TRADES_REQUEST,
    FETCH_TRADES_SUCCESS,
    SELECT_SINGLE_TRADE
} from "./tradeTypes";

const fetchTradesRequest = () => {
    return {
        type: FETCH_TRADES_REQUEST
    }
}

const fetchTradesSuccess = (trades) => {
    return {
        type: FETCH_TRADES_SUCCESS,
        payload: trades
    }
}

const fetchTradesFailure = error => {
    return {
        type: FETCH_TRADES_FAILURE,
        payload: error
    }
}

const selectSingleTrade = selectedTradeId => {
    return {
        type: SELECT_SINGLE_TRADE,
        payload: selectedTradeId
    }
}

const foundTradeDetails = trade => {
    return {
        type: FETCH_SINGLE_TRADE,
        payload: trade
    }
}

export const fetchSingleTrade = (id) => {
    return (dispatch) => {
        //NEEDS CHANGING TO FETCH FOR SINGLE TRADE
        dispatch(selectSingleTrade(id))
        axios.get("https://jsonplaceholder.typicode.com/users")
            .then(r => {
                const trades = r.data
                dispatch(foundTradeDetails(trades[id]))
                //response data is the array of stocks
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchTradesFailure(errorMsg))
                // error.message is the error description
            })
    }
}

export const fetchTrades = () => {
    return (dispatch) => {
        dispatch(fetchTradesRequest)
        axios.get("https://jsonplaceholder.typicode.com/users")
            .then(r => {
                const trades = r.data
                dispatch(fetchTradesSuccess(trades))
                //response data is the array of stocks
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchTradesFailure(errorMsg))
                // error.message is the error description
            })
    }
}

export const fetchRecentTrades = () => {
    return (dispatch) => {
        dispatch(fetchTradesRequest)
        axios.get("https://jsonplaceholder.typicode.com/users")
            .then(r => {
                const trades = r.data
                dispatch(fetchTradesSuccess(trades))
                //response data is the array of stocks
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchTradesFailure(errorMsg))
                // error.message is the error description
            })
    }
}