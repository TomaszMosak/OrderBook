import * as actions from './stockTypes';

const initialState = {
    loading: false,
    numOfStocks: 0,
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
            loading: false,
            stocks: action.payload,
            error: ''
        }
        case actions.FETCH_STOCKS_FAILURE:
            return {
            loading: false,
            stocks: [],
            error: action.payload
        }
        default: return state
    }
}

export default stockReducer;