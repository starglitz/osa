import debounce from "lodash.debounce";
import { useEffect, useState } from "react";
import ArticleCardCustomer from "../components/customer/ArticleCardCustomer";
import NavBar from "../components/NavBar";
import { ArticlesService } from "../services/ArticlesService";

const ArticlesSearch = () => {
  const [articles, setArticles] = useState([]);
  const [query, setQuery] = useState("");

  useEffect(() => {
    fetchArticles();
  }, [query]);

  async function fetchArticles() {
    const response = await ArticlesService.getAll(query);
    setArticles(response.data);
    console.log(response.data);
  }

  const debouncedFetchArticles = debounce((query) => {
    setQuery(query);
  }, 2000);

  const onSearchInputChange = (e) => {
    debouncedFetchArticles(e.target.value);
  };

  //   async function addToCart(orderItem) {

  //     try {

  //         console.log("order item: " + JSON.stringify(orderItem))

  //          let item_ids = []

  //         orderItems.forEach(item => item_ids.push(item.article.id));

  //         if(item_ids.includes(orderItem.article.id)) {
  //             let itemsModified = orderItems

  //             for (let i = 0; i < orderItems.length - 1; i++) {
  //                 if (orderItems[i].article.id === orderItem.article.id) {
  //                     let itemMod = orderItems[i];
  //                     itemMod.amount = +itemMod.amount + +orderItem.amount;
  //                     itemsModified.splice(i, 1);
  //                     itemsModified.push(itemMod);
  //                 }
  //             }
  //             setOrderItems(itemsModified)
  //             console.log(itemsModified)
  //             }

  //     else {
  //             setOrderItems(orderItems => [...orderItems, orderItem]);
  //         }

  //         console.log("order items now:" + orderItems);

  //     } catch (error) {
  //         console.error(`Error loading sellers articles !: ${error}`);
  //     }

  // }

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
