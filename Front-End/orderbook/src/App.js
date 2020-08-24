import React from 'react';
import './App.css';

import { BrowserRouter as Router, Route, Switch, Redirect} from "react-router-dom";

//Pages
import MainPage from "./pages/MainPage";
import NotFoundPage from "./pages/NotFoundPage";

function App(){
    return (
    <Router>
      <Switch>

      {/*Home Page */}
      <Route exact path="/" component={MainPage}/>

      {/* Only runs if they type in a weird url manually */}
      <Route exact path="/404" component={NotFoundPage}/>
      <Redirect to="/404" />
      </Switch>
    </Router>
    )
}

export default App;
