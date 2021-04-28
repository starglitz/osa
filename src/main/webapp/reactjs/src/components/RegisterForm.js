import React, {useEffect, useState} from "react";
import {render} from "@testing-library/react";
import {Button} from "@material-ui/core";
import {CustomersService} from "../services/CustomersService";
import {ValidateRegister} from "../services/ValidateRegister";
import {SellersService} from "../services/SellersService";


const RegisterForm = () => {


    const [role, setRole] = useState('customer');
    const [showAlert, setShowAlert] = useState({ success: null, message: "" });

    let sameData = <>
        <div style={{margin: '0 auto', display: 'flex',
            justifyContent: 'center'}}>

            <div className="register-form">

                <label htmlFor="password" className="label-register">Password:</label>
                <input name="password" id="password" type="password" placeholder="enter password here"
                       maxLength="100" onKeyUp={passwordChanged} className="input-register"/>
                <span id="strength">Type Password</span>

                <label htmlFor="confirm" className="label-register">Confirm password:</label>
                <input  id="confirm" type="password" placeholder="confirm password" className="input-register"/>

                <label htmlFor="name" className="label-register">Name:</label>
                <input  id="name" type="text" placeholder="enter your name here" className="input-register"/>

                <label htmlFor="surname" className="label-register">Surname:</label>
                <input  id="surname" type="text" placeholder="enter your surname here" className="input-register"/>

                <label htmlFor="address" className="label-register">Address:</label>
                <input  id="address" type="text" placeholder="enter your address here" className="input-register"/>

                <label htmlFor="username" className="label-register">Username:</label>
                <input id="username" type="text" placeholder="enter your username here" className="input-register"/>

            </div>
        </div>
        </>

    function passwordChanged() {
        var strength = document.getElementById('strength');
        var strongRegex = new RegExp("^(?=.{14,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
        var mediumRegex = new RegExp("^(?=.{10,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
        var enoughRegex = new RegExp("(?=.{8,}).*", "g");
        var pwd = document.getElementById("password");
        if (pwd.value.length === 0) {
            strength.innerHTML = 'Type Password';
        } else if (false === enoughRegex.test(pwd.value)) {
            strength.innerHTML = 'More Characters';
        } else if (strongRegex.test(pwd.value)) {
            strength.innerHTML = '<span style="color:green">Strong! 👌</span>';
        } else if (mediumRegex.test(pwd.value)) {
            strength.innerHTML = '<span style="color:orange">Medium! 😐</span>';
        } else {
            strength.innerHTML = '<span style="color:red">Weak! 😡</span>';
        }
    }


    // useEffect(() => {
    // }, [reload]);


    const renderAuthButton = () => {

        if(role == 'customer') {
            return <>
            <br/><Button onClick={registerCustomer} className='register-btn' variant="contained" color="secondary">
                Register as customer
            </Button> </>;
        }
        else if(role == 'seller') {
            return (
                <>
                    <label htmlFor="email" className="label-register">Email:</label>
                    <input  id="email" type="text" placeholder="enter your email here" className="input-register"/>

                    <label htmlFor="seller_name" className="label-register">Seller name:</label>
                    <input  id="seller_name" type="text" placeholder="enter your company name here" className="input-register"/>
                    <br/> <br/>
                    <Button onClick={registerSeller} className='register-btn' variant="contained" color="secondary">
                        Register as seller
                    </Button>
                </>);
        }
    }

    const setRoleCustomer = () => {
        setRole('customer');
    }

    const setRoleSeller = () => {
        setRole('seller');
    }

    const validateCustomer = (username, password, confirm, name, surname, address)  => {
        let ok = true;
        if(password === ""  || confirm === "" || name === "" || surname === "" || address=== "") {
            ok = false;
            alert("Make sure to fill all fields!")
        }

        else if(password.length < 8) {
            ok = false;
            alert("Password should be at least 8 characters long! 😡")
        }
        else if(password !== confirm) {
            ok = false;
            alert("Passwords don't match!")
        }

        return ok;
    }

    const validateSeller = (email, address, seller_name,username,
                            password, confirm, name, surname) => {
        let ok = true;
        if (email === "" || address === "" || seller_name === "" ||
            password === "" || confirm === "" || username==="" || name === "" || surname === "") {
            ok = false;
            alert("Make sure to fill all fields!")
        } else if (password.length < 8) {
            ok = false;
            alert("Password should be at least 8 characters long! 😡")
        } else if (password !== confirm) {
            ok = false;
            alert("Passwords don't match!")
        } else if (validateEmail(email) === false) {
            ok = false;
            alert("You have entered an invalid email address!")
        }
        return ok;
    }
        const validateEmail = (email)  => {
            let mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
            if(email.match(mailformat)) {
                return true;
            }
            return false;
        }


    async function registerCustomer () {
     let password = document.getElementById('password').value;
     let confirm = document.getElementById('confirm').value;
     let name = document.getElementById('name').value;
     let surname = document.getElementById('surname').value;
     let username = document.getElementById('username').value;
     let address = document.getElementById('address').value;

     if (validateCustomer(username, password, confirm,name,surname,address)) {


         let customer = {"username": username, "password": password,
             "name":name, "surname":surname,"address":address}

         try {
             await CustomersService.addCustomer(customer);
             setShowAlert({ success: true, message: "Successfully registered" });
         } catch (error) {
             console.error(`Greška prilikom dodavanja novog zadataka: ${error}`);
             setShowAlert({
                 success: false,
                 message: "Error ocurred while registering",
             });
         }
         alert("successfully registered! log in before u start using the website")
         window.location.assign("/");
     }
 }


    async function registerSeller () {
            console.log("im here")
        let password = document.getElementById('password').value;
        let confirm = document.getElementById('confirm').value;
        let name = document.getElementById('name').value;
        let surname = document.getElementById('surname').value;
        let username = document.getElementById('username').value;
        let address = document.getElementById('address').value;
        let email = document.getElementById('email').value;
        let seller_name = document.getElementById('seller_name').value;

        if (validateSeller(email, address, seller_name,username,
            password, confirm, name, surname)) {

            let seller = {"username": username, "password": password,
                "name":name, "surname":surname,"address":address, "email":email,
            "sellerName":seller_name}
            try {
                await SellersService.addSeller(seller);
                setShowAlert({ success: true, message: "Successfully registered" });
            } catch (error) {
                console.error(`Greška prilikom dodavanja novog zadataka: ${error}`);
                setShowAlert({
                    success: false,
                    message: "Error ocurred while registering",
                });
            }
            alert("successfully registered! log in before u start using the website")
            window.location.assign("/");
        }
    }

   // const registerCustomer = () => {
   //      let password = document.getElementById('password').value;
   //      let confirm = document.getElementById('confirm').value;
   //      let name = document.getElementById('name').value;
   //      let surname = document.getElementById('surname').value;
   //      let username = document.getElementById('username').value;
   //      let address = document.getElementById('address').value;
   //
   //      console.log("wwwtf")
   //      if (validateCustomer(username, password, confirm,name,surname,address)) {
   //
   //
   //          // let customer = {"username": username, "password": password,
   //          //     "name":name, "surname":surname,"address":address}
   //          //
   //          // try {
   //          //     CustomersService.addCustomer(customer);
   //          //     setShowAlert({ success: true, message: "Successfully registered" });
   //          // } catch (error) {
   //          //     console.error(`Greška prilikom dodavanja novog zadataka: ${error}`);
   //          //     setShowAlert({
   //          //         success: false,
   //          //         message: "Error ocurred while registering",
   //          //     });
   //          //}
   //
   //      }
   //  }

    return (
        <>

            <div className="registerInfoCard">
            <h3>Register</h3>

                <div className="radios">
            <input type="radio" id="customer" name="usertype" value="customer" defaultChecked onClick={setRoleCustomer}/>
                <label className="labelRadio left" htmlFor="male">Register as customer</label>

            <input type="radio" id="seller" name="usertype" className="right" value="seller" onClick={setRoleSeller}/>
                <label className="labelRadio" htmlFor="female">Register as seller</label>
                </div>
            {sameData}

            {renderAuthButton()}
            </div>
        </>
    );
}


export default RegisterForm;
