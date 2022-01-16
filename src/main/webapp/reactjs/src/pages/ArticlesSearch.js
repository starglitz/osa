import debounce from "lodash.debounce";
import { useEffect, useState } from "react";
import NavBar from "../components/NavBar";
import { ArticlesService } from "../services/ArticlesService";

const ArticlesSearch = () => {
  const [articles, setArticles] = useState([]);
  const [query, setQuery] = useState([]);

  useEffect(() => {
    fetchArticles();
  }, [query]);

  async function fetchArticles() {
    const response = await ArticlesService.getAll("Burger");
    console.log(response.data);
  }

  const debouncedFetchArticles = debounce((query) => {
    setQuery(query);
  }, 2000);

  const onSearchInputChange = (e) => {
    debouncedFetchArticles(e.target.value);
  };

  return (
    <div>
      <NavBar></NavBar>
      <label for="articleName">Search all articles: </label>
      <input type="text" name="articleName"></input>
    </div>
  );
};

export default ArticlesSearch;
