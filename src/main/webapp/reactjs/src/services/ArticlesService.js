import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const ArticlesService = {
    getArticlesByCurrentSeller,
    editArticle,
    getArticle
};

async function getArticlesByCurrentSeller () {
    return await SprintsAxiosClient.get('http://localhost:8080/articles/seller/me');
}

async function editArticle(id, article) {
    return await SprintsAxiosClient.put(`http://localhost:8080/articles/${id}`, article);
}


async function getArticle(id) {
    return await SprintsAxiosClient.get(`http://localhost:8080/articles/${id}`);
}