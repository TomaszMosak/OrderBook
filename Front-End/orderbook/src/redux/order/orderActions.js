import axios from "axios";
import {
    FETCH_ORDERS_FAILURE,
    FETCH_ORDERS_REQUEST,
    FETCH_BUY_ORDERS_SUCCESS,
    FETCH_SELL_ORDERS_SUCCESS,
    FETCH_ORDER_HISTORY
} from "./orderTypes";

const fetchOrdersRequest = () => {
    return {
        type: FETCH_ORDERS_REQUEST
    }
}

const fetchBuyOrdersSuccess = (orders) => {
    return {
        type: FETCH_BUY_ORDERS_SUCCESS,
        payload: orders
    }
}

const fetchSellOrdersSuccess = (orders) => {
    return {
        type: FETCH_SELL_ORDERS_SUCCESS,
        payload: orders
    }
}

const fetchOrdersFailure = error => {
    return {
        type: FETCH_ORDERS_FAILURE,
        payload: error
    }
}

export const fetchAllOrders = () => {
    return (dispatch) => {
        dispatch(fetchOrdersRequest)
        axios.get("http://localhost:8080/order/buy")
            .then(r => {
                const buyOrders = r.data
                dispatch(fetchBuyOrdersSuccess(buyOrders))
                //response data is the array of stocks
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchOrdersFailure(errorMsg))
                // error.message is the error description
            })
        axios.get("http://localhost:8080/order/sell")
            .then(r => {
                const sellOrders = r.data
                dispatch(fetchSellOrdersSuccess(sellOrders))
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchOrdersFailure(errorMsg))
            })
    }
}

const returnOrderHistory = (orderHistory) => {
    return {
        type: FETCH_ORDER_HISTORY,
        payload: orderHistory
    }
}

export const fetchOrderHistory = orderId => {
    return (dispatch) => {
        dispatch(fetchOrdersRequest)
        axios.get("http://localhost:8080/order/history/" + orderId)
            .then(r => {
                const orderHistory = r.data
                dispatch(returnOrderHistory(orderHistory))
                //response data is the array of stocks
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchOrdersFailure(errorMsg))
                // error.message is the error description
            })
    }
}