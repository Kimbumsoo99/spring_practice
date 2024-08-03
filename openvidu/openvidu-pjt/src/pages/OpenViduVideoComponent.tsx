import React, { useEffect, useRef } from "react";
import { StreamManager } from "openvidu-browser";

interface OpenViduVideoComponentProps {
    streamManager: StreamManager;
}

const OpenViduVideoComponent: React.FC<OpenViduVideoComponentProps> = ({ streamManager }) => {
    const videoRef = useRef<HTMLVideoElement>(null);

    useEffect(() => {
        if (videoRef.current) {
            streamManager.addVideoElement(videoRef.current);
        }
    }, [streamManager]);

    return <video ref={videoRef} autoPlay={true} />;
};

export default OpenViduVideoComponent;
