import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginPage from "./components/LoginPage";
import Board from "./components/Board";
import { AuthProvider } from "./Auth/AuthProvider";
import PrivateRoute from "./Auth/PrivateRoute";
import SignupPage from "./components/SignUp";

function App() {
	return (
		<AuthProvider>
			<Router>
				<Routes>
					<Route path="/" element={<LoginPage />} />
					<Route path="/signup" element={<SignupPage />} />
					<Route path="/" element={<PrivateRoute />}>
						<Route path="/board" element={<Board />} />
					</Route>
				</Routes>
			</Router>
		</AuthProvider>
	);
}

export default App;
