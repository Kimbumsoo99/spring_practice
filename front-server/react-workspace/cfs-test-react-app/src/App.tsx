import React, { useEffect, useState } from "react";
import { getFcmToken, onMessageListener } from "./firebase";

const App: React.FC = () => {
    const [token, setToken] = useState<string | null>("");
    const [notification, setNotification] = useState<{ title: string; body: string }>({ title: "", body: "" });

    useEffect(() => {
        getFcmToken().then((currentToken) => {
            if (currentToken) {
                setToken(currentToken);
                fetch("https://localhost:8443/api/fcm/register", {
                    // 서버 URL 확인
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ token: currentToken }),
                }).catch((error) => console.error("Error:", error));
            }
        });

        onMessageListener()
            .then((payload) => {
                setNotification({ title: payload.data.title, body: payload.data.body });
            })
            .catch((err) => console.log("failed: ", err));
    }, []);

    const sendTestNotification = () => {
        fetch("https://localhost:8443/api/fcm/sendTest")
            .then(() => console.log("Test notification sent"))
            .catch((error) => console.error("Error:", error));
    };

    return (
        <div>
            <h1>FCM with React</h1>
            <p>Token: {token}</p>
            <h2>Notification:</h2>
            <p>Title: {notification.title}</p>
            <p>Body: {notification.body}</p>
            <button onClick={sendTestNotification}>Send Test Notification</button>
        </div>
    );
};

export default App;
