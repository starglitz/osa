import React, {useEffect, useState} from "react";
import {OrderService} from "../../services/OrderService";
import {AuthenticationService} from "../../services/AuthenticationService";
import NavigationBar from "../NavigationBar";
import {Nav} from "react-bootstrap";
import Button from "@material-ui/core/Button";
import {useHistory} from "react-router-dom";

const DeliveredOrders = () => {

    const [orders, setOrders] = useState([]);

    const history = useHistory();


    useEffect(() => {
        fetchOrders();
    }, []);


    async function fetchOrders() {
        try {
            const response = await OrderService.getOrdersByCurrentCustomer();
            setOrders(response.data.filter((order) => order.delivered === true
                && (order.comment == null || order.comment == "")));
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading my orders !: ${error}`);
        }
    }


    const logout = () => {
        AuthenticationService.logout();
    }

    const notDeliveredOrders = () => {
        history.push("/notDeliveredOrders")
    }

    const deliveredOrders = () => {
        history.push("/deliveredOrders")
    }

    const comment = (order) => {
        console.log(order);
    }

    return(
        <>
            <NavigationBar>
                <Nav>
                    <Nav.Link onClick={notDeliveredOrders}>My orders</Nav.Link>
                    <Nav.Link onClick={deliveredOrders}>Comment on order</Nav.Link>
                    {/*<Nav.Link eventKey={2} href="#memes">*/}
                    {/*    Dank memes*/}
                    {/*</Nav.Link>*/}
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
                    <td>Comment</td>
                </tr>
                </thead>
                <tbody>

                {orders.filter(order => order.delivered === true
                    && (order.comment == null || order.comment == "")).map((order) =>

                    <tr>
                        <td>{order.id}</td>
                        <td>{order.time}</td>
                        <td>
                            {order.items.map((item) => <p>{item.article.name} x{item.amount} </p> )}
                        </td>
                        <td><Button variant="contained" onClick={() => comment(order)}>Leave a comment</Button></td>
                    </tr>

                )}
                </tbody>

            </table>
        </>
    )
}

export default DeliveredOrders;