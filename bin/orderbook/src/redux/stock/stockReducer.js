import * as actions from './stockTypes';

const initialState = {
    loading: false,
    numOfStocks: -1,
    currentStockId: -1,
    selectedStock: [],
    stocks: [],
    error: ''
}

const stockReducer = (state = initialState, action) => {
    switch(action.type){
        case actions.FETCH_STOCKS_REQUEST:
            return {
            ...state,
            loading: true
        }
        case actions.FETCH_STOCKS_SUCCESS:
            return {
                ...state,
            loading: false,
            stocks: action.payload,
            error: ''
        }
        case actions.FETCH_STOCKS_FAILURE:
            return {
                ...state,
            loading: false,
            stocks: [],
            error: action.payload
        }
        case actions.SELECT_SINGLE_STOCK:
            return{
                ...state,
                currentStockId: action.payload.id,
                selectedStock: state.stocks[action.payload.index]
            }
        default: return state
    }
}

export default stockReducer;