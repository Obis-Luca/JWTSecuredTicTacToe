import React, { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { MDBContainer, MDBInput, MDBBtn } from "mdb-react-ui-kit";
import "bootstrap/dist/css/bootstrap.min.css";
import { useAuth } from "../Auth/AuthProvider";

function LoginPage() {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState("");
	const history = useNavigate();
	const { login } = useAuth();

	const handleLogin = async () => {
		try {
			if (!username || !password) {
				setError("Please enter both username and password.");
				return;
			}

			const credentials = {
				username: username,
				password: password,
			};

			const response = await axios.post("http://localhost:8080/auth/authenticate", credentials);
			console.log("Login successful:", response.data);
			localStorage.setItem("token", response.data.token);
			login(response.data.player);

			setError(null);
			history("/board");
		} catch (error) {
			if (error.response) {
				console.error("Login failed:", error.response.data);

				const errorData = error.response.data;
				if (errorData.businessErrorDescription) setError(errorData.businessErrorDescription);
				else setError("Something went wrong. Please try again.");
			}
		}
	};

	return (
		<div className="d-flex justify-content-center align-items-center vh-100">
			<div className="border rounded-lg p-4" style={{ width: "500px", height: "auto" }}>
				<MDBContainer className="p-3">
					<h2 className="mb-4 text-center">Login Page</h2>
					<MDBInput wrapperClass="mb-4" placeholder="Name" id="name" value={username} type="name" onChange={(e) => setUsername(e.target.value)} />
					<MDBInput wrapperClass="mb-4" placeholder="Password" id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
					{error && <p className="text-danger">{error}</p>} {/* Render error message if exists */}
					<button className="mb-4 d-block btn-primary" style={{ height: "50px", width: "100%" }} onClick={handleLogin}>
						Sign in
					</button>
					<div className="text-center">
						<p>
							Not a member? <Link to={`/signup`}>Register</Link>
						</p>
					</div>
				</MDBContainer>
			</div>
		</div>
	);
}

export default LoginPage;
