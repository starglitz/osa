import React, {useEffect, useState} from "react";
import {ArticlesService} from "../../services/ArticlesService";
import {DiscountService} from "../../services/DiscountService";
import Button from "@material-ui/core/Button";
import {Nav} from "react-bootstrap";
import NavigationBar from "../NavigationBar";
import {useHistory} from "react-router-dom";
import {AuthenticationService} from "../../services/AuthenticationService";

const DiscountsTable = () => {

    const [discounts, setDiscounts] = useState([])

    useEffect(() => {
        fetchDiscounts();
    }, []);

    async function fetchDiscounts() {
        try {
            let today = new Date()


            const response = await DiscountService.getDiscountByCurrentSeller()
            setDiscounts(response.data.filter(discount => dates.inRange(new Date(), discount.dateFrom, discount.dateTo)));

        } catch (error) {
            console.error(`Error loading discounts!: ${error}`);
        }
    }

    var dates = {
        convert:function(d) {
            return (
                d.constructor === Date ? d :
                    d.constructor === Array ? new Date(d[0],d[1],d[2]) :
                        d.constructor === Number ? new Date(d) :
                            d.constructor === String ? new Date(d) :
                                typeof d === "object" ? new Date(d.year,d.month,d.date) :
                                    NaN
            );
        },
        compare:function(a,b) {
            return (
                isFinite(a=this.convert(a).valueOf()) &&
                isFinite(b=this.convert(b).valueOf()) ?
                    (a>b)-(a<b) :
                    NaN
            );
        },
        inRange:function(d,start,end) {

            return (
                isFinite(d=this.convert(d).valueOf()) &&
                isFinite(start=this.convert(start).valueOf()) &&
                isFinite(end=this.convert(end).valueOf()) ?
                    start <= d && d <= end :
                    NaN
            );
        }
    }


    const history = useHistory()

    const createNew = () => {
        history.push("/createArticle")
    }

    const profile = () => {
        history.push("/profile")
    }

    const manageDisc = () => {
        history.push("/allDiscounts")
    }

    const logout = () => {
        AuthenticationService.logout();
    }

    async function deleteDisc(id) {
        try {
            await DiscountService.deleteDiscount(id)
            setDiscounts((discounts) => discounts.filter(disc => disc.id != id))
        } catch (error) {
            console.error(`Error ocurred while deleting discount with id ${id}: ${error}`);
        }
    }

    return (
        <>

            <NavigationBar>
                <Nav>
                    <Nav.Link eventKey={3} onClick={profile}>
                        Profile
                    </Nav.Link>
                    <Nav.Link href="#deets">Seller option</Nav.Link>

                    <Nav.Link eventKey={3} onClick={logout}>
                        Log out
                    </Nav.Link>

                    <Nav.Link eventKey={3} onClick={createNew}>
                        Add new article
                    </Nav.Link>

                    <Nav.Link eventKey={3} onClick={manageDisc}>
                        ManageDiscounts
                    </Nav.Link>


                </Nav>
            </NavigationBar>

            <table className="styled-table">
                <thead>
                <tr>
                    <td>Discount ID</td>
                    <td>Start date</td>
                    <td>End date</td>
                    <td>Description</td>
                    <td>Percentage</td>
                    <td>Articles on discount</td>
                    <td colSpan="2">Modify / Delete a discount</td>
                </tr>
                </thead>
                <tbody>

                {/*filter(disc => disc.dateFrom >= new Date()*/}
                {/*&& disc.dateTo <= new Date()).*/}
                {discounts.map((disc) =>

                    <tr>
                        <td>{disc.id}</td>
                        <td>{disc.dateFrom}</td>
                        <td>{disc.dateTo}</td>
                        <td>{disc.description}</td>
                        <td>{disc.percent}</td>
                        <td>
                            {disc.articles.map(article => <p>{article.name} x{article.amount} </p> )}
                        </td>
                        <td>
                            <Button variant="contained" color="primary">Modify</Button>
                        </td>
                        <td>
                            <Button variant="contained" color="secondary" onClick={() => deleteDisc(disc.id)}>Delete</Button>
                        </td>
                    </tr>

                )}
                </tbody>

            </table>
        </>
    )
}

export default DiscountsTable;