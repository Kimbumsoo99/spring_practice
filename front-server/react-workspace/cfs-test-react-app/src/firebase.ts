import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { getAnalytics } from "firebase/analytics";
import axios from "axios";
import toast from "react-hot-toast";

const firebaseConfig = {
    apiKey: import.meta.env.VITE_FIREBASE_API_KEY,
    authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN,
    projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID,
    storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET,
    messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
    appId: import.meta.env.VITE_FIREBASE_APP_ID,
    measurementId: import.meta.env.VITE_FIREBASE_MEASUREMENT_ID,
};

const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const messaging = getMessaging(app);

const customAxios = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL, // API base URL
    headers: {
        "Content-Type": "application/json",
    },
});

export const setPushEnabled = (enabled: boolean) => {
    localStorage.setItem("pushEnabled", JSON.stringify(enabled));
};

export const getFcmToken = async (): Promise<string | null> => {
    try {
        const registration = await navigator.serviceWorker.register("/firebase-messaging-sw.js");
        const currentToken = await getToken(messaging, {
            vapidKey: import.meta.env.VITE_FIREBASE_VAPID_KEY,
            serviceWorkerRegistration: registration,
        });
        if (currentToken) {
            localStorage.setItem("fcmToken", currentToken);
            console.log("FCM Token:", currentToken);
            await setting(currentToken);
            return currentToken;
        } else {
            setPushEnabled(false);
            console.log("No registration token available. Request permission to generate one.");
            return null;
        }
    } catch (err) {
        console.error("An error occurred while retrieving token. ", err);
        setPushEnabled(false);
        return null;
    }
};

const setting = async (token: string) => {
    const body = {
        token: token,
    };
    try {
        await customAxios.post("/fcm", body);
        toast.success("푸시 알림을 받습니다", {
            duration: 1000,
        });
        setTimeout(() => {}, 1000);
    } catch (err) {
        setPushEnabled(false);
    }
};

export const onMessageListener = (): Promise<any> =>
    new Promise((resolve) => {
        onMessage(messaging, (payload) => {
            console.log("Message received: ", payload);
            resolve(payload);
        });
    });
