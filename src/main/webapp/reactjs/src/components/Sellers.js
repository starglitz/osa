import { SellersService } from "../services/SellersService";
import {useEffect, useState} from "react";
import SellerCard from "./SellerCard";

const Sellers = () => {

    const [sellers, setSellers] = useState([]);

    useEffect(() => {
        fetchSellers();
    }, []);

    async function fetchSellers() {
        try {
            const response = await SellersService.getSellers();
            setSellers(response.data);
            console.log(response.data);
        } catch (error) {
            console.error(`Error loading all sellers !: ${error}`);
        }
    }


    return (
        <>
            <div className="flex-container">

            {sellers.map((s) =>
                <div className="flex-child">
                <SellerCard
                    key={s.id} id={s.id} sellerName={s.sellerName} name={s.name}
                    address={s.address} email={s.email} rating={(Math.round(s.rating * 100) / 100).toFixed(2)}/>
                </div>
            )}
            </div>
        </>
    )

}

export default Sellers;
