import {useEffect, useState} from "react";
import {ArticlesService} from "../../services/ArticlesService";
import ArticleCardSeller from "./ArticleCardSeller";
import MyComments from "./MyComments";

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

    async function deleteArticle(id) {
        try {
            await ArticlesService.deleteArticle(id);

            // Za novu vrednost liste zadataka uzima se prethodna lista, filtrirana tako da ne sadrži obrisani zatak
            setArticles((articles) => articles.filter((article) => article.id !== id));
        } catch (error) {
            console.error(`Greška prilikom brisanja zadataka ${id}: ${error}`);
        }
    }

    return (
        <>
            <div className="flex-container" style={{flexWrap:"wrap", width:'90%',margin:'0 auto'}}>
            {articles.map((a) =>
                <div className="flex-child" style={{margin: '0 auto', marginTop:'30px'}}>
                    <ArticleCardSeller
                        key={a.id} id={a.id} path={a.path} name={a.name}
                        description={a.description} price={a.price} deleteArticle={deleteArticle}/>
                </div>
            )}
            </div>

            <MyComments/>
        </>
    )

}

export default SellersArticles;