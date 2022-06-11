import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import React from "react";

const ArticleCardDiscount = (props) => {
  return (
    <>
      <CardActionArea>
        <CardMedia
          component="img"
          alt="Contemplative Reptile"
          width="150"
          height="200"
          image={window.location.origin + props.path}
          title="Contemplative Reptile"
        />
        <CardContent style={{ height: "180px" }}>
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
    </>
  );
};

export default ArticleCardDiscount;
