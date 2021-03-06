import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const OrderService = {
  addOrder,
  getOrdersByCurrentCustomer,
  editOrder,
  setDelivered,
  getBySellerId,
  getOrdersByCurrentSeller,
  setArchived,
  getAll,
  getByRange,
};

async function addOrder(order) {
  return await SprintsAxiosClient.post("http://localhost:8080/order", order);
}

async function getOrdersByCurrentCustomer() {
  return await SprintsAxiosClient.get(
    "http://localhost:8080/order/customer/me"
  );
}

async function getAll(query) {
  console.log("Query at the time: ", query);
  return await SprintsAxiosClient.get("http://localhost:8080/order", {
    params: { query },
  });
}

async function getByRange(from, to) {
  return await SprintsAxiosClient.get("http://localhost:8080/order/range", {
    params: {
      start: from,
      end: to,
    },
  });
}

async function getOrdersByCurrentSeller() {
  return await SprintsAxiosClient.get("http://localhost:8080/order/seller/me");
}

async function editOrder(id, order) {
  return await SprintsAxiosClient.put(
    `http://localhost:8080/order/${id}`,
    order
  );
}

async function setDelivered(id, order) {
  return await SprintsAxiosClient.put(
    `http://localhost:8080/order/delivered/${id}`,
    order
  );
}

async function setArchived(id, order) {
  return await SprintsAxiosClient.put(
    `http://localhost:8080/order/archived/${id}`,
    order
  );
}

async function getBySellerId(id) {
  return await SprintsAxiosClient.get(
    `http://localhost:8080/order/seller/${id}`
  );
}
