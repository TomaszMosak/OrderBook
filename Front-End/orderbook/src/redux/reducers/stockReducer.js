import * as actions from '../actions/actionTypes';

const initialState = {
    numOfStocks: 1,
    stocks: []
}

const stockReducer = (state = initialState, action) => {
    switch(action.type){
        case actions.GET_STOCK_LIST: return {
            ...state,
            numOfStocks: state.numOfStocks + 1
        }
        default: return state
    }
}

export default stockReducer;