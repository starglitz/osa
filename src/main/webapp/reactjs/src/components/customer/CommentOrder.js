import React, {useState} from "react";
import {OrderService} from "../../services/OrderService";
import {useHistory, useLocation} from "react-router-dom";
import Button from "@material-ui/core/Button";

const CommentOrder = () => {

    const location = useLocation();

    const [order, setOrder] = useState(location.state);

    const history = useHistory();


    async function edit(){

        let edited = order

        let rating = document.getElementById("rating").value;


        if((rating < 1) || (rating > 5) || (rating = "") || isNaN(rating)) {
            alert("Rating has to be a number between 1 and 5!")
        }
        else if(order.comment === "" || order.comment === null) {
            alert("You have to fill in the comment field!")
        }

        else {
            if(document.getElementById("anon").checked) {
                edited = {...order, ["anonymous"]:true}
            }

            edited.rating = document.getElementById("rating").value;
            console.log(edited);

            try {
                await OrderService.editOrder(order.id, edited);
                history.push("/home")
            } catch (error) {
                console.error(`Error ocurred while updating the order: ${error}`);
            }
        }

    }



    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value;
        setOrder({ ...order, [name]: val });
    };




    return (
        <>
        <h1>Leave a comment on order</h1>
            <table className="styled-table">
                <thead>
                <tr>
                    <td>Order ID</td>
                    <td>Time</td>
                    <td>Order items</td>
                </tr>
                </thead>
                <tbody>
                    <tr>
                    <td>{order.id}</td>
                    <td>{order.time}</td>
                    <td>
                        {order.items.map((item) => <p>{item.article.name} x{item.amount} </p> )}
                    </td>
                    </tr>
                </tbody>
            </table>

            <br/>
            <br/>

            <table style={{width:'100%'}}>
                <tr>
                    <td>
                        <label htmlFor="comment" >Comment:</label><br/>
                        <input id="comment" type="text" placeholder="enter your comment here"
                               defaultValue={order.comment} style={{width:'400px'}}
                                onChange={handleFormInputChange("comment")}/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label htmlFor="rating" >Rating (1-5):</label> <br/>
                        <input id="rating" type="number"
                                onChange={handleFormInputChange("rating")}/>

                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="checkbox" id="anon" name="anon"/>
                        <label htmlFor="anon"> Stay anonymous</label><br/>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td>
                        <Button onClick={edit}>Submit </Button>
                    </td>
                </tr>
            </table>


        </>
    )

}

export default CommentOrder;