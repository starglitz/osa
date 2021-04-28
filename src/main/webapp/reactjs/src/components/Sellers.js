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
                    key={s.id} sellerName={s.sellerName} name={s.name}
                    address={s.address} email={s.email}/>
                </div>
            )}
            </div>
        </>
    )

}

export default Sellers;
