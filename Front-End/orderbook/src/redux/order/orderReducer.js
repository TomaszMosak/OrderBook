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
        case actions.FETCH_ORDERS_SUCCESS:
            return {
                loading: false,
                buyOrders: action.payload.buyOrders,
                sellOrders: action.payload.sellOrders,
                error: ''
            }
        case actions.FETCH_ORDERS_FAILURE:
            return {
                loading: false,
                buyOrders: [],
                sellOrders: [],
                error: action.payload
            }
        default: return state
    }
}

export default orderReducer;