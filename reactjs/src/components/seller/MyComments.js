import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { OrderService } from "../../services/OrderService";
import Button from "@material-ui/core/Button";

const MyComments = () => {
  const [orders, setOrders] = useState([]);
  const [refresh, setRefresh] = useState(0);

  const location = useLocation();

  useEffect(() => {
    fetchOrders();
  }, [refresh]);

  async function fetchOrders() {
    try {
      const response = await OrderService.getOrdersByCurrentSeller();
      setOrders(
        response.data.filter(
          (order) =>
            order.delivered == true &&
            order.comment != null &&
            order.archived == false
        )
      );
    } catch (error) {
      console.error(`Error loading my orders !: ${error}`);
    }
  }

  async function archive(order) {
    order.archived = true;

    try {
      await OrderService.setArchived(order.id, order);
      setRefresh(refresh + 1);
    } catch (error) {
      console.error(`Error ocurred while updating the order: ${error}`);
    }
  }

  return (
    <>
      <br />
      <br />
      <h2>Customer reviews</h2>

      {orders.map((order) => (
        <div className="commentContainer">
          {order.anonymous ? (
            <h3>Anonymous rated his order {order.rating}</h3>
          ) : (
            <h3>
              {order.customer.name} rated his order {order.rating}
            </h3>
          )}
          <br />
          <h5>{order.comment}</h5>

          <div style={{ display: "flex", justifyContent: "flex-end" }}>
            <Button
              variant="contained"
              size="large"
              color="primary"
              onClick={() => archive(order)}
            >
              Archive
            </Button>
          </div>
        </div>
      ))}
      <br />
      <br />
    </>
  );
};

export default MyComments;
