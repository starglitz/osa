import {useEffect, useState} from "react";
import {SellersService} from "../services/SellersService";
import {ArticlesService} from "../services/ArticlesService";
import SellerCard from "./SellerCard";
import ArticleCard from "./ArticleCard";

const SellersArticles = () => {


    const [articles, setArticles] = useState([]);

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

    return (
        <>
            <div className="flex-container">
            {articles.map((a) =>
                <div className="flex-child">
                    <ArticleCard
                        key={a.id} id={a.id} path={a.path} name={a.name}
                        description={a.description} price={a.price}/>
                </div>
            )}
            </div>
        </>
    )

}

export default SellersArticles;