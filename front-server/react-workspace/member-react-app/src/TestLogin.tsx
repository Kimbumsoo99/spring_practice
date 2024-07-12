import React, { useState } from "react";
import axios from "axios";

// axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.withCredentials = true;

const Login: React.FC = () => {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [error, setError] = useState<string | null>(null);

    const onFinish = (event: React.FormEvent) => {
        event.preventDefault();

        axios({
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            url: "http://localhost:8080/api/user/login",
            data: {
                username: username,
                password: password,
            },
        })
            .then((res) => {
                console.log(res);
                let accessToken = res.headers["access"];
                console.log("access", accessToken);
                console.log(res.headers);
                localStorage.setItem("Token", accessToken);
                // console.log(res.headers)
                console.log(res.headers["access"]);
            })
            .catch((err) => {
                console.log(err);
                setError("Login failed. Please check your credentials and try again.");
            });
    };

    const onOAuth2Login = (code) => {
        window.location.href = `http://localhost:8080/oauth2/authorization/${code}`;
    };

    return (
        <div style={styles.container}>
            <h2>Login</h2>
            <form onSubmit={onFinish} style={styles.form}>
                <div style={styles.formGroup}>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="username"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                        style={styles.input}
                    />
                </div>
                <div style={styles.formGroup}>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        style={styles.input}
                    />
                </div>
                {error && <p style={styles.error}>{error}</p>}
                <button type="submit" style={styles.button}>
                    Login
                </button>
            </form>
            <button type="button" style={styles.button} onClick={() => onOAuth2Login("naver")}>
                Naver
            </button>
            <button type="button" style={styles.button} onClick={() => onOAuth2Login("google")}>
                Google
            </button>
        </div>
    );
};

const styles: { [key: string]: React.CSSProperties } = {
    container: {
        maxWidth: "400px",
        margin: "0 auto",
        padding: "20px",
        textAlign: "center",
        border: "1px solid #ccc",
        borderRadius: "8px",
        boxShadow: "0 0 10px rgba(0, 0, 0, 0.1)",
    },
    form: {
        display: "flex",
        flexDirection: "column",
    },
    formGroup: {
        marginBottom: "15px",
    },
    input: {
        padding: "10px",
        fontSize: "16px",
        borderRadius: "4px",
        border: "1px solid #ccc",
    },
    button: {
        padding: "10px 15px",
        fontSize: "16px",
        color: "#fff",
        backgroundColor: "#007bff",
        border: "none",
        borderRadius: "4px",
        cursor: "pointer",
    },
    error: {
        color: "red",
        marginBottom: "15px",
    },
};

export default Login;
