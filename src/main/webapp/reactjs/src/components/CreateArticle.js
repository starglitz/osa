import {useHistory, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {ArticlesService} from "../services/ArticlesService";
import {Form} from "react-bootstrap";
import Button from "@material-ui/core/Button";

const CreateArticle = () => {



    const [article, setArticle] = useState({
        name: "",
        description: "",
        price: "",
        path: "",
    });

    const history = useHistory();



    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value;
        setArticle({ ...article, [name]: val });
    };


    const validate = () => {
        let ok = true;
        if(article.name === "" || article.description === "" || article.price === "") {
            alert("Make sure to fill all fields!")
            ok = false;
        }
        if(article.price < 1) {
            alert("Price has to be a positive number!")
            ok = false;
        }
        if(isNaN(article.price)) {
            alert("Price must be a number!")
            ok = false;
        }
        return ok
    }

    async function addArticle() {
        try {
            await ArticlesService.addArticle(article);

            // Resetovanje polja nakon što je zadatak dodat
            setArticle({
                name: "",
                description: "",
                price: "",
                path: "",
            });
            history.push("/home")
        } catch (error) {
            console.error(`Greška prilikom dodavanja novog zadataka: ${error}`);
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

                    <img src={article.path} style={{width:'250px', height:'200px'}}/> <br/> <br/>

                    <Button  variant="contained" color="secondary" onClick={() => addArticle()}>
                        ADD
                    </Button>
                </Form>
            </div>
        </>
    );


}

export default CreateArticle;