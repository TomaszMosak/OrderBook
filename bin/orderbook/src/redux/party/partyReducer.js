import * as actions from './partyTypes';

const initialState = {
    loading: false,
    numOfParties: -1,
    parties: [],
    error: ''
}

const userReducer = (state = initialState, action) => {
    switch(action.type){
        case actions.FETCH_PARTIES_REQUEST:
            return {
                ...state,
                loading: true
            }
        case actions.FETCH_PARTIES_SUCCESS:
            return {
                ...state,
                loading: false,
                numOfParties: action.payload.length,
                parties: action.payload,
                error: ''
            }
        case actions.FETCH_PARTIES_FAILURE:
            return {
                ...state,
                loading: false,
                parties: [],
                error: action.payload
            }
        default: return state
    }
}

export default userReducer;