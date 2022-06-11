import { Modal } from "react-bootstrap";
import Button from "@material-ui/core/Button";
import React, { useEffect, useState } from "react";
import { useHistory, useLocation } from "react-router-dom";
import ArticleCardCustomer from "./ArticleCardCustomer";
import { OrderService } from "../../services/OrderService";

const FinishOrder = () => {
  const location = useLocation();
  const history = useHistory();

  const [order, setOrder] = useState(JSON.parse(location.state));

  const [total, setTotal] = useState(0);

  const countTotal = () => {
    let price = 0;

    order.items.forEach(
      (item) =>
        (price +=
          (item.article.price -
            item.article.price * (item.article.discounts * 0.01)) *
          item.amount)
    );

    setTotal(price);
  };

  useEffect(() => {
    countTotal();
  }, [total]);

  const buy = () => {
    if (order.items.length == 0) {
      alert("You havent picked any articles!");
      history.push("/home");
    } else {
      let item_ids = [];

      order.items.forEach((item) =>
        item_ids.push({
          amount: item.amount,
          article: { id: item.article.id },
        })
      );
      console.log(item_ids);

      let orderPost = {
        archived: false,
        anonymous: false,
        comment: "",
        items: item_ids,
      };

      OrderService.addOrder(orderPost);
      history.push("/home");
    }
  };

  return (
    <>
      <h1 style={{ margin: "20px" }}>Your order: </h1>

      <h2 style={{ textAlign: "left", margin: "20px", marginLeft: "40px" }}>
        Order items:{" "}
      </h2>

      {order.items.map((a) => (
        <div className="orderItem">
          <p>Name: {a.article.name}</p>
          <p>
            Price:{" "}
            {a.article.price - a.article.price * (a.article.discounts * 0.01)}
          </p>
          <p>Description: {a.article.description}</p>
          <p>Amount: {a.amount}</p>
        </div>
      ))}

      <h2>Total price: {total}</h2>

      <Button
        id="buyBtn"
        onClick={buy}
        size="large"
        color="secondary"
        variant="contained"
      >
        {" "}
        BUY SELECTED ITEMS
      </Button>
    </>
  );
};

export default FinishOrder;
