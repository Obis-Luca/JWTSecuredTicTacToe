<h1>Tic-Tac-Toe Application with React, Spring Boot, MySQL, JWT Tokens, and Spring Security</h1>

This is a full-stack Tic-Tac-Toe application built with React for the front-end and Spring Boot for the back-end. The back-end uses MySQL for data storage, JWT tokens for authentication, and Spring Security for authentication and authorization.

<h2>JWT Token Authentication</h2>
Registration: Users can register by providing a username and password. This information is stored securely in the MySQL database.

<h3>Authentication:</h3> Upon successful Login, users receive a JSON Web Token (JWT). This token must be included in the Authorization header of subsequent requests to authenticate the user.

<h3>Authorization:</h3> Spring Security validates the JWT with every request and grants access to protected endpoints. Unauthorized access attempts are denied with appropriate HTTP status codes.

<h3>Logout Functionality</h3>
Logout: Users can log out by clearing the JWT token stored in the client-side stored in local storage. This invalidates the token and requires users to re-authenticate to access protected endpoints. Uses a custom Spring-boot LogoutHandler.



<h2>Multiplayer Support</h2>

**Playing with Two Separate Browsers**
Real-time Gameplay: Two players can play against each other from separate browsers. The application supports real-time updates of the game board and turn-based gameplay.

<h2>Error Handling and Custom Exceptions</h2>
Customized error handling using @RestControllerAdvice @ExceptionHandler. Also customized my own business error codes and a GlobalExceptionHandler that also contains custom exceptions.

<h3>Technologies Used</h3>

**Front-end:**

<ul>
<li>React</li>
<li>React Router</li>
<li>Axios (for API calls)</li>
<li>Bootstrap (for styling)</li>
</ul>

**Back-end:**

<ul>
<li>Spring Boot</li>
<li>Spring Security (JWT Authentication)</li>
<li>Spring Data JPA</li>
<li>MySQL</li>
</ul>
