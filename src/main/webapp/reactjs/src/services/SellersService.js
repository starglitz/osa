import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const SellersService = {
    getSellers,
    getSeller,
    addSeller,
    editSeller,
    getMe,
};

async function getSellers() {
    return await SprintsAxiosClient.get("http://localhost:8080/sellers");
}

async function getSeller(id) {
    return await SprintsAxiosClient.get(`http://localhost:8080/sellers/${id}`);
}

async function editSeller(id, seller) {
    return await SprintsAxiosClient.put(`http://localhost:8080/sellers/${id}`, seller);
}

async function deleteSeller(id) {
    return await SprintsAxiosClient.delete(`http://localhost:8080/sellers/${id}`);
}

async function addSeller(seller) {
    return await SprintsAxiosClient.post("http://localhost:8080/registerSeller", seller);
}

async function getMe() {
    return await SprintsAxiosClient.get("http://localhost:8080/sellers/profile");
}
