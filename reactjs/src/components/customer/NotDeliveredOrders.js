import React, { useEffect, useState } from "react";
import { ArticlesService } from "../../services/ArticlesService";
import { OrderService } from "../../services/OrderService";
import NavigationBar from "../NavigationBar";
import { Nav } from "react-bootstrap";
import Button from "@material-ui/core/Button";
import { AuthenticationService } from "../../services/AuthenticationService";
import { setRef } from "@material-ui/core";
import { useHistory } from "react-router-dom";

const NotDeliveredOrders = () => {
  const [orders, setOrders] = useState([]);
  const [refresh, setRefresh] = useState(0);

  const history = useHistory();

  useEffect(() => {
    fetchOrders();
  }, [refresh]);

  async function fetchOrders() {
    try {
      const response = await OrderService.getOrdersByCurrentCustomer();
      setOrders(response.data.filter((order) => order.delivered === false));
    } catch (error) {
      console.error(`Error loading my orders !: ${error}`);
    }
  }

  const logout = () => {
    AuthenticationService.logout();
  };

  async function delivered(order) {
    order.delivered = true;
    try {
      await OrderService.setDelivered(order.id, order);
      setRefresh(refresh + 1);
    } catch (error) {
      console.error(`Error ocurred while updating the order: ${error}`);
    }
  }

  const notDeliveredOrders = () => {
    history.push("/notDeliveredOrders");
  };

  const deliveredOrders = () => {
    history.push("/deliveredOrders");
  };

  return (
    <>
      <NavigationBar>
        <Nav>
          <Nav.Link onClick={notDeliveredOrders}>My orders</Nav.Link>
          <Nav.Link onClick={deliveredOrders}>Comment on order</Nav.Link>
          <Nav.Link eventKey={3} onClick={logout}>
            Log out
          </Nav.Link>
        </Nav>
      </NavigationBar>

      <table className="styled-table">
        <thead>
          <tr>
            <td>Order ID</td>
            <td>Time</td>
            <td>Order items</td>
            <td>Check orders that have been delivered</td>
          </tr>
        </thead>
        <tbody>
          {orders
            .filter((order) => order.delivered === false)
            .map((order) => (
              <tr>
                <td>{order.id}</td>
                <td>{order.time}</td>
                <td>
                  {order.items.map((item) => (
                    <p>
                      {item.article.name} x{item.amount}{" "}
                    </p>
                  ))}
                </td>
                <td>
                  <Button variant="contained" onClick={() => delivered(order)}>
                    Check as delivered
                  </Button>
                </td>
              </tr>
            ))}
        </tbody>
      </table>
    </>
  );
};

export default NotDeliveredOrders;
