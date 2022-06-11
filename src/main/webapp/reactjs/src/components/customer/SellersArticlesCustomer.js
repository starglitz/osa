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
import {SellersService} from "../../services/SellersService";
import Comments from "./Comments";

const SellersArticlesCustomer = (props) => {


    const [articles, setArticles] = useState([]);

    const { id } = useParams();

    const location = useLocation();

    const history = useHistory();

    const [orderItems,setOrderItems] = useState([]);

    const [seller, setSeller] = useState({});

     const [total, setTotal] = useState(0);

     const [random, setRandom] = useState(0);

    // let totalPrice = 0;



   // const [total = 0,setTotal] = useState()


   //let total = useRef(0);

    useEffect(() => {
        fetchArticles();
        fetchSeller()
        //console.log(location.state.detail)
        console.log("order items,use effect: " + orderItems)
        console.log("total atm: " + total)
    }, [orderItems]);

    const logout = () => {
        AuthenticationService.logout();
    }

    const notDeliveredOrders = () => {
        history.push("/notDeliveredOrders")
    }


    const updateTotal = () => setTotal((state) => !state);

    const setTotalF = (orderItem) => {
        let price = 0;
        orderItems.forEach(item => price += item.article.price * item.amount);
        setTotal(total + price)};

    async function addToCart(orderItem) {

        try {

            console.log("order item: " + JSON.stringify(orderItem))

             let item_ids = []

            orderItems.forEach(item => item_ids.push(item.article.id));

            if(item_ids.includes(orderItem.article.id)) {
                let itemsModified = orderItems

                for (let i = 0; i < orderItems.length - 1; i++) {
                    if (orderItems[i].article.id === orderItem.article.id) {
                        let itemMod = orderItems[i];
                        itemMod.amount = +itemMod.amount + +orderItem.amount;
                        itemsModified.splice(i, 1);
                        itemsModified.push(itemMod);
                    }
                }
                setOrderItems(itemsModified)
                console.log(itemsModified)
                }

        else {
                setOrderItems(orderItems => [...orderItems, orderItem]);
            }

            console.log("order items now:" + orderItems);

        } catch (error) {
            console.error(`Error loading sellers articles !: ${error}`);
        }

    }


    const buy = () => {

        let price = 0;
        orderItems.forEach(item => price += item.article.price * item.amount);
        setTotal(total + price)

        let item_ids = []

        orderItems.forEach(item => item_ids.push({"amount":item.amount,
            "article":{"id":item.article.id}}));
        console.log(item_ids)
        console.log(orderItems)

        let order = {"archived":false, "anonymous":false, "comment":"",
        "items":orderItems}


        history.push({
            pathname: '/finish',
            state: JSON.stringify(order)
        })


        console.log("am i here twice?")

    }


    const [show, setShow] = useState(false);




    async function fetchArticles() {
      try {
            const response = await ArticlesService.getArticlesBySellerId(location.state.detail);
            setArticles(response.data);
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading sellers articles !: ${error}`);
        }

    }

    async function fetchSeller() {
        try {
            const response = await SellersService.getSeller(location.state.detail)
            setSeller(response.data);
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading seller !: ${error}`);
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
    const deliveredOrders = () => {
        history.push("/deliveredOrders")
    }



    return (
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

            <div style={{textAlign:"left", marginLeft:'40px'}}>
                <br/>
            <h1>Seller: {seller.name}</h1>
            <h2>Average rating: {(Math.round(seller.rating * 100) / 100).toFixed(2)}</h2>
                <br/>
            </div>
            <Button size="large" variant="contained" color="secondary"
                    style={{position:"absolute", right:'100px', top:'100px',padding:'15px'}} onClick={buy}> Finish your order </Button>

            <div className="flex-container" style={{flexWrap:"wrap", width:'90%',margin:'0 auto'}}>
                {articles.map((a) =>
                    <div className="flex-child" style={{margin: '0 auto', marginTop:'30px'}}>
                        <ArticleCardCustomer
                            key={a.id} id={a.id} path={a.path} name={a.name}
                            description={a.description} price={a.price} discounts={a.discounts}
                            addToCart={addToCart} totalF={setTotalF}/>
                    </div>
                )}
            </div>

            <Comments seller_id={location.state.detail}></Comments>


        </>
    )

}

export default SellersArticlesCustomer;