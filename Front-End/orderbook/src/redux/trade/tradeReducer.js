import * as actions from './tradeTypes';

const initialState = {
    loading: false,
    numOfTrades: -1,
    currentTrade: -1,
    trades: [],
    error: ''
}

const tradeReducer = (state = initialState, action) => {
    switch(action.type){
        case actions.FETCH_TRADES_REQUEST:
            return {
                ...state,
                loading: true
            }
        case actions.FETCH_TRADES_SUCCESS:
            return {
                loading: false,
                trades: action.payload,
                error: ''
            }
        case actions.FETCH_TRADES_FAILURE:
            return {
                loading: false,
                trades: [],
                error: action.payload
            }
        default: return state
    }
}

export default tradeReducer;