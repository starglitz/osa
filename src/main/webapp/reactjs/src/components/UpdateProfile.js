import React, {useEffect, useState} from "react";
import {AuthenticationService} from "../services/AuthenticationService";
import {ArticlesService} from "../services/ArticlesService";
import {SellersService} from "../services/SellersService";
import {CustomersService} from "../services/CustomersService";
import {Button} from "@material-ui/core";
import {useHistory} from "react-router-dom";
import {Nav} from "react-bootstrap";
import NavigationBar from "./NavigationBar";


const UpdateProfile = () => {

    const [user, setUser] = useState({});

    const history = useHistory();

    const [formated, setFormated] = useState("");

    useEffect(() => {
        if(AuthenticationService.getRole() == 'ROLE_CUSTOMER') {
            fetchCustomer()
        }
        else if(AuthenticationService.getRole() == 'ROLE_SELLER') {
            fetchSeller()
        }
    }, []);


    const createNew = () => {
        history.push("/createArticle")
    }

    const profile = () => {
        history.push("/profile")
    }

    const listOfUsers = () => {
        history.push("/users")
    }

    const logout = () => {
        AuthenticationService.logout();
    }

    const notDeliveredOrders = () => {
        history.push("/notDeliveredOrders")
    }


    async function fetchSeller() {
        try {
            const response = await SellersService.getMe();
           // setUser(response.data);
            let user = response.data;
            user.since = new Date(response.data.since)
            setUser(response.data)
            console.log(response.data);

            let formatted = response.data.since.toISOString().split('T')[0]
            console.log(formatted)
            setFormated(formatted);
        } catch (error) {
            console.error(`Error loading your profile !: ${error}`);
        }
    }


    async function fetchCustomer() {
        try {
            const response = await CustomersService.getMe();
            setUser(response.data);
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading your profile !: ${error}`);
        }
    }

    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value;
        setUser({ ...user, [name]: val });
    };

    async function edit() {
        if(validate()) {
            try {
                if(AuthenticationService.getRole() == 'ROLE_CUSTOMER') {
                    console.log(user)
                    await CustomersService.editCustomer(user.id, user)
                }
                else if(AuthenticationService.getRole() == 'ROLE_SELLER') {
                    await SellersService.editSeller(user.id, user)
                }
                history.push("/home")
            } catch (error) {
                console.error(`Error ocurred while updating your info: ${error}`);
            }
        }
    }

    const validate = () => {
        let ok = true;
        if(user.name === "" || user.surname === "" || user.password === ""
        || user.username === "" || user.address == "") {
            alert("Make sure all fields are filled!")
            ok = false;
        }

        if(AuthenticationService.getRole() == 'ROLE_SELLER') {
            if(user.email === "" || user.sellerName === "" || user.since === "") {
                if(ok) {
                    alert("Make sure all fields are filled!")
                }
                ok = false;
            }
        }
        return ok
    }


    const passChange = () => {
        console.log("userrr")
        console.log(user)
        history.push({
            pathname: '/changePassword',
            state: user
        })
    }

    let navContent;

    if(AuthenticationService.getRole() === "ROLE_CUSTOMER") {
        navContent = <>
            <Nav.Link onClick={notDeliveredOrders}>My orders</Nav.Link>
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

    let sellerData = <></>

    if(AuthenticationService.getRole() == 'ROLE_SELLER') {
        sellerData = <>
            <label htmlFor="surname" className="label-register">Email:</label>
            <input id="surname" type="text" placeholder="enter your surname here"
                   defaultValue={user.email}
                   className="input-register" onChange={handleFormInputChange("email")}/>

            <label htmlFor="address" className="label-register">Seller name:</label>
            <input id="address" type="text" placeholder="enter your address here"
                   defaultValue={user.sellerName}
                   className="input-register" onChange={handleFormInputChange("sellerName")}/>

            <label htmlFor="username" className="label-register">Since:</label>
            <input id="username" type="date"
                   value={formated}
                   className="input-register" onChange={handleFormInputChange("since")}/>
        </>
    }

    let sameData = <>


        <div style={{margin: '0 auto', display: 'flex',
            justifyContent: 'center'}}>

            <div className="register-form">
                <h1 style={{margin:'20px', marginBottom:'40px'}}>My profile</h1>

                <label htmlFor="name" className="label-register">Name:</label>
                <input defaultValue={user.name}  id="name" type="text" placeholder="enter your name here"
                       onChange={handleFormInputChange("name")} className="input-register"/>

                <label htmlFor="surname" className="label-register">Surname:</label>
                <input  id="surname" type="text" placeholder="enter your surname here"
                        defaultValue={user.surname}
                        className="input-register" onChange={handleFormInputChange("surname")}/>

                <label htmlFor="address" className="label-register">Address:</label>
                <input  id="address" type="text" placeholder="enter your address here"
                        defaultValue={user.address}
                        className="input-register" onChange={handleFormInputChange("address")}/>

                <label htmlFor="username" className="label-register">Username:</label>
                <input id="username" type="text" placeholder="enter your username here"
                       defaultValue={user.username}
                       className="input-register" onChange={handleFormInputChange("username")}/>

                {sellerData}


                <br/> <br/>
                <Button variant="contained" onClick={edit}>Edit</Button>

                <Button style={{marginLeft:"30px"}} onClick={passChange} variant="contained" color="primary">Change password</Button>

                       {/*<div className="passwordChange">*/}

                       {/*    <label htmlFor="password" className="label-register"> Old password:</label>*/}
                       {/*    <input name="password" id="password" type="password" placeholder="enter old password here"*/}
                       {/*           maxLength="100" className="input-register" />*/}
                       {/*    <span id="strength">Type Password</span>*/}

                       {/*    <label htmlFor="confirm" className="label-register">New password:</label>*/}
                       {/*    <input  id="confirm" type="password" placeholder="enter new password" className="input-register"/>*/}

                       {/*</div>*/}

            </div>
        </div>
    </>

    return(
        <>
            <NavigationBar>
                <Nav>
                    <Nav.Link eventKey={3} onClick={profile}>
                        Profile
                    </Nav.Link>
                    {navContent}
                </Nav>
            </NavigationBar>
            <div className="updateInfoCard">
            {sameData}
            </div>
        </>
    )
}

export default UpdateProfile;