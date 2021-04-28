
import Navbar from 'react-bootstrap/Navbar'
import NavigationBar from "../components/NavigationBar";
import {Nav} from "react-bootstrap";
import React from 'react';
import Sellers from "../components/Sellers";

const Home = () => {

return (
    <>
    <NavigationBar>
    <Nav>
        <Nav.Link href="#deets">More deets</Nav.Link>
        <Nav.Link eventKey={2} href="#memes">
            Dank memes
        </Nav.Link>
    </Nav>
    </NavigationBar>

        <h1 style={{marginTop:'30px'}}>
            List of available sellers:
        </h1>

        <Sellers/>
    </>
    )
}

export default Home;