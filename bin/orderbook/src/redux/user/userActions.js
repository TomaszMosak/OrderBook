import axios from "axios";
import {FETCH_USERS_FAILURE, FETCH_USERS_REQUEST, FETCH_USERS_SUCCESS,} from "./userTypes";

const fetchUsersRequest = () => {
    return {
        type: FETCH_USERS_REQUEST
    }
}

const fetchUsersSuccess = users => {
    return {
        type: FETCH_USERS_SUCCESS,
        payload: users
    }
}

const fetchUsersFailure = error => {
    return {
        type: FETCH_USERS_FAILURE,
        payload: error
    }
}

export const fetchUsers = () => {
    return (dispatch) => {
        dispatch(fetchUsersRequest)
        axios.get("http://localhost:8080/user")
            .then(r => {
                const users = r.data
                dispatch(fetchUsersSuccess(users))
                //response data is the array of users
            })
            .catch(err => {
                const errorMsg = err.message + "! Please Reload the webpage";
                console.log(err.message);
                dispatch(fetchUsersFailure(errorMsg))
                // error.message is the error description
            })
    }
}