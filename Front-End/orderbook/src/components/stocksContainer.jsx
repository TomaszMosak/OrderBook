import React, {Component} from "react";
/*import { connect } from 'react-redux';*/
import { useSelector, useDispatch} from "react-redux";
import { addStock } from '../redux/actions/stockActions'

function StockContainer(props){
    const numOfStocks = useSelector(state => state.stockReducer.numOfStocks)
    const dispatch = useDispatch()
    return (
        <div>
            <h2>Number of stocks - {numOfStocks}</h2>
            <button onClick={() => dispatch(addStock())}>Add A Stock</button>
        </div>
    )
}

/*const mapStateToProps = state => {
    return {
        numOfStocks: state.numOfStocks
    }
}

const mapDispatchToProps = dispatch => {
    return {
        addStock: () => dispatch()
    }
}*/

export default /*connect(mapStateToProps, mapDispatchToProps)*/(StockContainer);