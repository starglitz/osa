import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const DiscountService = {
    addDiscount,
    getDiscountByCurrentSeller,
    deleteDiscount
};



async function getDiscountByCurrentSeller () {
    return await SprintsAxiosClient.get('http://localhost:8080/discounts/seller/me');
}

async function addDiscount(discount) {
    return await SprintsAxiosClient.post("http://localhost:8080/discounts", discount);
}

async function deleteDiscount(id) {
    return await SprintsAxiosClient.delete(`http://localhost:8080/discounts/${id}`);
}