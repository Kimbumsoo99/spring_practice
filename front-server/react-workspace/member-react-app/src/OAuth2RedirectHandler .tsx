import React, { useEffect } from "react";
import { useHistory } from "react-router-dom";

const OAuth2RedirectHandler = () => {
    const history = useHistory();

    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        const token = params.get("token");

        if (token) {
            localStorage.setItem("jwtToken", token);
            history.push("/");
        } else {
            // Handle error case
            history.push("/login");
        }
    }, [history]);

    return <div>Redirecting...</div>;
};

export default OAuth2RedirectHandler;
