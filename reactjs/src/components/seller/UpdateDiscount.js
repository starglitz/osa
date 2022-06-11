import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import ArticleCardDiscount from "./ArticleCardDiscount";
import CardActions from "@material-ui/core/CardActions";
import Checkbox from "./Checkbox";
import React, { useEffect, useRef, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import { ArticlesService } from "../../services/ArticlesService";
import { DiscountService } from "../../services/DiscountService";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles({
  root: {
    width: 250,
    height: 450,
    backgroundColor: "aliceblue",
    borderRadius: "10px",
    boxShadow: "0 4px 8px 0 rgba(0,0,0,0.2)",
    transition: "0.3s",
  },
});

const UpdateDiscount = () => {
  const [discount, setDiscount] = useState({
    discount_id: "",
    dateFrom: "",
    dateTo: "",
    description: "",
    percent: "",
    articles: [],
  });

  const [articles, setArticles] = useState([]);

  const [selectedCheckboxes, setSelectedCheckboxes] = useState(new Set());

  const [rerender, setRerender] = useState(true);

  const history = useHistory();

  const articlesRef = useRef([]);
  const discountRef = useRef({
    discount_id: "",
    dateFrom: "",
    dateTo: "",
    description: "",
    percent: 0,
    articles: [],
  });

  const { id } = useParams();
  const classes = useStyles();

  useEffect(() => {
    fetchDiscount(id);
    fetchArticles(function () {
      manageSelected();
    });
    manageSelected();
  }, []);

  function manageSelected() {
    let discount_article_ids = [];

    discountRef.current.articles.forEach((article) =>
      discount_article_ids.push(article.id)
    );
    let alreadySelected = new Set();

    articlesRef.current.forEach((article) => {
      if (discount_article_ids.includes(article.id)) {
        alreadySelected.add(article.id);
      }
    });

    setSelectedCheckboxes(alreadySelected);
    setRerender(!rerender);
  }

  async function fetchDiscount(id) {
    try {
      const response = await DiscountService.getDiscount(id);
      setDiscount(response.data);
      discountRef.current = response.data;
    } catch (error) {
      console.error(`Error while loading discount with id ${id}: ${error}`);
    }
  }

  async function fetchArticles(_callback) {
    try {
      const response = await ArticlesService.getArticlesByCurrentSeller();
      setArticles(response.data);
      articlesRef.current = response.data;
      console.log(response.data);
      _callback();
    } catch (error) {
      console.error(`Error loading sellers articles !: ${error}`);
    }
  }

  const handleFormInputChange = (name) => (event) => {
    const val = event.target.value;
    setDiscount({ ...discount, [name]: val });
  };

  const toggleCheckbox = (label) => {
    let copy = selectedCheckboxes;
    if (selectedCheckboxes.has(label)) {
      copy.delete(label);
      setSelectedCheckboxes(copy);
    } else {
      copy.add(label);
      setSelectedCheckboxes(copy);
    }
  };

  async function edit() {
    let start = document.getElementById("start");

    let end = document.getElementById("end");

    let description = document.getElementById("description").value;

    let percent = document.getElementById("percent").value;

    if (validate(start.valueAsDate, end.valueAsDate, description, percent)) {
      let articlesA = [];
      for (const checkbox of selectedCheckboxes) {
        articlesA.push({ id: checkbox });
      }

      const copy = {
        ...discount,
        articles: articlesA,
        dateFrom: start.valueAsDate,
        dateTo: end.valueAsDate,
        description: description,
        percent: percent,
      };
      console.log(copy);

      if (copy.articles.length != 0) {
        try {
          await DiscountService.editDiscount(id, copy);
          setDiscount({
            discount_id: "",
            dateFrom: "",
            dateTo: "",
            description: "",
            percent: 0,
            articles: [],
          });
          history.push("/home");
        } catch (error) {
          console.error(`Error ocurred while updating the discount: ${error}`);
        }
      }
    }
  }

  const validate = (start, end, description, percent) => {
    let ok = true;

    if (start === null || end === null) {
      alert("Make sure to fill all fields!");
      ok = false;
    } else if (end <= start) {
      alert("End date must be after the start date!");
      ok = false;
    } else if (start === end) {
      alert("Start and end date cannot be the same!");
      ok = false;
    } else if (percent > 60 || percent < 1) {
      alert("Discount cant be less than 1 or greater than 60!");
      ok = false;
    } else if (description === "" || isNaN(percent)) {
      alert("Make sure to fill all fields!");
      ok = false;
    } else if (selectedCheckboxes.size === 0) {
      alert("You haven't picked any articles!");
      ok = false;
    }
    return ok;
  };

  return (
    <>
      <div className="registerInfoCard">
        <h3>Edit discount</h3>

        <div
          style={{
            margin: "0 auto",
            display: "flex",
            justifyContent: "center",
          }}
        >
          <div className="register-form">
            <label htmlFor="description" className="label-register">
              Description:
            </label>
            <input
              defaultValue={discount.description}
              id="description"
              type="text"
              placeholder="enter description"
              className="input-register"
            />

            <label htmlFor="percent" className="label-register">
              Discount percentage:
            </label>
            <input
              defaultValue={discount.percent}
              id="percent"
              type="number"
              className="input-register"
            />

            <label htmlFor="start" className="label-register">
              Discount start date:
            </label>
            <input
              defaultValue={discount.dateFrom}
              id="start"
              type="date"
              className="input-register"
            />

            <label htmlFor="end" className="label-register">
              Discount end date:
            </label>
            <input
              defaultValue={discount.dateTo}
              id="end"
              type="date"
              className="input-register"
            />
          </div>
        </div>
        <br />
        <Button onClick={edit} variant="contained" color="primary">
          EDIT
        </Button>
      </div>
      <br />
      <br />
      <h5>Pick articles that discount applies to:</h5>
      <h6>
        (Articles that are already on discounts of more than 60% are not shown)
      </h6>

      <div
        className="flex-container"
        style={{ flexWrap: "wrap", width: "90%", margin: "0 auto" }}
      >
        {articles.map(
          (article) =>
            article.discounts < 60 && (
              <div
                className="flex-child"
                style={{ margin: "0 auto", marginTop: "30px" }}
              >
                <Card className={classes.root}>
                  <ArticleCardDiscount
                    path={article.path}
                    name={article.name}
                    price={article.price}
                    description={article.description}
                  ></ArticleCardDiscount>
                  <CardActions style={{ position: "relative" }}>
                    <div style={{ margin: "0 auto" }}>
                      apply discount
                      <Checkbox
                        style={{ width: "20px" }}
                        label={article.id}
                        handleCheckboxChange={() => toggleCheckbox(article.id)}
                        key={article.id}
                        checked={
                          selectedCheckboxes.has(article.id) ? true : false
                        }
                      />
                    </div>
                  </CardActions>
                </Card>
              </div>
            )
        )}
      </div>
      <br />
      <br />
    </>
  );
};

export default UpdateDiscount;
