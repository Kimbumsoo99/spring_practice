// src/firebase.ts
import { initializeApp } from "firebase/app";
import { getMessaging, getToken as getFirebaseToken, onMessage } from "firebase/messaging";
import firebaseConfig from "./firebase-config";

// Firebase 초기화
const firebaseApp = initializeApp(firebaseConfig);
const messaging = getMessaging(firebaseApp);

// FCM 토큰 요청 함수
export const getToken = (setTokenFound: React.Dispatch<React.SetStateAction<boolean>>) => {
    return getFirebaseToken(messaging, { vapidKey: import.meta.env.VITE_FIREBASE_VAPID_KEY })
        .then((currentToken) => {
            if (currentToken) {
                console.log("FCM Token:", currentToken);
                setTokenFound(true);
                // 서버로 토큰 전송
            } else {
                console.log("No registration token available. Request permission to generate one.");
                setTokenFound(false);
            }
        })
        .catch((err) => {
            console.log("An error occurred while retrieving token. ", err);
            setTokenFound(false);
        });
};

// FCM 메시지 수신 함수
export const onMessageListener = () =>
    new Promise((resolve) => {
        onMessage(messaging, (payload) => {
            resolve(payload);
        });
    });
