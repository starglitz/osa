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
import { withStyles } from "@material-ui/core/styles";


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

const RedTextTypography = withStyles({
    root: {
        color: "#e60000"
    }
})(Typography);

const ArticleCardCustomer = (props) => {
    const classes = useStyles();

    const history = useHistory();

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);



    // const goToEditPage = () => {
    //     history.push("/updateArticle/" + props.id);
    // };

    const handleAdd = () => {
        let amount = document.getElementById("articleAmount").value;
        console.log(amount)
        if(amount < 1) {
            console.log("is anything happening")
            alert("amount has to be a positive number!")
        }
        else {

            let article = {
                "id": props.id, "path": props.path, "name": props.name,
                "description": props.description, "price": props.price
            };

            let orderItem = {"amount": amount, "article": article};

            props.totalF(orderItem);
            props.addToCart(orderItem);

            setShow(false)
        }
    }


    return (

        <>
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
                    {/*<Typography variant="body2" component="h4">*/}
                    {/*    price: {props.price}*/}
                    {/*</Typography>*/}



                    {props.discounts === 0? <Typography variant="body2" component="h4">
                            price: {props.price}
                        </Typography>
                        :<div>
                        <RedTextTypography variant="body2" component="h4" style={{textColor:'red'}}>
                            DISCOUNT {props.discounts} %
                        </RedTextTypography>
                        <Typography variant="body2" component="h4">
                        price with discount: {props.price - (props.price * props.discounts)}
                        </Typography>
                        </div>
                    }



                    <Typography variant="body2" component="p">
                        {props.description}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions style={{position:"relative"}}>


                <Button
                    onClick={handleShow}
                    variant="contained"
                    color="default"
                    className={classes.button} style={{margin:'0 auto', marginBottom:'10px', position:"absolute", left:'130px'}}>
                    ORDER
                </Button>
                {/*<Button*/}
                {/*    variant="contained"*/}
                {/*    color="default"*/}
                {/*    onClick={() => props.deleteArticle(props.id)}*/}
                {/*    className={classes.button} style={{margin:'0 auto', marginBottom:'10px', position:"absolute",left:'20px'}}>*/}
                {/*    DELETE*/}
                {/*</Button>*/}

            </CardActions>
        </Card>

        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Pick amount</Modal.Title>
            </Modal.Header>
            <Modal.Body>Select amount of this article: <input id="articleAmount" type="number" defaultValue="1" min="1"/></Modal.Body>
            <Modal.Footer>
                <Button variant="contained" onClick={handleClose}>
                    Close
                </Button>
                <Button variant="contained" onClick={handleAdd}>
                    Select
                </Button>
            </Modal.Footer>
        </Modal>


        </>

    );
}

export default ArticleCardCustomer;