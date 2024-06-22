import React, { useEffect, useState } from "react";
import "./Board.css";
import "../App.css";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import axios from "axios";
import { useAuth } from "../Auth/AuthProvider";

const Board = () => {
	const [board, setBoard] = useState([]);
	const [status, setStatus] = useState("ongoing");
	const [activeUsers, setActiveUsers] = useState(0);
	const [isXNext, setIsXNext] = useState(true);
	const { logout, turn } = useAuth();
	const history = useNavigate();

	useEffect(() => {
		const fetchData = async () => {
			const token = localStorage.getItem("token");

			try {
				const response = await axios.get("http://localhost:8080/game", {
					headers: {
						"Content-Type": "application/json",
						Authorization: `Bearer ${token}`,
					},
				});

				const dataArray = response.data.split(",");
				const board = dataArray[0].split("");
				const status = dataArray[1];
				const turn = JSON.parse(dataArray[2]);

				setBoard(board);
				setStatus(status);
				setIsXNext(turn);
			} catch (error) {
				console.error("Error fetching game data:", error);
			}
		};

		fetchData(); // Initial fetch
		const interval = setInterval(fetchData, 1500); // Fetch every 2 seconds

		return () => clearInterval(interval); // Cleanup on unmount
	}, []);

	useEffect(() => {
		const fetchData = async () => {
			try {
				const response = await axios.get("http://localhost:8080/auth/active-users");
				setActiveUsers(response.data);
			} catch (error) {
				console.error("Error fetching game data:", error);
			}
		};

		fetchData();
		const interval = setInterval(fetchData, 2000);

		return () => clearInterval(interval);
	}, []);

	const handleLogout = async () => {
		const token = localStorage.getItem("token");
		try {
			const response = await axios.delete("http://localhost:8080/auth/logout", {
				headers: {
					"Content-Type": "application/json",
					Authorization: `Bearer ${token}`,
				},
			});

			console.log("Logout successful:", response.data);
			logout();
			//history("/");
		} catch (error) {
			console.error("Logout failed:", error.response ? error.response.data : error.message);
		}
	};

	const handleClick = async (index) => {
		if (board[index] === "X" || board[index] === "O" || status !== "ongoing") return;

		const next = isXNext ? "X" : "O";
		console.log(turn);
		if ((next === "X" && turn !== "playerX") || (next === "O" && turn !== "playerO")) {
			return;
		}

		const token = localStorage.getItem("token");
		const move = { movePosition: index, player: next };

		try {
			const response = await axios.post("http://localhost:8080/game", move, {
				headers: {
					"Content-Type": "application/json",
					Authorization: `Bearer ${token}`,
				},
			});
			const data = response.data.split(";");
			const newBoard = data[0].split("");
			const newStatus = data[1];
			const newTurn = JSON.parse(data[2]);

			setBoard(newBoard);
			setStatus(newStatus);
			setIsXNext(newTurn);
		} catch (error) {
			console.error("Error:", error.response ? error.response.data : error.message);
		}
	};

	const handlePlayAgain = async () => {
		const token = localStorage.getItem("token");

		try {
			const response = await axios.post(
				"http://localhost:8080/game/reset",
				{},
				{
					headers: {
						"Content-Type": "application/json",
						Authorization: `Bearer ${token}`,
					},
				}
			);
			console.log(respone.data);
		} catch (error) {
			console.error("Error:", error.response ? error.response.data : error.message);
		}
	};

	const renderCell = (index) => {
		return (
			<button className="cell" onClick={() => handleClick(index)}>
				{board[index]}
			</button>
		);
	};

	return (
		<div className="App">
			<header className="App-header">
				<h1>Tic-Tac-Toe</h1>

				{activeUsers === 1 ? (
					<div className="waiting-message">
						<p>Waiting for another player to join...</p>
					</div>
				) : (
					<div className="board">
						{board.map((_, index) => (
							<div key={index} className="board-cell">
								{renderCell(index)}
							</div>
						))}
					</div>
				)}

				{status !== "ongoing" && (
					<div className="game-status">
						<p>{status}</p>
						<button type="button" className="btn btn-primary mt-3" onClick={handlePlayAgain}>
							Play again!
						</button>
					</div>
				)}

				<div className="text-center">
					<button type="button" className="btn btn-primary mt-3" onClick={handleLogout}>
						Logout
					</button>
				</div>
			</header>
		</div>
	);
};

export default Board;
