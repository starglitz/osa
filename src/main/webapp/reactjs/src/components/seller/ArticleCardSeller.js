import {makeStyles} from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import React from "react";
import {useHistory} from "react-router-dom";


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


const ArticleCardSeller = (props) => {
    const classes = useStyles();

    const history = useHistory();

    const goToEditPage = () => {
        history.push("/updateArticle/" + props.id);
    };


    return (
        <Card className={classes.root} >
            <CardActionArea>
                <CardMedia
                    component="img"
                    alt="Contemplative Reptile"

                    width="150"
                    height="200"
                    image={window.location.origin + props.path}
                    title="Contemplative Reptile"
                />
                <CardContent style={{height:'200px'}}>
                    <Typography gutterBottom variant="h5" component="h2">
                        {props.name}
                    </Typography>
                    <Typography variant="body2" component="h4">
                        price: {props.price}
                    </Typography>
                    <Typography variant="body2" component="p">
                        {props.description}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions style={{position:"relative"}}>


                <Button
                    variant="contained"
                    color="default"
                    onClick={goToEditPage}
                    className={classes.button} style={{margin:'0 auto', marginBottom:'10px', position:"absolute", left:'130px'}}>
                    MODIFY
                </Button>
                <Button
                    variant="contained"
                    color="default"
                    onClick={() => props.deleteArticle(props.id)}
                    className={classes.button} style={{margin:'0 auto', marginBottom:'10px', position:"absolute",left:'20px'}}>
                    DELETE
                </Button>

            </CardActions>
        </Card>
    );
}

export default ArticleCardSeller;