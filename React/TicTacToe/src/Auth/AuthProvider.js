import React, { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
	const [isAuthenticated, setIsAuthenticated] = useState(false);
	const [turn, setTurn] = useState(null);

	const login = (playerTurn) => {
		setIsAuthenticated(true);
		setTurn(playerTurn);
	};

	const logout = () => {
		setIsAuthenticated(false);
		setTurn(null);
	};

	return <AuthContext.Provider value={{ isAuthenticated, turn, login, logout }}>{children}</AuthContext.Provider>;
};

export const useAuth = () => useContext(AuthContext);
