import * as actions from './orderTypes';

const initialState = {
    loading: false,
    numOfOrders: 0,
    currentOrder: 0,
    buyOrders: [],
    sellOrders: [],
    orderHistory: [],
    error: ''
}

const orderReducer = (state = initialState, action) => {
    switch(action.type){
        case actions.FETCH_ORDERS_REQUEST:
            return {
                ...state,
                loading: true
            }
        case actions.FETCH_BUY_ORDERS_SUCCESS:
            return {
                ...state,
                loading: false,
                buyOrders: action.payload,
                error: ''
            }
        case actions.FETCH_SELL_ORDERS_SUCCESS:
            return {
                ...state,
                loading: false,
                sellOrders: action.payload,
                error: ''
            }
        case actions.FETCH_ORDERS_FAILURE:
            return {
                loading: false,
                buyOrders: [],
                sellOrders: [],
                error: action.payload
            }
        case actions.FETCH_ORDER_HISTORY:
            return {
                ...state,
                loading: false,
                orderHistory: action.payload,
                error: ''
            }
        default: return state
    }
}

export default orderReducer;