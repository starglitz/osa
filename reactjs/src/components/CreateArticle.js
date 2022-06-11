import { useHistory } from "react-router-dom";
import { useState } from "react";
import { ArticlesService } from "../services/ArticlesService";
import { Form } from "react-bootstrap";
import Button from "@material-ui/core/Button";

const CreateArticle = () => {
  const [article, setArticle] = useState({
    name: "",
    description: "",
    price: "",
    path: "",
  });

  const [selectedFile, setSelectedFile] = useState(null);

  const history = useHistory();

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
    } else if (article.price < 1) {
      alert("Price has to be a positive number!");
      ok = false;
    } else if (isNaN(article.price)) {
      alert("Price must be a number!");
      ok = false;
    }
    return ok;
  };

  function prepareUpload(event) {
    let files = event.target.files;
    let fileName = files[0].name;
    alert(fileName);
  }

  async function addArticle() {
    try {
      if (validate()) {
        const selected = document.getElementById("img").files[0];
        article.path = "/images/" + selected.name;
        await ArticlesService.addArticle(article);

        setArticle({
          name: "",
          description: "",
          price: "",
          path: "",
        });
        history.push("/home");
      }
    } catch (error) {
      console.error(`Greška prilikom dodavanja novog zadataka: ${error}`);
      alert("Make sure to fill all fields! Check if you've uploaded an image.");
    }
  }

  const onFileChangeHandler = (e) => {
    e.preventDefault();

    const selected = document.getElementById("img").files[0];
    const formData = new FormData();

    formData.append("file", selected);
    fetch("http://localhost:8080/upload", {
      method: "post",
      body: formData,
    }).then((res) => {
      if (res.ok) {
        alert("File uploaded successfully.");
      }
    });
  };

  return (
    <>
      <div className="edit-article-div">
        <h1>Add new article</h1>
        <Form action="./upload?${_csrf.parameterName}=${_csrf.token}">
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

          <div className="container">
            <div className="row">
              <div className="col-md-6">
                <div className="form-group files color">
                  <label>Upload Your File </label>

                  <input
                    id="img"
                    type="file"
                    className="form-control"
                    name="file"
                    onChange={onFileChangeHandler}
                  />
                </div>
              </div>
            </div>
          </div>

          <br />
          <br />

          <Button
            variant="contained"
            color="secondary"
            onClick={() => addArticle()}
          >
            ADD
          </Button>
        </Form>
      </div>
    </>
  );
};

export default CreateArticle;
