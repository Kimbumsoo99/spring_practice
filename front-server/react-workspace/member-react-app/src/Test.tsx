// src/components/Test.tsx
import React from 'react';
import axiosInstance from './AxiosInstance';

const Test: React.FC = () => {
  const test = async (event: React.FormEvent) => {
    event.preventDefault();

    try {
      const response = await axiosInstance.get('/my');
      
      console.log(response);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  return (
    <div>
      <button onClick={test}>Test</button>
    </div>
  );
};

export default Test;
