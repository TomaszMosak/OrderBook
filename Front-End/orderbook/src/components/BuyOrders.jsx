import React from "react";
import {connect} from "react-redux";
import {Table} from "react-bootstrap";

function BuyOrders({ orderData }){
    return (
        <React.Fragment>
            <h5 className="buyText text-right">Bids</h5>
        <Table striped bordered hover size="sm" className="mr-4">
            <thead>
            <tr>
                <th>View Order</th>
                <th>TimeStamp</th>
                <th>Size</th>
                <th>Price</th>
                <th>Tick</th>
            </tr>
            </thead>
            <tbody>
            {
                orderData.buyOrders.map(order =>
                    <tr>
                        <td>...</td>
                        <td>{order.versionTime}</td>
                        <td>{order.size}</td>
                        <td>{order.price}</td>
                        <td className="text-center"><i className="arrow up"></i></td>
                    </tr>)
            }
            </tbody>
        </Table>
        </React.Fragment>
    )
}

const mapStateToProps = state => {
    return {
        orderData: state.order,
        tradeData: state.trades
    }
}

export default connect(mapStateToProps)(BuyOrders);