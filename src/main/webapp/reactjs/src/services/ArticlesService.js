import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const ArticlesService = {
  getArticlesByCurrentSeller,
  editArticle,
  getArticle,
  deleteArticle,
  addArticle,
  getArticlesBySellerId,
  getAll,
  getByRange,
};

async function getArticlesByCurrentSeller() {
  return await SprintsAxiosClient.get(
    "http://localhost:8080/articles/seller/me"
  );
}

async function getArticlesBySellerId(id) {
  return await SprintsAxiosClient.get(
    `http://localhost:8080/articles/seller/${id}`
  );
}

async function editArticle(id, article) {
  return await SprintsAxiosClient.put(
    `http://localhost:8080/articles/${id}`,
    article
  );
}

async function getArticle(id) {
  return await SprintsAxiosClient.get(`http://localhost:8080/articles/${id}`);
}

async function deleteArticle(id) {
  return await SprintsAxiosClient.delete(
    `http://localhost:8080/articles/${id}`
  );
}

async function addArticle(article) {
  return await SprintsAxiosClient.post(
    "http://localhost:8080/articles",
    article
  );
}

async function getAll(search) {
  return await SprintsAxiosClient.get("http://localhost:8080/articles/search", {
    params: { search },
  });
}

async function getByRange(from, to) {
  return await SprintsAxiosClient.get(
    "http://localhost:8080/articles/searchByRange",
    {
      params: {
        start: from,
        end: to,
      },
    }
  );
}
