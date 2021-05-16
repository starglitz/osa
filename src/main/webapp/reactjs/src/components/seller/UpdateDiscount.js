import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import ArticleCardDiscount from "./ArticleCardDiscount";
import CardActions from "@material-ui/core/CardActions";
import Checkbox from "./Checkbox";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {ArticlesService} from "../../services/ArticlesService";
import {DiscountService} from "../../services/DiscountService";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles({
    root: {
        width:250,
        height:450,
        backgroundColor:'aliceblue',
        borderRadius:'10px',
        boxShadow: '0 4px 8px 0 rgba(0,0,0,0.2)',
        transition: '0.3s'
    },
});


const UpdateDiscount = () => {

    const [discount, setDiscount] = useState({});

    const [articles, setArticles] = useState([]);

    const [selectedCheckboxes, setSelectedCheckboxes] = useState(new Set());

    const { id } = useParams();
    const classes = useStyles();

    useEffect(() => {
        fetchDiscount(id);
        fetchArticles();
    }, [id]);


    async function fetchDiscount(id) {
        try {
            const response = await DiscountService.getDiscount(id);
            setDiscount(response.data);
        } catch (error) {
            console.error(`Error while loading discount with id ${id}: ${error}`);
        }
    }

    async function fetchArticles() {
        try {
            const response = await ArticlesService.getArticlesByCurrentSeller();
            setArticles(response.data);
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading sellers articles !: ${error}`);
        }
    }

    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value;
        setDiscount({ ...discount, [name]: val });
    };


    const toggleCheckbox = label => {
        console.log(label)
        let copy = selectedCheckboxes
        if (selectedCheckboxes.has(label)) {
            copy.delete(label)
            setSelectedCheckboxes(copy)
            //selectedCheckboxes.delete(label);
        } else {
            copy.add(label)
            setSelectedCheckboxes(copy)
            //selectedCheckboxes.add(label);
        }
    }

    return (
        <>
            <div className="registerInfoCard">
                <h3>Edit discount</h3>


                <div style={{margin: '0 auto', display: 'flex',
                    justifyContent: 'center'}}>

                    <div className="register-form">

                        <label htmlFor="description" className="label-register">Description:</label>
                        <input defaultValue={discount.description}
                               onChange={handleFormInputChange("description")}
                               id="description" type="text" placeholder="enter description"
                               className="input-register"/>

                        <label htmlFor="percent" className="label-register">Discount percentage:</label>
                        <input defaultValue={discount.percent} id="percent"
                               type="number" className="input-register"
                               onChange={handleFormInputChange("percent")}/>

                        <label htmlFor="start" className="label-register">Discount start date:</label>
                        <input defaultValue={discount.dateFrom} id="start" type="date" className="input-register"/>

                        <label htmlFor="end" className="label-register">Discount end date:</label>
                        <input defaultValue={discount.dateTo} id="end" type="date" className="input-register"/>

                    </div>
                </div>
                <br/>
                <Button variant="contained" color="primary">EDIT</Button>

            </div>
            <br/>
            <br/>
            <h5>Pick articles that discount applies to:</h5>
            <h6>(Articles that are already on discounts of more than 60% are not shown)</h6>

            <div className="flex-container" style={{flexWrap:"wrap", width:'90%',margin:'0 auto'}}>
                {articles.map((article) =>
                    article.discounts < 60 &&
                    <div className="flex-child" style={{margin: '0 auto', marginTop:'30px'}}>
                        <Card className={classes.root} >
                            <ArticleCardDiscount path={article.path} name={article.name}
                                                 price={article.price} description = {article.description}>

                            </ArticleCardDiscount>
                            <CardActions style={{position:"relative"}}>
                                <div style={{margin:'0 auto'}}>
                                    apply discount
                                    <Checkbox style={{width:'20px'}}
                                              label={article.id}
                                              handleCheckboxChange={() => toggleCheckbox(article.id)}
                                              key={article.id}/>
                                </div>
                            </CardActions>
                        </Card>
                    </div>
                )}
            </div>
            <br/>
            <br/>

        </>
    )
}

export default UpdateDiscount;