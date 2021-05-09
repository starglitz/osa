import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const OrderService = {
    addOrder
};

async function addOrder(order) {
    return await SprintsAxiosClient.post("http://localhost:8080/order", order);
}