import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const CustomersService = {
    getCustomer,
    getCustomers,
    addCustomer,
    editCustomer,
    deleteCustomer,
};

async function getCustomers() {
    return await SprintsAxiosClient.get("http://localhost:8080/customers/all");
}

async function getCustomer(id) {
    return await SprintsAxiosClient.get(`http://localhost:8080/customers/${id}`);
}

async function editCustomer(id, customer) {
    return await SprintsAxiosClient.put(`http://localhost:8080/customers/${id}`, customer);
}

async function deleteCustomer(id) {
    return await SprintsAxiosClient.delete(`http://localhost:8080/customers/${id}`);
}

async function addCustomer(customer) {
    return await SprintsAxiosClient.post("http://localhost:8080/registerCustomer", customer);
}