export const ValidateRegister = {
    // validateCustomer,
    // validateSeller
};

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
    if(email === ""  || address === "" || seller_name === "" ||
        password === ""  || confirm === "" || name === "" || surname === "") {
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

    else if(validateEmail(email) === false) {
        ok = false;
        alert("You have entered an invalid email address!")
    }



}

const validateEmail = (email)  => {
    let mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if(email.match(mailformat)) {
        return true;
    }
    return false;
}