import axios from 'axios';
import SprintsAxiosClient from "./clients/SprintsAxiosClient";
import ImageAxiosClient from "./clients/ImageAxiosClient";

export const ApiService =
{
    upload
}

    // upload(data) {
    //     return axios.post("http://localhost:8080/upload", data);
    // }


    async function upload(data) {
        return await ImageAxiosClient.post("http://localhost:8080/upload", data)
    }