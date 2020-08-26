import axios from "axios";
import {
    FETCH_ORDERS_FAILURE,
    FETCH_ORDERS_REQUEST,
    FETCH_BUY_ORDERS_SUCCESS,
    FETCH_SELL_ORDERS_SUCCESS,
    FETCH_ORDER_HISTORY,
    CREATE_NEW_ORDER,
    CANCEL_ORDER,
    EDIT_ORDER
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

const returnNewOrder = order => {
    return {
        type: CREATE_NEW_ORDER,
        payload: order
    }
}

export const createNewOrder = order => {
    return (dispatch) => {
        dispatch(fetchOrdersRequest)
        axios.post("http://localhost:8080/order",
            {stockId: order.stock.id,
            partyId: order.party.id,
            userId: 1,
            isBuy: order.isBuy,
            price: order.price,
            size: order.size})
            .then(r => {
                const newOrder = r.data
                dispatch(returnOrderHistory(newOrder.id))
                dispatch(returnNewOrder(newOrder))
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchOrdersFailure(errorMsg))
            })
    }
}

const returnCancelled = order => {
    return {
        type: CANCEL_ORDER,
        payload: order
    }
}

export const cancelExistingOrder = orderId => {
    return (dispatch) => {
        dispatch(fetchOrdersRequest)
        axios.post("http://localhost:8080/order/cancel" + orderId,
            {userId: 1})
            .then(r => {
                const cancelledOrder = r.data
                dispatch(returnCancelled(cancelledOrder))
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchOrdersFailure(errorMsg))
            })
    }
}

const returnEdit = order => {
    return {
        type: EDIT_ORDER,
        payload: order
    }
}

export const editExistingOrder = order => {
    return (dispatch) => {
        dispatch(fetchOrdersRequest)
        axios.post("http://localhost:8080/order/edit" + order.id,
            {userId: 1,
            price: order.price,
            size: order.size})
            .then(r => {
                const editOrder = r.data
                dispatch(returnEdit(editOrder))
            })
            .catch(err => {
                const errorMsg = err.message
                dispatch(fetchOrdersFailure(errorMsg))
            })
    }
}