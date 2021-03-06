import "./App.css";
import LoginForm from "./components/LoginForm";
import Home from "../src/pages/Home";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { PrivateRoute } from "./services/PrivateRoute";
import RegisterForm from "./components/RegisterForm";
import UpdateArticle from "./components/UpdateArticle";
import CreateArticle from "./components/CreateArticle";
import SellersArticlesCustomer from "./components/customer/SellersArticlesCustomer";
import FinishOrder from "./components/customer/FinishOrder";
import UsersTable from "./components/admin/UsersTable";
import UpdateProfile from "./components/UpdateProfile";
import ChangePassword from "./components/ChangePassword";
import NotDeliveredOrders from "./components/customer/NotDeliveredOrders";
import DeliveredOrders from "./components/customer/DeliveredOrders";
import CommentOrder from "./components/customer/CommentOrder";
import AddDiscount from "./components/seller/AddDiscount";
import DiscountsTable from "./components/seller/DiscountsTable";
import UpdateDiscount from "./components/seller/UpdateDiscount";
import ArticlesSearch from "./pages/ArticlesSearch";
import OrderSearch from "./pages/OrderSearch";

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact component={LoginForm} />

          <Route path="/register" exact component={RegisterForm} />

          <PrivateRoute
            exact
            path="/articlesSearch"
            component={ArticlesSearch}
            roles={["ROLE_CUSTOMER", "ROLE_SELLER", "ROLE_ADMIN"]}
          ></PrivateRoute>

          <PrivateRoute
            exact
            path="/ordersSearch"
            component={OrderSearch}
            roles={["ROLE_CUSTOMER", "ROLE_SELLER", "ROLE_ADMIN"]}
          ></PrivateRoute>

          <PrivateRoute
            exact
            path="/home"
            component={Home}
            roles={["ROLE_CUSTOMER", "ROLE_SELLER", "ROLE_ADMIN"]}
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
            roles={["ROLE_SELLER"]}
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
            roles={["ROLE_SELLER"]}
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

          <PrivateRoute
            exact
            path="/notDeliveredOrders"
            component={NotDeliveredOrders}
            roles={["ROLE_CUSTOMER"]}
          />

          <PrivateRoute
            exact
            path="/deliveredOrders"
            component={DeliveredOrders}
            roles={["ROLE_CUSTOMER"]}
          />

          <PrivateRoute
            exact
            path="/comment"
            component={CommentOrder}
            roles={["ROLE_CUSTOMER"]}
          />

          <PrivateRoute
            exact
            path="/addDiscount"
            component={AddDiscount}
            roles={["ROLE_SELLER"]}
          />

          <PrivateRoute
            exact
            path="/allDiscounts"
            component={DiscountsTable}
            roles={["ROLE_SELLER"]}
          />

          <PrivateRoute
            exact
            path="/discounts/edit/:id"
            component={UpdateDiscount}
            roles={["ROLE_SELLER"]}
          />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
