import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { OpenVidu, Session, StreamEvent, Publisher, Subscriber } from "openvidu-browser";
import axios from "axios";
import OpenViduVideoComponent from "./OpenViduVideoComponent.tsx";

const APPLICATION_SERVER_URL = import.meta.env.VITE_APPLICATION_SERVER_URL;

const SessionPage: React.FC = () => {
    const location = useLocation();
    const { mySessionId, myUserName } = location.state || {};
    const [session, setSession] = useState<Session | undefined>();
    const [mainStreamManager, setMainStreamManager] = useState<Publisher | undefined>();
    const [subscribers, setSubscribers] = useState<Subscriber[]>([]);

    useEffect(() => {
        joinSession();
        return () => leaveSession();
    }, []);

    const createSession = async (sessionId: string) => {
        console.log("createSession", `${APPLICATION_SERVER_URL}/api/video/sessions`);
        const response = await axios.post(`${APPLICATION_SERVER_URL}/api/video/sessions`, {
            customSessionId: sessionId,
        });
        console.log("createSession response");
        console.log(response);
        return response.data; // sessionId를 반환
    };

    const getToken = async (sessionId: string) => {
        console.log("getToken", `${APPLICATION_SERVER_URL}/api/video/sessions/${sessionId}/connections`);
        const response = await axios.post(`${APPLICATION_SERVER_URL}/api/video/sessions/${sessionId}/connections`);
        console.log("getToken response");
        console.log(response);
        return response.data;
    };

    const joinSession = async () => {
        console.log("joinSession", mySessionId, myUserName);
        const OV = new OpenVidu();
        const session = OV.initSession();

        session.on("streamCreated", (event: StreamEvent) => {
            const subscriber = session.subscribe(event.stream, undefined);
            setSubscribers((prevSubscribers) => [...prevSubscribers, subscriber]);
        });

        session.on("streamDestroyed", (event: StreamEvent) => {
            setSubscribers((prevSubscribers) => prevSubscribers.filter((subscriber) => subscriber !== event.stream.streamManager));
        });

        // mySessionId를 사용하여 세션을 생성하고 세션 ID를 받아옴
        const sessionId = await createSession(mySessionId);

        // 토큰을 받아옴
        const token = await getToken(sessionId);
        await session.connect(token, { clientData: myUserName });

        const publisher = OV.initPublisher(undefined, {
            audioSource: undefined,
            videoSource: undefined,
            publishAudio: true,
            publishVideo: true,
            resolution: "640x480",
            frameRate: 30,
            insertMode: "APPEND",
            mirror: true,
        });

        session.publish(publisher);

        setSession(session);
        setMainStreamManager(publisher);
    };

    const leaveSession = () => {
        if (session) {
            session.disconnect();
            setSession(undefined);
            setMainStreamManager(undefined);
            setSubscribers([]);
        }
    };

    return (
        <div>
            {session ? (
                <div>
                    <div>{mainStreamManager && <OpenViduVideoComponent streamManager={mainStreamManager} />}</div>
                    <div>
                        {subscribers.map((sub, i) => (
                            <OpenViduVideoComponent key={i} streamManager={sub} />
                        ))}
                    </div>
                    <button onClick={leaveSession}>Leave Session</button>
                </div>
            ) : (
                <div>
                    <h1>Session {mySessionId}</h1>
                    <h2>Username: {myUserName}</h2>
                    <button onClick={joinSession}>Join Session</button>
                </div>
            )}
        </div>
    );
};

export default SessionPage;
