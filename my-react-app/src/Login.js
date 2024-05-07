import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './Login.css';
import MainMenu from "./Menu";

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [isLoginError, setLoginError] = useState(false);
    const [isRegisterError, setRegisterError] = useState(false);
    const [isCreateAccountClicked, setIsCreateAccountClicked] = useState(false);
    const [usernameValidationResult, setUsernameValidationResult] = useState(-2); // Стейт для результата проверки уникальности имени пользователя
    const [isMainMenuOpen, setIsMainMenuOpen] = useState(false);
    const [namePlayer, setNamePlayer] = useState(null);

    useEffect(() => {
        console.log("NAME = ", namePlayer);
    }, [namePlayer]);

    const handleLogin = async () => {
        setLoginError(true);
        try {
            // Используем переменные пути для username и password
            const response = await axios.get(`http://localhost:8080/api/connect/login/${username}/${password}`);
            console.log("USERNAME = ", username);
            if (response.data) {
                setNamePlayer(username);
                setMessage('Login successful.');
                setIsMainMenuOpen(true);
                // Redirect to game or dashboard
                // Пример редиректа: history.push('/dashboard');
            } else {
                setMessage('Login failed: Invalid username or password.');
            }
        } catch (error) {
            console.error('Login error:', error);
            if (error.response) {
                // Сервер вернул ошибку
                //console.log(error.response.data);
                //setMessage(`Login failed: ${error.response.status} ${error.response.data.message}`);
                setMessage('Login failed: Invalid username or password.')
            } else if (error.request) {
                // Запрос был сделан, но сервер не ответил
                setMessage('Login failed: Server did not respond');
            } else {
                // Ошибка в настройке запроса
                setMessage('Login failed: Error in setting up the request');
            }
        }
    };

    const handleCreateAccount = () => {
        setMessage('');
        setUsername('');
        setPassword('');
        setIsCreateAccountClicked(true);
        setLoginError(false)
        setRegisterError(false)
    };

    const handleNext = async () => {
        setMessage('');
        // Проверяем уникальность имени пользователя
        try {
            const response = await axios.get(`http://localhost:8080/api/connect/check/${username}`);
            const result = response.data;
            setUsernameValidationResult(result);
            if (result === 0) {
                // Если имя уникально, переходим к следующему шагу
                setIsCreateAccountClicked(true);
            } else if (result === 1) {
                setMessage('Username already exists. Choose another username.');
            } else if (result === -1) {
                setMessage('Username should be at least 3 characters long.');
            }
        } catch (error) {
            console.error('Error checking username uniqueness:', error);
            // Обработка ошибки
        }
    };

    const handleReturn = () => {
        setIsMainMenuOpen(false)
        setIsCreateAccountClicked(false);
        setRegisterError(false);
        setLoginError(false);
        setUsernameValidationResult(-2);
        setUsername('');
        setPassword('');
        setMessage('');
        setNamePlayer(null);
    };

    const handleFinishRegistration = async () => {
        setRegisterError(true);
        try {
            const response = await axios.get(`http://localhost:8080/api/connect/register/${username}/${password}`);
            // Дополнительные действия при успешной регистрации
            if (response.data) {
                setMessage('Registration successful.')
                // Задержка выполнения на одну секунду
                await new Promise(resolve => setTimeout(resolve, 1000));
                // Запуск функции handleReturn
                handleReturn();
            } else {
                setMessage('Registration failed: password must be at least 3 characters.')
            }
        } catch (error) {
            console.error('Error finishing registration:', error);
            if (error.response) {
                // Сервер вернул ошибку
                //  console.log(error.response.data);
                //  setMessage(`Registration failed: ${error.response.status} ${error.response.data.message}`);
                setMessage('Registration failed: password must be at least 3 characters.')
            } else if (error.request) {
                // Запрос был сделан, но сервер не ответил
                setMessage('Registration failed: Server did not respond');
            } else {
                // Ошибка в настройке запроса
                setMessage('Registration failed: Error in setting up the request');
            }
        }
    };

    const handleLoginGuest = async () => {
        setIsMainMenuOpen(true);
    }

    return (
        <div className="Login">
            {isMainMenuOpen ? (
                <div className="MainMenu">
                    <MainMenu onLoginMenuSelect={handleReturn} namePlayer={namePlayer}/>
                </div>
            ) : (
                <div className="login-container">

                    <div className="login-form">
                        <div className="message-container">
                            {isCreateAccountClicked && !isRegisterError && (
                                <div className="message">{message}</div>
                            )}

                            {isLoginError && !isRegisterError && (
                                <div className="message-login">{message}</div>
                            )}

                        </div>

                        {isRegisterError && (
                            <div className="message-register">{message}</div>
                        )}

                        {!isCreateAccountClicked && (
                            <>
                                <input
                                    type="text"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    placeholder="Username"
                                />
                                <input
                                    type="password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    placeholder="Password"
                                />
                            </>
                        )}

                        <div className="button-container">
                            {!isCreateAccountClicked && (
                                <>
                                    <button onClick={handleLogin}>Login</button>
                                    <button className="create-account-btn" onClick={handleCreateAccount}>
                                        Create Account
                                    </button>
                                </>
                            )}
                        </div>

                        {!isCreateAccountClicked && (
                            <button className="login-guest" onClick={handleLoginGuest}>Login Guest</button>
                        )}

                        <div className="message-container">
                            {isCreateAccountClicked && usernameValidationResult !== 0 && (
                                <div className="button-container-register">
                                    <input
                                        type="text"
                                        value={username}
                                        onChange={(e) => setUsername(e.target.value)}
                                        placeholder="Username"
                                    />
                                    <button onClick={handleNext}>Next</button>
                                    <button onClick={handleReturn}>Return</button>
                                </div>
                            )}

                            {isCreateAccountClicked && usernameValidationResult === 0 && (
                                <div className="button-container-register">
                                    <input
                                        type="password"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        placeholder="Password"
                                    />
                                    <button onClick={handleFinishRegistration}>Finish Registration</button>
                                    <button onClick={handleReturn}>Return</button>
                                </div>
                            )}
                        </div>
                        {/* <div className="message">{message}</div> */}
                    </div>
                </div>
            )}
        </div>
    );
}
export default Login;
