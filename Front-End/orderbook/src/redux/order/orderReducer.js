import * as actions from './orderTypes';

const initialState = {
    loading: false,
    numOfOrders: 0,
    currentOrder: 0,
    buyOrders: [],
    sellOrders: [],
    error: ''
}

const stockReducer = (state = initialState, action) => {
    switch(action.type){
        case actions.FETCH_ORDERS_REQUEST:
            return {
                ...state,
                loading: true
            }
        case actions.FETCH_ORDERS_SUCCESS:
            return {
                loading: false,
                stocks: action.payload,
                error: ''
            }
        case actions.FETCH_ORDERS_FAILURE:
            return {
                loading: false,
                stocks: [],
                error: action.payload
            }
        default: return state
    }
}

export default stockReducer;