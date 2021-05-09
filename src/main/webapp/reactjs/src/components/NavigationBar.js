import {Navbar} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';


const NavigationBar = (props) => {
    return (
<>
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
        <Navbar.Brand href="/home">Web shop</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
            {props.children}
        </Navbar.Collapse>
    </Navbar>
    </>
    )
}


export default NavigationBar;