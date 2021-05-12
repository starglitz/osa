import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const CustomersService = {
    getCustomer,
    getCustomers,
    addCustomer,
    editCustomer,
    deleteCustomer,
    getMe
};

async function getCustomers() {
    return await SprintsAxiosClient.get("http://localhost:8080/customers/all");
}

async function getCustomer(id) {
    return await SprintsAxiosClient.get(`http://localhost:8080/customers/${id}`);
}

async function editCustomer(id, customer) {
    let status = '200';
    await SprintsAxiosClient.put(`http://localhost:8080/customers/${id}`, customer)
        .catch(function (error) {
            if (error.response) {
                console.log(error.response.status);
                if(error.response.status == '400') {
                    status = '400'
                    return '400'
                }
            }
        });
    return status;
}

async function deleteCustomer(id) {
    return await SprintsAxiosClient.delete(`http://localhost:8080/customers/${id}`);
}

async function addCustomer(customer) {
    return await SprintsAxiosClient.post("http://localhost:8080/registerCustomer", customer);
}

async function getMe() {
    return await SprintsAxiosClient.get("http://localhost:8080/customers/profile");
}