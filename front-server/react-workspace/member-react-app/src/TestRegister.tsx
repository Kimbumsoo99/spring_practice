import React from 'react';
import axios from 'axios';
import { withCookies, ReactCookieProps } from 'react-cookie';

const ReIssue: React.FC<ReactCookieProps> = () => {
  const onFinish = async (event: React.FormEvent) => {
    event.preventDefault();

    try {
      const res = await axios({
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        url: 'http://localhost:8080/api/user/reissue',
        data: {},
      });

      const accessToken = res.headers["access-token"]; // 확인 필요
      if (accessToken) {
        localStorage.setItem("Token", accessToken);
        console.log('Access token reissued:', accessToken);
      } else {
        console.error('Access token not found in response headers.');
      }
    } catch (err) {
      console.error('Error reissuing access token:', err);
    }
  };

  return (
    <div>
      <button onClick={onFinish}>ReIssue</button>
    </div>
  );
};

export default ReIssue;
