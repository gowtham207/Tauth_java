import React, { useEffect } from 'react';
import { Navigate } from 'react-router';

const CheckTokenComp:React.FC<{ children?: React.ReactNode }> = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = React.useState<boolean>(false);

    useEffect(() => {        
        const token = localStorage.getItem('authToken');
        if (token) {
            setIsAuthenticated(true);
        }
    }, []);

  return (
    <>
      {isAuthenticated ? children : <Navigate to="/login" replace />}
    </>
  );
};

export default CheckTokenComp;
