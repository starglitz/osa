import React, {useEffect, useState} from "react";
import {ArticlesService} from "../../services/ArticlesService";
import ArticleCardSeller from "../seller/ArticleCardSeller";
import ArticleCardCustomer from "./ArticleCardCustomer";
import {useLocation, useParams} from "react-router-dom";
import {Nav} from "react-bootstrap";
import {AuthenticationService} from "../../services/AuthenticationService";
import NavigationBar from "../NavigationBar";
import Button from "@material-ui/core/Button";
import {OrderService} from "../../services/OrderService";
import {forEach} from "react-bootstrap/ElementChildren";

const SellersArticlesCustomer = (props) => {


    const [articles, setArticles] = useState([]);

    const { id } = useParams();

    const location = useLocation();

    const [orderItems,setOrderItems] = useState([]);

    useEffect(() => {
        fetchArticles();
        //console.log(location.state.detail)
        console.log("order items,use effect: " + orderItems)
    }, [orderItems]);

    const logout = () => {
        AuthenticationService.logout();
    }



    async function addToCart(orderItem) {

        try {
            // const response = await ArticlesService.getArticle(id);
            // //setArticles(response.data);
            // let selectedArticle = response.data;
            // let orderItem = {article: selectedArticle, amount: amount}
            console.log("order item: " + JSON.stringify(orderItem))

            //JSON.stringify(orderItem)
            setOrderItems(orderItems => [...orderItems, orderItem]);

            console.log("order items now:" + orderItems);

        } catch (error) {
            console.error(`Error loading sellers articles !: ${error}`);
        }

    }

    const buy = () => {

        let item_ids = []

        orderItems.forEach(item => item_ids.push({"amount":item.amount,
            "article":{"id":item.article.id}}));
        console.log(item_ids)
        console.log(orderItems)

        let order = {"archived":false, "anonymous":false, "comment":"",
        "items":item_ids}

        //console.log(order)
        //JSON.stringify({ items: orderItems });
        OrderService.addOrder(order);
    }




    async function fetchArticles() {
      try {
            const response = await ArticlesService.getArticlesBySellerId(location.state.detail);
            setArticles(response.data);
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading sellers articles !: ${error}`);
        }

    }


    // async function deleteArticle(id) {
    //     try {
    //         await ArticlesService.deleteArticle(id);
    //
    //         // Za novu vrednost liste zadataka uzima se prethodna lista, filtrirana tako da ne sadrži obrisani zatak
    //         setArticles((articles) => articles.filter((article) => article.id !== id));
    //     } catch (error) {
    //         console.error(`Greška prilikom brisanja zadataka ${id}: ${error}`);
    //     }
    // }

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

            <Button onClick={buy}> Buy selected articles </Button>

            <div className="flex-container">
                {articles.map((a) =>
                    <div className="flex-child">
                        <ArticleCardCustomer
                            key={a.id} id={a.id} path={a.path} name={a.name}
                            description={a.description} price={a.price} addToCart={addToCart}/>
                    </div>
                )}
            </div>
        </>
    )

}

export default SellersArticlesCustomer;