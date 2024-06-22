import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./AuthProvider"; // Adjust the path as necessary

const PrivateRoute = () => {
	const { isAuthenticated } = useAuth();

	return isAuthenticated ? <Outlet /> : <Navigate to="/" />;
};

export default PrivateRoute;
