import './App.css';
import LoginForm from "./components/LoginForm";
import Home from "../src/pages/Home"
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import {PrivateRoute} from "./services/PrivateRoute";
import RegisterForm from "./components/RegisterForm";
import UpdateArticle from "./components/UpdateArticle";
import CreateArticle from "./components/CreateArticle";

function App() {
  return (
    <div className="App">

      <Router>
        <Switch>
          {/*<Route path="/register" exact component={RegisterLayout}/>*/}
          <Route path="/" exact component={LoginForm}/>
          {/*<Route path="/home" exact component={Home}/>*/}
            <Route path="/register" exact component={RegisterForm}/>
          <Route path="/test" exact component={CreateArticle}/>

            <PrivateRoute
                exact
                path="/home"
                component={Home}
                roles={["ROLE_CUSTOMER", "ROLE_SELLER"]}
            />

          <PrivateRoute
              exact
              path="/updateArticle/:id"
              component={UpdateArticle}
              roles={["ROLE_ADMIN", "ROLE_SELLER"]}
          />

        </Switch>
      </Router>
    </div>
  );
}

export default App;
