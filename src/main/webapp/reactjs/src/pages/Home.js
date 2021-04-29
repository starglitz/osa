
import Navbar from 'react-bootstrap/Navbar'
import NavigationBar from "../components/NavigationBar";
import {Nav} from "react-bootstrap";
import React from 'react';
import Sellers from "../components/Sellers";
import {AuthenticationService} from "../services/AuthenticationService";
import SellersArticles from "../components/SellersArticles";

const Home = () => {

    const logout = () => {
        AuthenticationService.logout();
    }


    let navContent;

    if(AuthenticationService.getRole() === "ROLE_CUSTOMER") {
        navContent = <>
                <Nav.Link href="#deets">Customer option</Nav.Link>
                <Nav.Link eventKey={2} href="#memes">
                    Dank memes
                </Nav.Link>
                <Nav.Link eventKey={3} onClick={logout}>
                    Log out
                </Nav.Link>
        </>;
    }

    else if(AuthenticationService.getRole() === "ROLE_SELLER") {
        navContent = <>
                <Nav.Link href="#deets">Seller option</Nav.Link>
                <Nav.Link eventKey={2} href="#memes">
                    Dank memes
                </Nav.Link>
                <Nav.Link eventKey={3} onClick={logout}>
                    Log out
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