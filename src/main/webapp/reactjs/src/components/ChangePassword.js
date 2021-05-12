import React, {useState} from "react";
import {Button} from "@material-ui/core";
import {AuthenticationService} from "../services/AuthenticationService";
import {CustomersService} from "../services/CustomersService";
import {SellersService} from "../services/SellersService";
import {useHistory, useLocation} from "react-router-dom";

const ChangePassword = () => {
    const location = useLocation();
    const history = useHistory();

    const [user, setUser] = useState(location.state);



    async function change() {
        console.log("ehh")
        let old = document.getElementById("old").value;
        let newPass = document.getElementById("new").value;
        if(validate(old, newPass)) {

            let edited = {...user, "passwordValidate":old};
            edited.password = newPass;
            // edited.password = newPass;
            // edited.passwordValidate = old;
            //console.log(edited);
            try {
                if(AuthenticationService.getRole() == 'ROLE_CUSTOMER') {
                    console.log(user)
                    const status = await CustomersService.editCustomer(user.id, edited)
                    console.log("status here: " + status)
                    if(status == '200') {
                        history.push("/home")
                    }
                    else if(status == '400') {
                        alert("The old password is incorrect!")
                    }
                }
                else if(AuthenticationService.getRole() == 'ROLE_SELLER') {
                    console.log('am i here')
                    console.log(edited)
                    const status = await SellersService.editSeller(user.id, edited)
                    if(status == '200') {
                        history.push("/home")
                    }
                    else if(status == '400') {
                        alert("The old password is incorrect!")
                    }
                }
            } catch (error) {
                console.error(`Error ocurred while updating your info: ${error}`);
            }
        }
    }

    const validate = (old, newPass) => {
        let ok = true
        if(old === "") {
            alert("You left old password field empty!")
            ok = false
        }
        else if(newPass === "") {
            alert("You left new password field empty!")
            ok = false
        }
        return ok
    }

    return(
        <>
            <div className="updateInfoCard">
            <div className="passwordChange">

                <label htmlFor="password" className="label-register"> Old password:</label>
                <input name="password" id="old" type="password" placeholder="enter old password here"
                       maxLength="100" className="input-register" />


                <label htmlFor="confirm" className="label-register">New password:</label>
                <input  id="new" type="password" placeholder="enter new password" className="input-register"/>


                <Button variant="contained" onClick={change}>Change</Button>
            </div>
            </div>
        </>
    )
}

export default ChangePassword;