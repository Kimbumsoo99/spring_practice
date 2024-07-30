// src/App.tsx
import React, { useEffect, useState } from "react";
import { getToken, onMessageListener } from "./firebase";
import axios from "axios";

const App: React.FC = () => {
    const [isTokenFound, setTokenFound] = useState(false);
    const [notification, setNotification] = useState<{ title: string; body: string }>({ title: "", body: "" });

    useEffect(() => {
        getToken(setTokenFound);
    }, []);

    useEffect(() => {
        onMessageListener()
            .then((payload) => {
                setNotification({ title: payload.notification?.title || "", body: payload.notification?.body || "" });
                console.log(payload);
            })
            .catch((err) => console.log("failed: ", err));
    }, []);

    const sendNotification = async () => {
        try {
            const response = await axios.post("https://localhost:8443/api/notifications/send", {
                token: "RECIPIENT_FCM_TOKEN", // 여기에 실제 수신자의 FCM 토큰을 사용
                title: "Test Notification",
                body: "This is a test notification from the frontend.",
            });
            console.log("Notification sent successfully:", response.data);
        } catch (error) {
            console.error("Error sending notification:", error);
        }
    };

    return (
        <div>
            {isTokenFound ? <p>Notification permission enabled.</p> : <p>Need notification permission.</p>}
            <h2>Notification:</h2>
            <p>{notification.title}</p>
            <p>{notification.body}</p>
            <button onClick={sendNotification}>Send Notification</button>
        </div>
    );
};

export default App;
