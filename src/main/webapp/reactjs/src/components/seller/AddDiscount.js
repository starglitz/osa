import React, {useEffect, useState} from "react";
import {DiscountService} from "../../services/DiscountService";
import {ArticlesService} from "../../services/ArticlesService";
import ArticleCardDiscount from "./ArticleCardDiscount";
import {useHistory} from "react-router-dom";

const AddDiscount = () => {

    const [discount, setDiscount] = useState({
        dateFrom: "",
        dateTo: "",
        description: "",
        percent: 0,
        articles: {}
    });

    const [articles, setArticles] = useState([]);

    const history = useHistory();

    useEffect(() => {
        fetchArticles();
    }, []);


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


    async function addDiscount() {
        try {

            let start = document.getElementById('start');
            if(start.valueAsDate != null) {
                start = start.valueAsDate.toJSON().slice(-13, -8)
            }

            let end = document.getElementById('end');
            if(end.valueAsDate != null) {
                end = end.valueAsDate.toJSON().slice(-13, -8)
            }

            if(validate(start, end)) {
                setDiscount({...discount, ["dateFrom"]:start, ["dateTo"]:end})
                await DiscountService.addDiscount(discount);
                setDiscount({
                    dateFrom: "",
                    dateTo: "",
                    description: "",
                    percent: 0,
                    articles: {}
                });
                history.push("/home")
            }
        } catch (error) {
            console.error(`Error occured while adding a discount: ${error}`);
            alert("Error occured while adding a discount!.")
        }

    }

    const validate = (start, end) => {

        let ok = true;

        let today = new Date();

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

        else if(discount.description === "" || isNaN(discount.percent) ) {
            alert("Make sure to fill all fields!")
            ok = false;
        }
        else if(discount.percent > 60 || discount.percent < 1) {
            alert("Discount cant be less than 1 or greater than 60!")
            ok = false;
        }
        return ok
    }


    return(
        <>
            <div className="registerInfoCard">
                <h3>Add new discount</h3>


                <div style={{margin: '0 auto', display: 'flex',
                    justifyContent: 'center'}}>

                    <div className="register-form">

                        <label htmlFor="description" className="label-register">Description:</label>
                        <input  id="description" type="text" placeholder="enter description" className="input-register"/>

                        <label htmlFor="percent" className="label-register">Discount percentage:</label>
                        <input  id="percent" type="number" className="input-register"/>

                        <label htmlFor="start" className="label-register">Discount start date:</label>
                        <input  id="start" type="date" className="input-register"/>

                        <label htmlFor="end" className="label-register">Discount end date:</label>
                        <input  id="end" type="date" className="input-register"/>

                    </div>
                </div>

            </div>
            <br/>
            <br/>
            <h5>Pick articles that discount applies to:</h5>

            <div className="flex-container" style={{flexWrap:"wrap", width:'90%',margin:'0 auto'}}>
                {articles.map((article) =>
                    <div className="flex-child" style={{margin: '0 auto', marginTop:'30px'}}>
                        <ArticleCardDiscount path={article.path} name={article.name}
                                             price={article.price} description = {article.description}>

                        </ArticleCardDiscount>
                    </div>
                )}
            </div>
            <br/>
            <br/>
        </>
    )
}

export default AddDiscount;