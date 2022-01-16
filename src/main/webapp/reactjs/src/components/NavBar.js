import NavigationBar from "./NavigationBar";
import { Nav } from "react-bootstrap";
import React from "react";
import { AuthenticationService } from "../services/AuthenticationService";
import { useHistory } from "react-router-dom";

const NavBar = () => {
  const logout = () => {
    AuthenticationService.logout();
  };

  console.log(AuthenticationService.getRole());

  let navContent;

  const history = useHistory();

  const createNew = () => {
    history.push("/createArticle");
  };

  const profile = () => {
    history.push("/profile");
  };

  const listOfUsers = () => {
    history.push("/users");
  };

  const notDeliveredOrders = () => {
    history.push("/notDeliveredOrders");
  };

  const deliveredOrders = () => {
    history.push("/deliveredOrders");
  };

  const addDiscount = () => {
    history.push("/addDiscount");
  };

  const allDiscounts = () => {
    history.push("/allDiscounts");
  };

  const articlesSearch = () => {
    history.push("/articlesSearch");
  };

  if (AuthenticationService.getRole() === "ROLE_CUSTOMER") {
    navContent = (
      <>
        <Nav.Link onClick={notDeliveredOrders}>My orders</Nav.Link>
        <Nav.Link onClick={deliveredOrders}>Comment on order</Nav.Link>
        <Nav.Link eventKey={3} onClick={logout}>
          Log out
        </Nav.Link>
      </>
    );
  } else if (AuthenticationService.getRole() === "ROLE_SELLER") {
    navContent = (
      <>
        <Nav.Link href="#deets">Seller option</Nav.Link>

        <Nav.Link eventKey={3} onClick={logout}>
          Log out
        </Nav.Link>

        <Nav.Link eventKey={3} onClick={createNew}>
          Add new article
        </Nav.Link>

        <Nav.Link eventKey={3} onClick={addDiscount}>
          Add new discount
        </Nav.Link>

        <Nav.Link eventKey={3} onClick={allDiscounts}>
          Manage discounts
        </Nav.Link>
      </>
    );
  } else if (AuthenticationService.getRole() === "ROLE_ADMIN") {
    navContent = (
      <>
        <Nav.Link href="#deets">Admin option</Nav.Link>

        <Nav.Link eventKey={3} onClick={logout}>
          Log out
        </Nav.Link>

        <Nav.Link eventKey={3} onClick={listOfUsers}>
          Manage users
        </Nav.Link>
      </>
    );
  }

  return (
    <div>
      <NavigationBar>
        <Nav>
          <Nav.Link eventKey={3} onClick={profile}>
            Profile
          </Nav.Link>
          <Nav.Link onClick={articlesSearch}>Articles search</Nav.Link>
          {navContent}
        </Nav>
      </NavigationBar>
    </div>
  );
};

export default NavBar;
