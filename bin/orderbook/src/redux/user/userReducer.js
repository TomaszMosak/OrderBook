import * as actions from './userTypes';

const initialState = {
    loading: false,
    numOfUsers: -1,
    users: [],
    error: ''
}

const userReducer = (state = initialState, action) => {
    switch(action.type){
        case actions.FETCH_USERS_REQUEST:
            return {
                ...state,
                loading: true
            }
        case actions.FETCH_USERS_SUCCESS:
            return {
                ...state,
                loading: false,
                numOfUsers: action.payload.length,
                users: action.payload,
                error: ''
            }
        case actions.FETCH_USERS_FAILURE:
            return {
                ...state,
                loading: false,
                users: [],
                error: action.payload
            }
        default: return state
    }
}

export default userReducer;