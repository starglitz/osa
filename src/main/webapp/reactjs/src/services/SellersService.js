import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const SellersService = {
    getSellers,
    getSeller,
    addSeller,
    editSeller,
    deleteSeller,
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

// async function changeTaskState(id) {
//     return await SprintsAxiosClient.post(`tasks/${id}/change_state`);
// }

// function isTaskValid(task) {
//     return (
//         task.name !== "" &&
//         task.points !== "" &&
//         task.points >= 0 &&
//         task.points <= 20 &&
//         task.points % 1 === 0 &&
//         task.sprintId !== ""
//     );
// }

// async function getTasks(searchOptions) {
//     const config = {
//         params: {
//             pageNo: searchOptions.page,
//             taskName: searchOptions.taskName,
//             sprintId: searchOptions.sprintId,
//         },
//     };
//     return await SprintsAxiosClient.get("tasks", config);
// }
