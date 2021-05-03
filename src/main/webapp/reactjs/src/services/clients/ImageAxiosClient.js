import axios from "axios";
import { TokenService } from "../TokenService";
import { AuthenticationService } from "../AuthenticationService";

// API klijent se kreira ka specifičnom endpoint-u, uz sve ono što je uvek neophodno slati



const ImageAxiosClient = axios.create({
    baseURL: `${process.env.REACT_APP_SPRINTS_BACKEND_SERVER}`,
});

// Dodaj token na svaki zahtev ka Sprints backendu, ako je korisnik ulogovan
ImageAxiosClient.interceptors.request.use(function success(config) {
    const token = TokenService.getToken();
    if (token) {
        if (TokenService.didTokenExpire()) {
            alert("Token je istekao");
            AuthenticationService.logout();
            return false;
        }
        config.headers["Authorization"] = "Bearer " + token;
        config.headers["content-type"] = 'multipart/form-data'
    }
    return config;
});

// U slučaju da se sa Sprints backenda vrati forbidden, token je istekao te izloguj korisnika
ImageAxiosClient.interceptors.response.use(
    function success(response) {
        return response;
    },
    function failure(error) {
        const token = TokenService.getToken();
        if (token) {
            if (error.response && error.response.status === 403) {
                AuthenticationService.logout();
            }
        }
        throw error;
    }
);

export default ImageAxiosClient;