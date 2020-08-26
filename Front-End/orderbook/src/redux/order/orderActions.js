import axios from "axios";
import {FETCH_ORDERS_FAILURE, FETCH_ORDERS_REQUEST, FETCH_ORDERS_SUCCESS} from "./orderTypes";

const fetchOrdersRequest = () => {
    return {
        type: FETCH_ORDERS_REQUEST
    }
}

const fetchOrdersSuccess = (buyOrders, sellOrders) => {
    return {
        type: FETCH_ORDERS_SUCCESS,
        payload: {
            buyOrders,
            sellOrders
        }
    }
}

const fetchOrdersFailure = error => {
    return {
        type: FETCH_ORDERS_FAILURE,
        payload: error
    }
}

export const fetchOrders = () => {
    return (dispatch) => {
        dispatch(fetchOrdersRequest)
        axios.get("https://jsonplaceholder.typicode.com/users")
            .then(r => {
                const orders = r.data
                dispatch(fetchOrdersSuccess(orders))
                //response data is the array of stocks
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchOrdersFailure(errorMsg))
                // error.message is the error description
            })
    }
}