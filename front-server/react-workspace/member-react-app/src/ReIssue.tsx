import React, { useState } from 'react';
import axios from 'axios';
import { withCookies, ReactCookieProps } from 'react-cookie';

const ReIssue: React.FC<ReactCookieProps> = () => {
  const onFinish = (event: React.FormEvent) => {
    event.preventDefault();

    axios({
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      url: 'http://localhost:8080/api/user/reissue',
      data: {
        
      },
    })
      .then((res) => {
        let accessToken = res.headers["access"];
        console.log(res.headers);
        localStorage.setItem("Token", accessToken);
        // console.log(res.headers)
        console.log(res.headers["access"]);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div>
      <button onClick={onFinish}>ReIssue</button>
    </div>
  );
};

export default withCookies(ReIssue);
