import React, {useEffect, useState} from 'react';
import {TextField, Button, makeStyles} from '@material-ui/core';
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







