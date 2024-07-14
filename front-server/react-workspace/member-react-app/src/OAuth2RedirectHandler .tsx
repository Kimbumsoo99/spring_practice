import React from "react";
import { Navigate } from "react-router-dom";

const OAuth2RedirectHandler = (props) => {
    const getUrlParameter = (name) => {
        const search = window.location.search;
        const params = new URLSearchParams(search);
        return params.get(name);
    };

    const token = getUrlParameter("token");

    console.log("토큰", token);

    if (token) {
        console.log("로컬 스토리지 토큰 저장", token);
        localStorage.setItem("ACCESS_TOKEN", token);
        return (
            <Navigate
                to={{
                    pathname: "/",
                    state: { from: props.location },
                }}
            />
        );
    } else {
        return (
            <Navigate
                to={{
                    pathname: "/login",
                    state: { from: props.location },
                }}
            />
        );
    }
};

export default OAuth2RedirectHandler;
