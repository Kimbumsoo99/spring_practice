import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Home: React.FC = () => {
  const navigate = useNavigate();
  const [mySessionId, setMySessionId] = useState<string>('SessionA');
  const [myUserName, setMyUserName] = useState<string>('Participant' + Math.floor(Math.random() * 100));

  const goToSession = () => {
    navigate('/session', { state: { mySessionId, myUserName } });
  };

  return (
    <div>
      <h1>OpenVidu React Application</h1>
      <input
        type="text"
        value={mySessionId}
        onChange={(e) => setMySessionId(e.target.value)}
        placeholder="Session ID"
      />
      <input
        type="text"
        value={myUserName}
        onChange={(e) => setMyUserName(e.target.value)}
        placeholder="User Name"
      />
      <button onClick={goToSession}>Join Session</button>
    </div>
  );
};

export default Home;
