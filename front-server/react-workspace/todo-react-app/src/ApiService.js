import { AccountTree } from "@mui/icons-material";
import { API_BASE_URL } from "./api-config";

export function call(api, method, request) {
    let headers = new Headers({
        "Content-Type": "application/json",
    });

    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    if (accessToken && accessToken !== null) {
        headers.append("Authorization", accessToken);
    }
    let options = {
        headers: headers,
        url: API_BASE_URL + api,
        method: method,
    };

    if (request) {
        options.body = JSON.stringify(request);
    }

    console.log(options);

    return fetch(options.url, options)
        .then((response) => {
            console.log(response);
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 403) {
                // window.location.href = "/login";
            } else {
                new Error(response);
            }
        })
        .catch((error) => {
            console.log("http error");
            console.log(error);
        });
}

export function signin(userDTO) {
    console.log(userDTO);
    return call("/auth/signin", "POST", userDTO).then((res) => {
        console.log(res);
        if (res.token) {
            localStorage.setItem("ACCESS_TOKEN", res.token);
            window.location.href = "/";
        }
    });
}

export function signout() {
    localStorage.setItem("ACCESS_TOKEN", null);
    window.location.href = "/login";
}

export function signup(userDTO) {
    return call("/auth/signup", "POST", userDTO);
}
