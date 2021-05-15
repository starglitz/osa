import {makeStyles} from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import React, {useState} from "react";
import {useHistory} from "react-router-dom";
import {Modal} from "react-bootstrap";
import {CheckBox} from "@material-ui/icons";


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


const customStyles = {
    content : {
        top                   : '50%',
        left                  : '50%',
        right                 : 'auto',
        bottom                : 'auto',
        marginRight           : '-50%',
        transform             : 'translate(-50%, -50%)'
    }
};

const ArticleCardDiscount = (props) => {
    const classes = useStyles();

    const history = useHistory();

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);



    // const goToEditPage = () => {
    //     history.push("/updateArticle/" + props.id);
    // };

    return (

        <>
            {/*<Card className={classes.root} >*/}
                <CardActionArea>
                    <CardMedia
                        component="img"
                        alt="Contemplative Reptile"

                        width="150"
                        height="200"
                        image={window.location.origin + props.path}
                        title="Contemplative Reptile"
                    />
                    <CardContent style={{height:'180px'}}>
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
                {/*<CardActions style={{position:"relative"}}>*/}
                {/*    <div style={{margin:'0 auto'}}>*/}
                {/*    apply discount*/}
                {/*    <input type="checkbox" style={{width:'20px'}}/>*/}
                {/*    </div>*/}


                {/*</CardActions>*/}
            {/*</Card>*/}


        </>

    );
}

export default ArticleCardDiscount;