import NavigationBar from "../components/NavigationBar";
import {Nav} from "react-bootstrap";
import React from 'react';
import Sellers from "../components/Sellers";
import {AuthenticationService} from "../services/AuthenticationService";
import SellersArticles from "../components/seller/SellersArticles";
import {useHistory} from "react-router-dom";

const Home = () => {

    const logout = () => {
        AuthenticationService.logout();
    }


    let navContent;

    const history = useHistory()

    const createNew = () => {
        history.push("/createArticle")
    }

    const listOfUsers = () => {
        history.push("/users")
    }

    if(AuthenticationService.getRole() === "ROLE_CUSTOMER") {
        navContent = <>
                <Nav.Link href="#deets">Customer option</Nav.Link>
                {/*<Nav.Link eventKey={2} href="#memes">*/}
                {/*    Dank memes*/}
                {/*</Nav.Link>*/}
                <Nav.Link eventKey={3} onClick={logout}>
                    Log out
                </Nav.Link>
        </>;
    }


    else if(AuthenticationService.getRole() === "ROLE_SELLER") {
        navContent = <>
                <Nav.Link href="#deets">Seller option</Nav.Link>
                {/*<Nav.Link eventKey={2} href="#memes">*/}
                {/*    Dank memes*/}
                {/*</Nav.Link>*/}
                <Nav.Link eventKey={3} onClick={logout}>
                    Log out
                </Nav.Link>

            <Nav.Link eventKey={3} onClick={createNew}>
                Add new article
            </Nav.Link>
        </>
    }

    else if(AuthenticationService.getRole() === "ROLE_ADMIN") {
        navContent = <>
            <Nav.Link href="#deets">Admin option</Nav.Link>
            {/*<Nav.Link eventKey={2} href="#memes">*/}
            {/*    Dank memes*/}
            {/*</Nav.Link>*/}
            <Nav.Link eventKey={3} onClick={logout}>
                Log out
            </Nav.Link>

            <Nav.Link eventKey={3} onClick={listOfUsers}>
                Manage users
            </Nav.Link>
        </>
    }


    let pageContent;


    if(AuthenticationService.getRole() === "ROLE_CUSTOMER") {
        pageContent = <>
            <h1 style={{marginTop:'30px'}}>
                List of available sellers:
            </h1>

            <Sellers/>
        </>
    }

    else if(AuthenticationService.getRole() === "ROLE_SELLER") {
        pageContent = <>
            <h1>List of your articles: </h1>
            <SellersArticles/>
        </>
    }


return (
    <>
    <NavigationBar>
    <Nav>
        {navContent}
    </Nav>
    </NavigationBar>
        {pageContent}
    </>
    )
}

export default Home;