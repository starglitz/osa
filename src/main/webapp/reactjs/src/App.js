import logo from './logo.svg';
import './App.css';
import LoginForm from "./components/LoginForm";
import Home from "../src/pages/Home"
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import {PrivateRoute} from "./services/PrivateRoute";
import Sellers from "./components/Sellers";
import RegisterForm from "./components/RegisterForm";

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          {/*<Route path="/register" exact component={RegisterLayout}/>*/}
          <Route path="/" exact component={LoginForm}/>
          {/*<Route path="/home" exact component={Home}/>*/}
            <Route path="/register" exact component={RegisterForm}/>

            <PrivateRoute
                exact
                path="/home"
                component={Home}
                roles={["ROLE_CUSTOMER", "ROLE_SELLER"]}
            />

        </Switch>
      </Router>
    </div>
  );
}

export default App;
