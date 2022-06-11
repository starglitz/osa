import axios from "axios";
import SprintsAxiosClient from "./clients/SprintsAxiosClient";

export const ApiService = {
  upload,
};

async function upload(data) {
  return await SprintsAxiosClient.post("http://localhost:8080/upload", data);
}
