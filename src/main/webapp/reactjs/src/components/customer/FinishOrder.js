import {Modal} from "react-bootstrap";
import Button from "@material-ui/core/Button";
import React, {useEffect, useState} from "react";
import {useHistory, useLocation} from "react-router-dom";
import ArticleCardCustomer from "./ArticleCardCustomer";
import {OrderService} from "../../services/OrderService";


const FinishOrder = () => {

    // const [show, setShow] = useState(true);
    // const handleClose = () => setShow(false);
    // const handleShow = () => setShow(true);

    const location = useLocation();
    const history = useHistory();

    const [order, setOrder] = useState(JSON.parse(location.state));

    const [total, setTotal] = useState(0)

    const countTotal = () => {
        let price = 0;
        order.items.forEach(item => price += item.article.price * item.amount);
        setTotal(price)
        console.log("price here: " + price)
        console.log("total use eff: " + total)
    }

    useEffect(() => {
        //setOrder(JSON.parse(location.state))

        countTotal()
        console.log(JSON.parse(location.state))
        console.log("here")
        console.log(order.items)
        console.log(location.state.total)
    }, [total]);


    const buy = () => {


        let item_ids = []

        order.items.forEach(item => item_ids.push({"amount":item.amount,
            "article":{"id":item.article.id}}));
        console.log(item_ids)

        let orderPost = {"archived":false, "anonymous":false, "comment":"",
            "items":item_ids}


        //console.log(order)
        //JSON.stringify({ items: orderItems });
        OrderService.addOrder(orderPost);
        //handleClose()
        history.push("/home")
    }



    return (
        <>
            <h1 style={{margin:"20px"}}>Your order: </h1>

            <h2 style={{textAlign:"left", margin:"20px", marginLeft:"40px"}}>Order items: </h2>

            {order.items.map((a) =>
                <div className="orderItem">
                    <p>Name: {a.article.name}</p>
                    <p>Price: {a.article.price}</p>
                    <p>Description: {a.article.description}</p>
                    <p>Amount: {a.amount}</p>
                </div>
            )}

            <h2>Total price: {total}</h2>

            <Button id="buyBtn" onClick={buy} size="large" color="secondary" variant="contained" > BUY SELECTED ITEMS</Button>
        </>
    )
}

export default FinishOrder;