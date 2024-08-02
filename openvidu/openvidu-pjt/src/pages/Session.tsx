import React, { useEffect, useRef, useState } from 'react';
import { OpenVidu } from 'openvidu-browser';
import axios from 'axios';

const SessionComponent = ({ sessionName }) => {
    const videoContainerRef = useRef(null);
    const [publisher, setPublisher] = useState(null);
    const [subscribers, setSubscribers] = useState([]);
    const [session, setSession] = useState(null);

    useEffect(() => {
        if (session) {
            session.on('streamCreated', (event) => {
                const subscriber = session.subscribe(event.stream, undefined);
                setSubscribers((prevSubscribers) => [...prevSubscribers, subscriber]);
            });

            session.on('streamDestroyed', (event) => {
                setSubscribers((prevSubscribers) => prevSubscribers.filter(sub => sub !== event.stream.streamManager));
            });
        }

        return () => {
            if (session) {
                session.off('streamCreated');
                session.off('streamDestroyed');
            }
        };
    }, [session]);

    useEffect(() => {
        if (publisher && videoContainerRef.current) {
            const videoElement = document.createElement('video');
            videoElement.autoplay = true;
            videoElement.controls = false;
            videoElement.srcObject = publisher.stream.getMediaStream();
            videoContainerRef.current.appendChild(videoElement);
        }
    }, [publisher]);

    useEffect(() => {
        if (subscribers.length > 0 && videoContainerRef.current) {
            subscribers.forEach((subscriber) => {
                const videoElement = document.createElement('video');
                videoElement.autoplay = true;
                videoElement.controls = false;
                videoElement.srcObject = subscriber.stream.getMediaStream();
                videoContainerRef.current.appendChild(videoElement);
            });
        }
    }, [subscribers]);

    const startSession = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/video/get-token?sessionName=${sessionName}`);
            const token = response.data;
            console.log('Token received:', token);

            if (!token) {
                throw new Error('Failed to retrieve token');
            }

            const OV = new OpenVidu();
            const mySession = OV.initSession();

            mySession.on('streamCreated', (event) => {
                const subscriber = mySession.subscribe(event.stream, undefined);
                setSubscribers((prevSubscribers) => [...prevSubscribers, subscriber]);
            });

            mySession.on('streamDestroyed', (event) => {
                setSubscribers((prevSubscribers) => prevSubscribers.filter(sub => sub !== event.stream.streamManager));
            });

            await mySession.connect(token, { clientData: 'My User Name' });
            const myPublisher = OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true,     // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true,     // Whether you want to start publishing with your video enabled or not
                resolution: '640x480',  // The resolution of your video
                frameRate: 30,          // The frame rate of your video
                insertMode: 'APPEND',   // How the video is inserted in the target element 'video-container'
                mirror: false           // Whether to mirror your local video or not
            });

            mySession.publish(myPublisher);
            setPublisher(myPublisher);
            setSession(mySession);
        } catch (error) {
            console.error('There was an error connecting to the session:', error);
        }
    };

    return (
        <div>
            <button onClick={startSession}>Start Session</button>
            <div ref={videoContainerRef}></div>
        </div>
    );
};

export default SessionComponent;
