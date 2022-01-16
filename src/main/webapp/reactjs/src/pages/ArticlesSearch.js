import { Button } from "@material-ui/core";
import debounce from "lodash.debounce";
import { useEffect, useState } from "react";
import ArticleCardCustomer from "../components/customer/ArticleCardCustomer";
import NavBar from "../components/NavBar";
import { ArticlesService } from "../services/ArticlesService";

const ArticlesSearch = () => {
  const [articles, setArticles] = useState([]);
  const [query, setQuery] = useState("");

  const [from, setFrom] = useState(0);
  const [to, setTo] = useState(999999);

  useEffect(() => {
    fetchArticles();
  }, [query]);

  async function fetchArticles() {
    const response = await ArticlesService.getAll(query);
    setArticles(response.data);
    console.log(response.data);
  }

  async function fetchArticlesByRange() {
    console.log("FROM JE : ", from);
    console.log("TO JE: ", to);
    const response = await ArticlesService.getByRange(from, to);
    setArticles(response.data);
    console.log(response.data);
  }

  const debouncedFetchArticles = debounce((query) => {
    setQuery(query);
  }, 2000);

  const onSearchInputChange = (e) => {
    debouncedFetchArticles(e.target.value);
  };

  const reset = () => {
    setQuery("");
    setFrom(0);
    setTo(999999);
    fetchArticles();
  };

  return (
    <div>
      <NavBar></NavBar>
      <br></br>
      <label for="articleName" className="margin-right margin-bottom">
        Search all articles:{" "}
      </label>
      <input
        className="margin-bottom"
        type="text"
        name="articleName"
        onChange={onSearchInputChange}
      ></input>

      <br></br>
      <label for="from" className="margin-right margin-bottom">
        Search all articles by price range:
      </label>
      <input
        className="margin-bottom margin-right"
        type="number"
        name="from"
        onChange={(event) => setFrom(event.target.value)}
      ></input>

      <input
        className="margin-bottom margin-right"
        type="number"
        name="to"
        onChange={(event) => setTo(event.target.value)}
      ></input>

      <Button variant="contained" onClick={fetchArticlesByRange}>
        Search by price range
      </Button>

      <br></br>
      <Button variant="contained" color="error" onClick={reset}>
        {" "}
        Reset all{" "}
      </Button>

      <div
        className="flex-container"
        style={{ flexWrap: "wrap", width: "90%", margin: "0 auto" }}
      >
        {articles.map((a) => (
          <div
            className="flex-child"
            style={{ margin: "0 auto", marginTop: "30px" }}
          >
            <ArticleCardCustomer
              key={a.id}
              id={a.id}
              path={a.path}
              name={a.name}
              description={a.description}
              price={a.price}
              discounts={a.discounts}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default ArticlesSearch;
