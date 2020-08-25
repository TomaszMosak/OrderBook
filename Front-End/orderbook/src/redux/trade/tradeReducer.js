import * as actions from './tradeTypes';

const initialState = {
    loading: false,
    numOfTrades: -1,
    selectedTradeId: -1,
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
                ...state,
                loading: false,
                trades: action.payload,
                numOfTrades: action.payload.length,
                error: ''
            }
        case actions.FETCH_TRADES_FAILURE:
            return {
                loading: false,
                trades: [],
                error: action.payload
            }
        case actions.SELECT_SINGLE_TRADE:
            return {
                ...state,
                selectedTradeId: action.payload
            }
        default: return state
    }
}

export default tradeReducer;