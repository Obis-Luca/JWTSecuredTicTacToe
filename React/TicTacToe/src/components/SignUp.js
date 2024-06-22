import React, { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { MDBContainer, MDBInput, MDBBtn } from "mdb-react-ui-kit";
import "bootstrap/dist/css/bootstrap.min.css";
import LoginPage from "./LoginPage";
import { useAuth } from "../Auth/AuthProvider";

function SignupPage() {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [confirmPassword, setConfirmPassword] = useState("");
	const [error, setError] = useState("");
	const { login } = useAuth();
	const history = useNavigate();

	const handleSignup = async () => {
		try {
			if (!username || !password || !confirmPassword) {
				setError("Please fill in all fields.");
				return;
			}

			if (password !== confirmPassword) {
				setError("Passwords do not match.");
				return;
			}

			const credentials = {
				username: username,
				password: password,
			};

			const response = await axios.post("http://localhost:8080/auth/register", credentials);
			console.log("Signup successful:", response.data);
			setError(null);
			history("/");
		} catch (error) {
			console.error("Signup failed:", error.response ? error.response.data : error.message);
			setError(error.response ? error.response.data : "Something went wrong.");
		}
	};

	return (
		<div className="d-flex justify-content-center align-items-center vh-100">
			<div className="border rounded-lg p-4" style={{ width: "500px", height: "auto" }}>
				<MDBContainer className="p-3">
					<h2 className="mb-4 text-center">Sign Up Page</h2>
					<MDBInput wrapperClass="mb-4" placeholder="Username" id="username" value={username} type="text" onChange={(e) => setUsername(e.target.value)} />
					<MDBInput wrapperClass="mb-4" placeholder="Password" id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
					<MDBInput wrapperClass="mb-4" placeholder="Confirm Password" id="confirmPassword" type="password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
					{error && <p className="text-danger">{error}</p>}
					<button className="mb-4 d-block btn-primary" style={{ height: "50px", width: "100%" }} onClick={handleSignup}>
						Sign up
					</button>
					<div className="text-center">
						<p>
							Already have an account? <Link to={`/`}>Login</Link>
						</p>
					</div>
				</MDBContainer>
			</div>
		</div>
	);
}

export default SignupPage;
