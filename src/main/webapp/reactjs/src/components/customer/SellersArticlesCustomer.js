import React, {useEffect, useRef, useState} from "react";
import {ArticlesService} from "../../services/ArticlesService";
import ArticleCardSeller from "../seller/ArticleCardSeller";
import ArticleCardCustomer from "./ArticleCardCustomer";
import {useHistory, useLocation, useParams} from "react-router-dom";
import {Modal, Nav} from "react-bootstrap";
import {AuthenticationService} from "../../services/AuthenticationService";
import NavigationBar from "../NavigationBar";
import Button from "@material-ui/core/Button";
import {OrderService} from "../../services/OrderService";
import {forEach} from "react-bootstrap/ElementChildren";
import FinishOrder from "./FinishOrder";

const SellersArticlesCustomer = (props) => {


    const [articles, setArticles] = useState([]);

    const { id } = useParams();

    const location = useLocation();

    const history = useHistory();

    const [orderItems,setOrderItems] = useState([]);

     const [total, setTotal] = useState(0);

     const [random, setRandom] = useState(0);

    // let totalPrice = 0;



   // const [total = 0,setTotal] = useState()


   //let total = useRef(0);

    useEffect(() => {
        fetchArticles();
        //console.log(location.state.detail)
        console.log("order items,use effect: " + orderItems)
        console.log("total atm: " + total)
    }, [orderItems]);

    const logout = () => {
        AuthenticationService.logout();
    }

    const updateTotal = () => setTotal((state) => !state);

    const setTotalF = (orderItem) => {
        let price = 0;
        orderItems.forEach(item => price += item.article.price * item.amount);
        setTotal(total + price)};

    async function addToCart(orderItem) {

        try {
            // const response = await ArticlesService.getArticle(id);
            // //setArticles(response.data);
            // let selectedArticle = response.data;
            // let orderItem = {article: selectedArticle, amount: amount}
            console.log("order item: " + JSON.stringify(orderItem))

            //JSON.stringify(orderItem)
            setOrderItems(orderItems => [...orderItems, orderItem]);


            // let price = 0;
            // orderItems.forEach(item => price += item.article.price * item.amount);





            //totalPrice = total + price;

            // let totalNow = setTotal((state) => {
            //      // "React is awesome!"
            //
            //     return state;
            // })
            //
            // setTotal(totalNow);

            //total.current = total.current + price;
            console.log("order items now:" + orderItems);

        } catch (error) {
            console.error(`Error loading sellers articles !: ${error}`);
        }

    }
    //
    // setTotal((state) => {
    //     console.log(state); // "React is awesome!"
    //
    //     return state;
    // })

    const buy = () => {

        let price = 0;
        orderItems.forEach(item => price += item.article.price * item.amount);
        setTotal(total + price)
        //totalPrice = total + price;

        let item_ids = []

        orderItems.forEach(item => item_ids.push({"amount":item.amount,
            "article":{"id":item.article.id}}));
        console.log(item_ids)
        console.log(orderItems)

        let order = {"archived":false, "anonymous":false, "comment":"",
        "items":orderItems}


        history.push({
            pathname: '/finish',
            state: JSON.stringify(order) // your data array of objects
        })

        //console.log(order)
        //JSON.stringify({ items: orderItems });
        OrderService.addOrder(order);
        //handleClose()
        //history.push("/home")
    }


    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);



    async function fetchArticles() {
      try {
            const response = await ArticlesService.getArticlesBySellerId(location.state.detail);
            setArticles(response.data);
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading sellers articles !: ${error}`);
        }

    }

    let modal = <></>

    const renderModal = () => {
        setRandom(random+1);
        modal = <FinishOrder buy={buy} show={true} hideModal={hideModal} total={total}/>
    }

    const hideModal = () => {
        setRandom(random+1);
        modal = <></>
    }


    return (
        <>
            <NavigationBar>
                <Nav>
            <Nav.Link href="#deets">Customer option</Nav.Link>
            {/*<Nav.Link eventKey={2} href="#memes">*/}
            {/*    Dank memes*/}
            {/*</Nav.Link>*/}
            <Nav.Link eventKey={3} onClick={logout}>
                Log out
            </Nav.Link>

                </Nav>
            </NavigationBar>

            <h1>Available articles:</h1>

            <Button onClick={buy}> Finish your order </Button>

            <div className="flex-container">
                {articles.map((a) =>
                    <div className="flex-child">
                        <ArticleCardCustomer
                            key={a.id} id={a.id} path={a.path} name={a.name}
                            description={a.description} price={a.price} addToCart={addToCart} totalF={setTotalF}/>
                    </div>
                )}
            </div>

            {/*{modal}*/}

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Finish your order</Modal.Title>
                </Modal.Header>
                <Modal.Body>Total price: {total}</Modal.Body>
                <Modal.Footer>
                    <Button variant="contained" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="contained" onClick={buy}>
                        Buy
                    </Button>
                </Modal.Footer>
            </Modal>

        </>
    )

}

export default SellersArticlesCustomer;