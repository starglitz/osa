import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const DiscountService = {
    addDiscount,
    getDiscountByCurrentSeller
};



async function getDiscountByCurrentSeller () {
    return await SprintsAxiosClient.get('http://localhost:8080/discounts/seller/me');
}

async function addDiscount(discount) {
    return await SprintsAxiosClient.post("http://localhost:8080/discounts", discount);
}