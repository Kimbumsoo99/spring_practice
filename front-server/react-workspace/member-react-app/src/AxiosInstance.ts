// src/axiosInstance.ts
import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 1000,
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('Token');
    console.log(token);
    
    config.headers['Content-Type'] = 'application/json';
    config.headers['access'] = `${token}`;

    console.log(config.headers)
    
    console.log(config);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response) => {
    if (response.status === 404) {
      console.log('404 페이지로 넘어가야 함!');
    }
    return response;
  },
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const tokenResponse = await axios({
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          url: 'http://localhost:8080/api/user/reissue',
        });

        const accessToken = tokenResponse.headers["access"];
        console.log(tokenResponse.headers);
        localStorage.setItem("Token", accessToken);

        axiosInstance.defaults.headers.common['access'] = `${accessToken}`;
        originalRequest.headers['access'] = `${accessToken}`;

        return axiosInstance(originalRequest);
      } catch (tokenError) {
        console.error('토큰 재발급 실패:', tokenError);
        // 선택적으로, 토큰 재발급 실패 시 처리 (예: 로그인 페이지로 리디렉션)
      }
    }

    return Promise.reject(error);
  }
);

export default axiosInstance;
