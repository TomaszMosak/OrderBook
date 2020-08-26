import axios from "axios";
import {FETCH_PARTIES_FAILURE, FETCH_PARTIES_REQUEST, FETCH_PARTIES_SUCCESS,} from "./partyTypes";

const fetchPartiesRequest = () => {
    return {
        type: FETCH_PARTIES_REQUEST
    }
}

const fetchPartiesSuccess = parties => {
    return {
        type: FETCH_PARTIES_SUCCESS,
        payload: parties
    }
}

const fetchPartiesFailure = error => {
    return {
        type: FETCH_PARTIES_FAILURE,
        payload: error
    }
}

export const fetchParties = () => {
    return (dispatch) => {
        dispatch(fetchPartiesRequest)
        axios.get("http://localhost:8080/party")
            .then(r => {
                const parties = r.data
                dispatch(fetchPartiesSuccess(parties))
                //response data is the array of parties
            })
            .catch(err => {
                const errorMsg = err.message + "! Please Reload the webpage";
                console.log(err.message);
                dispatch(fetchPartiesFailure(errorMsg))
                // error.message is the error description
            })
    }
}