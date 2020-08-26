import { combineReducers} from "redux";
import stockReducer from "./stock/stockReducer";
import userReducer from "./user/userReducer";
import partyReducer from "./party/partyReducer";
import tradeReducer from "./trade/tradeReducer";
import orderReducer from "./order/orderReducer";

const rootReducer = combineReducers({
    stock : stockReducer,
    user: userReducer,
    party: partyReducer,
    trade: tradeReducer,
    order: orderReducer
})

export default rootReducer;