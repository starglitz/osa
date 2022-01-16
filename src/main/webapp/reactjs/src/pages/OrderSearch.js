import debounce from "lodash.debounce";
import { useEffect, useState } from "react";
import ArticleCardCustomer from "../components/customer/ArticleCardCustomer";
import NavBar from "../components/NavBar";
import { ArticlesService } from "../services/ArticlesService";
import { OrderService } from "../services/OrderService";

const OrderSearch = () => {
  const [orders, setOrders] = useState([]);
  const [query, setQuery] = useState("");

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

  //   async function addToCart(orderItem) {

  //     try {

  //         console.log("order item: " + JSON.stringify(orderItem))

  //          let item_ids = []

  //         orderItems.forEach(item => item_ids.push(item.article.id));

  //         if(item_ids.includes(orderItem.article.id)) {
  //             let itemsModified = orderItems

  //             for (let i = 0; i < orderItems.length - 1; i++) {
  //                 if (orderItems[i].article.id === orderItem.article.id) {
  //                     let itemMod = orderItems[i];
  //                     itemMod.amount = +itemMod.amount + +orderItem.amount;
  //                     itemsModified.splice(i, 1);
  //                     itemsModified.push(itemMod);
  //                 }
  //             }
  //             setOrderItems(itemsModified)
  //             console.log(itemsModified)
  //             }

  //     else {
  //             setOrderItems(orderItems => [...orderItems, orderItem]);
  //         }

  //         console.log("order items now:" + orderItems);

  //     } catch (error) {
  //         console.error(`Error loading sellers articles !: ${error}`);
  //     }

  // }

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

      <table className="styled-table width-80">
        <thead>
          <tr>
            <td>Order ID</td>
            <td>Time</td>
            <td>Order items</td>
            <td>Comment</td>
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
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default OrderSearch;
