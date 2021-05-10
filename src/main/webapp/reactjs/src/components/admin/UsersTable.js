import React, {useEffect, useState} from "react";
import {SellersService} from "../../services/SellersService";
import {UserService} from "../../services/UserService";
import Button from "@material-ui/core/Button";
import {Nav} from "react-bootstrap";
import NavigationBar from "../NavigationBar";
import {AuthenticationService} from "../../services/AuthenticationService";


const UsersTable = () => {

    const [users, setUsers] = useState([]);
    const [random, setRandom] = useState([]);

    useEffect(() => {
        fetchUsers();
    }, [users]);

    async function fetchUsers() {
        try {
            const response = await UserService.getUsers();
            let usersfetch = response.data.filter(user => user.blocked === false)
            setUsers(usersfetch);
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading all users !: ${error}`);
        }
    }

    const logout = () => {
        AuthenticationService.logout();
    }

    async function block(user, id) {
        try {
            console.log(user)
            user.blocked = true;
            await UserService.update(id, user);
        } catch (error) {
            console.error(`Error ocurred: ${error}`);
        }
    }

    return(
        <>
            <NavigationBar>
                <Nav>
                    <Nav.Link href="/home">Admin option</Nav.Link>
                    {/*<Nav.Link eventKey={2} href="#memes">*/}
                    {/*    Dank memes*/}
                    {/*</Nav.Link>*/}
                    <Nav.Link eventKey={3} onClick={logout}>
                        Log out
                    </Nav.Link>

                </Nav>
            </NavigationBar>


            <table className="styled-table">
                <thead>
                    <tr>
                        <td>User ID</td>
                        <td>Name</td>
                        <td>Surname</td>
                        <td>Username</td>
                        <td>Role</td>
                        <td>Block</td>
                    </tr>
                </thead>
                <tbody>

                {users.filter(user => user.blocked === false).map((user) =>

                    <tr>
                        <td>{user.id}</td>
                        <td>{user.name}</td>
                        <td>{user.surname}</td>
                        <td>{user.username}</td>
                        <td>{user.role}</td>
                        <td><Button variant="contained" onClick={() => block(user, user.id)}>Block user</Button></td>
                    </tr>

                )}
                </tbody>

            </table>
        </>
    )
}

export default UsersTable