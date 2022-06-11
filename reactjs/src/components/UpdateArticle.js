import { useHistory, useParams } from "react-router-dom";
import { Form } from "react-bootstrap";
import Button from "@material-ui/core/Button";
import { ArticlesService } from "../services/ArticlesService";
import { useEffect, useState } from "react";

const UpdateArticle = () => {
  const { id } = useParams();

  const [article, setArticle] = useState({
    name: "",
    description: "",
    price: "",
    path: "",
    id: "",
  });

  const history = useHistory();

  useEffect(() => {
    fetchArticle(id);
  }, [id]);

  async function fetchArticle(id) {
    try {
      const response = await ArticlesService.getArticle(id);
      setArticle(response.data);
    } catch (error) {
      console.error(`Error while loading article with id ${id}: ${error}`);
    }
  }

  const handleFormInputChange = (name) => (event) => {
    const val = event.target.value;
    setArticle({ ...article, [name]: val });
  };

  const validate = () => {
    let ok = true;
    if (
      article.name === "" ||
      article.description === "" ||
      article.price === ""
    ) {
      alert("Make sure to fill all fields!");
      ok = false;
    }
    if (article.price < 1) {
      alert("Price has to be a positive number!");
      ok = false;
    }
    if (isNaN(article.price)) {
      alert("Price must be a number!");
      ok = false;
    }
    return ok;
  };

  async function editArticle() {
    if (validate()) {
      try {
        await ArticlesService.editArticle(id, article);
        history.push("/home");
      } catch (error) {
        console.error(`Error ocurred while updating the article: ${error}`);
      }
    }
  }

  return (
    <>
      <div className="edit-article-div">
        <h1>Edit article</h1>
        <Form>
          <Form.Group>
            <Form.Label>Name</Form.Label>
            <Form.Control
              onChange={handleFormInputChange("name")}
              name="name"
              value={article.name}
              as="input"
            />
          </Form.Group>
          <Form.Group>
            <Form.Label>Description</Form.Label>
            <Form.Control
              onChange={handleFormInputChange("description")}
              name="description"
              value={article.description}
              as="input"
            />
          </Form.Group>
          <Form.Group>
            <Form.Label>Price</Form.Label>
            <Form.Control
              onChange={handleFormInputChange("price")}
              name="price"
              value={article.price}
              as="input"
            />
          </Form.Group>
          <img
            alt="article"
            src={article.path}
            style={{ width: "250px", height: "200px" }}
          />{" "}
          <br /> <br />
          <Button
            variant="contained"
            color="secondary"
            onClick={() => editArticle()}
          >
            Edit
          </Button>
        </Form>
      </div>
    </>
  );
};

export default UpdateArticle;
