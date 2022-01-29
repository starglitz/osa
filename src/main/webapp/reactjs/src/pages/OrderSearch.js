import { Button } from "@material-ui/core";
import debounce from "lodash.debounce";
import { useEffect, useState } from "react";
import ArticleCardCustomer from "../components/customer/ArticleCardCustomer";
import NavBar from "../components/NavBar";
import { ArticlesService } from "../services/ArticlesService";
import { OrderService } from "../services/OrderService";

const OrderSearch = () => {
  const [orders, setOrders] = useState([]);
  const [query, setQuery] = useState("");

  const [from, setFrom] = useState(0);
  const [to, setTo] = useState(999999);

  useEffect(() => {
    fetchOrders();
  }, [query]);

  async function fetchOrders() {
    const response = await OrderService.getAll(query);
    setOrders(response.data);
    console.log(response.data);
  }

  const debouncedFetchOrders = debounce((query) => {
    setQuery(query);
  }, 2000);

  const onSearchInputChange = (e) => {
    debouncedFetchOrders(e.target.value);
  };

  async function fetchOrdersByRange() {
    const response = await OrderService.getByRange(from, to);
    setOrders(response.data);
  }

  const reset = () => {
    setQuery("");
    setFrom(0);
    setTo(999999);
    fetchOrders();
  };

  return (
    <div>
      <NavBar></NavBar>
      <br></br>
      <label for="articleName" className="margin-right margin-bottom">
        Search all orders by comment:{" "}
      </label>
      <input
        className="margin-bottom"
        type="text"
        name="orderComment"
        onChange={onSearchInputChange}
      ></input>

      <br></br>
      <label for="from" className="margin-right margin-bottom">
        Search all orders by article range:
      </label>
      <input
        className="margin-bottom margin-right"
        type="number"
        name="from"
        onChange={(event) => setFrom(event.target.value)}
      ></input>

      <input
        className="margin-bottom margin-right"
        type="number"
        name="to"
        onChange={(event) => setTo(event.target.value)}
      ></input>

      <Button variant="contained" onClick={fetchOrdersByRange}>
        Search by rating range
      </Button>

      <br></br>
      <Button variant="contained" color="error" onClick={reset}>
        {" "}
        Reset all{" "}
      </Button>

      <table className="styled-table width-80">
        <thead>
          <tr>
            <td>Order ID</td>
            <td>Time</td>
            <td>Order items</td>
            <td>Comment</td>
            <td>Rating</td>
          </tr>
        </thead>
        <tbody>
          {orders.map((order) => (
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
                {order.comment
                  ? (order.anonymous
                      ? "Amonymous: "
                      : order.customer.user.name + ": ") +
                    " " +
                    order.comment
                  : "Customer hasn't left a comment yet"}
              </td>
              <td>{order.rating ? order.rating : "Not rated yet"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default OrderSearch;
