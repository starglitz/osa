import React, { useState } from "react";
import { Button } from "@material-ui/core";
import { AuthenticationService } from "../services/AuthenticationService";
import { CustomersService } from "../services/CustomersService";
import { SellersService } from "../services/SellersService";
import { useHistory, useLocation } from "react-router-dom";

const ChangePassword = () => {
  const location = useLocation();
  const history = useHistory();

  const [user, setUser] = useState(location.state);

  async function change() {
    let old = document.getElementById("old").value;
    let newPass = document.getElementById("new").value;
    if (validate(old, newPass)) {
      let edited = { ...user, passwordValidate: old };
      edited.password = newPass;

      try {
        if (AuthenticationService.getRole() == "ROLE_CUSTOMER") {
          const status = await CustomersService.editCustomer(user.id, edited);

          if (status == "200") {
            history.push("/home");
          } else if (status == "400") {
            alert("The old password is incorrect!");
          }
        } else if (AuthenticationService.getRole() == "ROLE_SELLER") {
          const status = await SellersService.editSeller(user.id, edited);
          if (status == "200") {
            history.push("/home");
          } else if (status == "400") {
            alert("The old password is incorrect!");
          }
        }
      } catch (error) {
        console.error(`Error ocurred while updating your info: ${error}`);
      }
    }
  }

  const validate = (old, newPass) => {
    let ok = true;
    if (old === "") {
      alert("You left old password field empty!");
      ok = false;
    } else if (newPass === "") {
      alert("You left new password field empty!");
      ok = false;
    }
    return ok;
  };

  return (
    <>
      <div className="updateInfoCard">
        <div className="passwordChange">
          <table style={{ width: "100%", margin: "30px" }}>
            <tr style={{ padding: "10px", margin: "10px" }}>
              <td style={{ width: "50%", textAlign: "right", margin: "10px" }}>
                <label htmlFor="password" className="label-register">
                  {" "}
                  Old password:
                </label>
              </td>
              <td style={{ width: "50%", textAlign: "left", margin: "10px" }}>
                <input
                  name="password"
                  id="old"
                  type="password"
                  placeholder="enter old password here"
                  maxLength="100"
                  className="input-register"
                />
              </td>
            </tr>
            <tr>
              <td style={{ width: "50%", textAlign: "right" }}>
                <label htmlFor="confirm" className="label-register">
                  New password:
                </label>
              </td>
              <td style={{ width: "50%", textAlign: "left" }}>
                <input
                  id="new"
                  type="password"
                  placeholder="enter new password"
                  className="input-register"
                />
              </td>
            </tr>
          </table>

          <Button variant="contained" onClick={change}>
            Change
          </Button>
        </div>
      </div>
    </>
  );
};

export default ChangePassword;
