import {useEffect, useState} from "react";
import {OrderService} from "../services/OrderService";
import {useLocation} from "react-router-dom";
import SellerCard from "./SellerCard";


const Comments = (props) => {

   const [orders, setOrders] = useState([]);

    const location = useLocation();

    useEffect(() => {
        fetchOrders();
    }, []);


    async function fetchOrders() {
        try {
            console.log("seller id: " + props.seller_id)
            const response = await OrderService.getBySellerId(props.seller_id);
            setOrders(response.data.filter((order) => order.delivered == true && (order.comment != null)));
            console.log(orders);
        } catch (error) {
            console.error(`Error loading my orders !: ${error}`);
        }
    }

    return (
        <>
            <br/>
            <br/>
            <h2>Customer reviews</h2>

            {orders.map((order) =>
                <div className="commentContainer">
                    {order.anonymous? <h3>Anonymous rated his order {order.rating}</h3>
                        : <h3>{order.customer.name} rated his order {order.rating}</h3>}
                        <br/>
                        <h5>{order.comment}</h5>
                </div>
            )}
            <br/>
            <br/>
        </>
    )
}

export default Comments;