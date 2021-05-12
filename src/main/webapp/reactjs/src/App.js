import './App.css';
import LoginForm from "./components/LoginForm";
import Home from "../src/pages/Home"
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import {PrivateRoute} from "./services/PrivateRoute";
import RegisterForm from "./components/RegisterForm";
import UpdateArticle from "./components/UpdateArticle";
import CreateArticle from "./components/CreateArticle";
import SellersArticlesCustomer from "./components/customer/SellersArticlesCustomer";
import FinishOrder from "./components/customer/FinishOrder";
import UsersTable from "./components/admin/UsersTable";
import UpdateProfile from "./components/UpdateProfile";
import ChangePassword from "./components/ChangePassword";

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

          <PrivateRoute
              exact
              path="/finish"
              component={FinishOrder}
              roles={["ROLE_CUSTOMER"]}
          />

          <PrivateRoute
              exact
              path="/users"
              component={UsersTable}
              roles={["ROLE_ADMIN"]}
          />

          <PrivateRoute
              exact
              path="/createArticle"
              component={CreateArticle}
              roles={["ROLE_CUSTOMER", "ROLE_SELLER"]}
          />

          <PrivateRoute
              exact
              path="/profile"
              component={UpdateProfile}
              roles={["ROLE_CUSTOMER", "ROLE_SELLER"]}
          />

          <PrivateRoute
              exact
              path="/updateArticle/:id"
              component={UpdateArticle}
              roles={["ROLE_ADMIN", "ROLE_SELLER"]}
          />

          <PrivateRoute
              exact
              path="/sellersArticles"
              component={SellersArticlesCustomer}
              roles={["ROLE_ADMIN", "ROLE_CUSTOMER"]}
          />

          <PrivateRoute
              exact
              path="/changePassword"
              component={ChangePassword}
              roles={["ROLE_SELLER", "ROLE_CUSTOMER"]}
          />

        </Switch>
      </Router>
    </div>
  );
}

export default App;
