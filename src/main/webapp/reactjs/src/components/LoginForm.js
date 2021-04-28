import React, {useEffect, useState} from 'react';
import {Paper, withStyles, Grid, TextField, Button, FormControlLabel, Checkbox, makeStyles} from '@material-ui/core';
import { Face, Fingerprint } from '@material-ui/icons'
import {useHistory} from "react-router-dom";
import {AuthenticationService} from "../services/AuthenticationService";
import {TokenService} from "../services/TokenService";



const LoginForm = (props) => {
 TokenService.removeToken();

    const DEFAULT_LOGIN = {
        username: '',
        password: ''
    };

    const useStyles = makeStyles((theme) => ({
        root: {
            display:'inline-block',
            width:'50%',
            margin:'20px',
            '& > *': {
                margin: '20px auto 20px auto',
                width: '25ch',
                display: 'block',

            },
        },
    }));

    const [loginData, setLoginData] = useState(DEFAULT_LOGIN);
    const [error, setError] = useState('');
    const history = useHistory();
    const handleChange = (event, prop) => {
        setLoginData({
            ...loginData,
            [prop]: event.target.value
        });
    };

    const classes = useStyles();

    const login = async () => {
        console.log(loginData)
        if(! await AuthenticationService.login(loginData)) {
            setError("Wrong username or password. Try again")
        }
    };

    useEffect(() => {
    }, [error]);



    const handleSubmit = (evt) => {
        evt.preventDefault();

        const data = {"username":loginData.username, "password":loginData.password};

        fetch('http://localhost:8080/login', {
            method: 'POST', // or 'PUT'
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response =>{
                if(response.status!==200){
                    throw new Error(response.status)
                }
                else {
                    history.push('/')
                }}  )
            .then(data => {
                console.log('Success:', JSON.stringify(data));
            })
            .catch((error) => {
                setError('Invalid username/password')
                console.error('Error:', console.warn(error));
            });


    }
    if(props != null) {
        let errortxt = <h3>{props.error}</h3>;
    }
    else {
        let errortxt=<br/>
    }

    const openRegister = () => {
        window.location.assign("/register");
    }

    return (
        <>
        <div className="loginFormContainer">
            <h1 style={{textAlign:'center', paddingTop:'40px'}}>Login</h1>

            <div style={{margin: '0 auto', display: 'flex',
                justifyContent: 'center', position:'relative'}}>


                    <form className={classes.root}>
                        <TextField value={loginData.username} onChange={(event) =>
                            handleChange(event, 'username')}
                                   id="outlined-basic" label="Username" variant="filled" />
                        <TextField helperText={error} value={loginData.password}
                                   onChange={(event) =>
                                       handleChange(event, 'password')}
                                   id="outlined-basic" label="Password" variant="filled" type='password' />
                        <Button onClick={login} type='button' variant="contained" size="large" color="default">Login</Button>
                </form>
                <div className="register-div">
                <p>Don't have an account?</p>
                <Button variant="contained" color="secondary" onClick={openRegister}>
                    Register
                </Button>
                </div>
            </div>
        </div>

        </>
    );

}

export default LoginForm;







