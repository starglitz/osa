import React, {useEffect, useState} from "react";
import {DiscountService} from "../../services/DiscountService";
import {ArticlesService} from "../../services/ArticlesService";
import ArticleCardDiscount from "./ArticleCardDiscount";
import {useHistory} from "react-router-dom";
import {makeStyles} from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import {CheckBox} from "@material-ui/icons";
import Checkbox from "./Checkbox";


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

const AddDiscount = () => {

    const [discount, setDiscount] = useState({
        dateFrom: new Date(),
        dateTo: new Date(),
        description: "",
        percent: 1,
        articles: []
    });

    const [articles, setArticles] = useState([]);

    const [selectedCheckboxes, setSelectedCheckboxes] = useState(new Set());

    const history = useHistory();

    const classes = useStyles();

    useEffect(() => {
        fetchArticles();
    }, [discount]);


    async function fetchArticles() {
        try {
            const response = await ArticlesService.getArticlesByCurrentSeller();
            setArticles(response.data);
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading sellers articles !: ${error}`);
        }
    }

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

    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value;
        setDiscount({ ...discount, [name]: val });
    };


    const delay = ms => new Promise(res => setTimeout(res, ms));

    async function addDiscount() {
        try {

            let start = document.getElementById('start');
            // if(start.valueAsDate != null) {
            //     start = start.valueAsDate.toJSON().slice(-13, -8)
            // }


            let end = document.getElementById('end');
            // if(end.valueAsDate != null) {
            //     end = end.valueAsDate.toJSON().slice(-13, -8)
            // }

            if(validate(start.valueAsDate, end.valueAsDate)) {
                let articlesA = [];
                for (const checkbox of selectedCheckboxes) {
                    articlesA.push({"id":checkbox})
                }
                console.log(start)
                console.log(end)
                // setArticles(articlesA)
                console.log("!?!?!??!")
                console.log(articlesA)

                setDiscount({...discount, ["dateFrom"]: start.valueAsDate,
                    ["dateTo"]:end.valueAsDate, ["articles"]:articlesA})

                //setDiscount({...discount, articles:articlesA})

                const copy = {...discount, articles: articlesA};
                console.log(copy)

                if(copy.articles.length != 0) {
                await DiscountService.addDiscount(copy);
                setDiscount({
                    dateFrom: new Date(),
                    dateTo: new Date(),
                    description: "",
                    percent: 0,
                    articles: []
                });
                history.push("/home")}

            }
        } catch (error) {
            console.error(`Error occured while adding a discount: ${error}`);
            alert("Error occured while adding a discount!.")
        }

    }

    const validate = (start, end) => {

        let ok = true;

        let today = new Date();

    console.log(start)
        console.log(end)

        if(start === null || end === null) {
            alert("Make sure to fill all fields!")
            ok = false;
        }

        else if(start <= today) {
            alert("Start date must be after current date!")
            ok = false;
        }
        else if(end <= start) {
            alert("End date must be after the start date!")
            ok = false;
        }

        else if(start === end) {
            alert("Start and end date cannot be the same!")
            ok = false;
        }
        else if(discount.percent > 60 || discount.percent < 1) {
            console.log(discount.description)
            console.log(discount.percent)
            alert("Discount cant be less than 1 or greater than 60!")
            ok = false;
        }

        else if(discount.description === "" || isNaN(discount.percent)) {
            console.log(discount.description)
            console.log(discount.percent)
            alert("Make sure to fill all fields!")
            ok = false;
        }


        else if(selectedCheckboxes.size === 0) {
            alert("You haven't picked any articles!");
            ok = false;
        }
        return ok
    }

    const test = () => {
        console.log(":(((")
        for (const checkbox of selectedCheckboxes) {
            console.log(checkbox, 'is selected.');
        }
    }

    return(
        <>
            <div className="registerInfoCard">
                <h3>Add new discount</h3>


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
                        <input  id="start" type="date" className="input-register"/>

                        <label htmlFor="end" className="label-register">Discount end date:</label>
                        <input  id="end" type="date" className="input-register"/>

                    </div>
                </div>
                <br/>
                <Button onClick={addDiscount} variant="contained" color="primary">Add</Button>

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

export default AddDiscount;