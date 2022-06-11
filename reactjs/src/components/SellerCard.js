import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import img from "../assets/food3.png";
import { useHistory } from "react-router-dom";

const useStyles = makeStyles({
  root: {
    width: 250,
    height: 480,
    backgroundColor: "aliceblue",
    borderRadius: "10px",
    boxShadow: "0 4px 8px 0 rgba(0,0,0,0.2)",
    transition: "0.3s",
  },
});

const SellerCard = (props) => {
  const classes = useStyles();

  const history = useHistory();

  const showArticles = () => {
    history.push({
      pathname: "/sellersArticles",
      search: "?id=" + props.id,
      state: { detail: props.id },
    });
  };

  return (
    <Card className={classes.root}>
      <CardActionArea>
        <CardMedia
          component="img"
          alt="Contemplative Reptile"
          width="150"
          image={img}
          title="Contemplative Reptile"
        />
        <CardContent style={{ height: "190px" }}>
          <Typography gutterBottom variant="h5" component="h2">
            {props.sellerName}
          </Typography>
          <Typography variant="body2" component="p">
            contact email: {props.email}
          </Typography>
          <Typography variant="body2" component="p">
            owner: {props.name}
          </Typography>
          <Typography variant="body2" component="p">
            address: {props.address}
          </Typography>
          <Typography variant="body2" component="p">
            {props.rating != 0 ? (
              <p>average rating: {props.rating} </p>
            ) : (
              <p> This seller is not rated yet</p>
            )}
          </Typography>
        </CardContent>
      </CardActionArea>
      <div style={{ position: "relative" }}>
        <CardActions style={{ position: "relative" }}>
          <Button
            onClick={showArticles}
            variant="contained"
            color="default"
            className={classes.button}
            style={{
              margin: "0 auto",
              marginBottom: "10px",
              position: "absolute",
              left: "20px",
            }}
          >
            ORDER
          </Button>
        </CardActions>
      </div>
    </Card>
  );
};

export default SellerCard;
