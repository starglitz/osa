import SprintsAxiosClient from "./SprintsAxiosClient";
import { TokenService } from "./TokenService";

export const AuthenticationService = {
  login,
  logout,
  getRole,
};

async function login(userCredentials) {
  let status = "200";
  const response = await SprintsAxiosClient.post(
    "http://localhost:8080/login",
    userCredentials
  ).catch(function (error) {
    if (error.response) {
      console.log(error.response.status);
      if (error.response.status == "404") {
        status = "404";
        return "404";
      } else if (error.response.status == "403") {
        status = "403";
        return "403";
      }
    }
  });

  if (status != "403" && status != "404") {
    const decoded_token = TokenService.decodeToken(response.data);
    if (decoded_token) {
      TokenService.setToken(response.data);

      return "200";
    } else {
    }
  }
  return status;
}

function logout() {
  TokenService.removeToken();
  window.location.assign("/");
}

function getRole() {
  const token = TokenService.getToken();
  const decoded_token = token ? TokenService.decodeToken(token) : null;
  if (decoded_token) {
    return decoded_token.role.authority;
  } else {
    return null;
  }
}
