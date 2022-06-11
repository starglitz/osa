import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const UserService = {
    getUsers,
    update
};

async function getUsers() {
    return await SprintsAxiosClient.get("http://localhost:8080/users");
}

async function update(id, user) {
    return await SprintsAxiosClient.put(`http://localhost:8080/users/${id}`, user);
}