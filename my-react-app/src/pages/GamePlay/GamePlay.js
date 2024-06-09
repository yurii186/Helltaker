import React, { useState, useEffect , useRef} from "react";
import './GamePlay.css';  // Убедитесь, что стили правильно подключены


function GamePlay({chapterLevel, returnMain, Exit, namePlayer}) {
    const maxLevel = 8;
    const [action, setAction] = useState('idle');
    const [direction, setDirection] = useState('right');
    const [actionScelet, setActionScelet] = useState('idle');
    const [hiddenSpike, setHiddenSpike] = useState(true);
    const [finishGame, setFinishGame] = useState(false);
    const [gameState, setGameState] = useState({
        level: 0,
        score: 0,
        step: 0,
        currentLevelBoard: []
    });
    const [topPlayers, setTopPlayers] = useState([]);
    const [score, setScore] = useState(0);
    const [timer, setTimer] = useState(false);

    const [secondsElapsed, setSecondsElapsed] = useState(0);
    const intervalRef = useRef(null);

    useEffect(() => {
        if (timer) {
            startTimer();
        } else {
            stopTimer();
        }

        return () => stopTimer(); // Очищаем интервал при размонтировании компонента
    }, [timer]);

    const startTimer = () => {
        if (intervalRef.current !== null) return; // Уже запущен

        intervalRef.current = setInterval(() => {
            setSecondsElapsed(prevSeconds => prevSeconds + 1);
        }, 1000);
    };

    const stopTimer = () => {
        if (intervalRef.current !== null) {
            clearInterval(intervalRef.current);
            intervalRef.current = null;
        }
    };

    useEffect(() => {
        startGame();
    }, []);

    useEffect(() => {
        if(finishGame === false) {
            let lastKeyPressTime = 0;  // Время последнего нажатия клавиши

            const handleKeyDown = (event) => {
                const now = Date.now();
                if (now - lastKeyPressTime > 1000) {  // Разрешаем нажатие если прошла 1 секунда
                    lastKeyPressTime = now;  // Обновляем время последнего нажатия клавиши
                    const {currentLevelBoard, level} = gameState;  // Доступ к нужным переменным состояния
                    sendKeyPressToServer(event.key, currentLevelBoard, level);
                }
            };

            window.addEventListener('keydown', handleKeyDown);
            return () => window.removeEventListener('keydown', handleKeyDown);
        }
    }, [gameState]);  // Добавляем gameState в массив зависимостей useEffect

    function getRandomNumber() {
        return Math.floor(Math.random() * 11) + 90;
    }

    const sendKeyPressToServer = async (key, currentLevelBoard, level) => {
        if (key === 'k')
            setAction('win');
        try {
            const postResponse = await fetch('http://localhost:8080/game/move', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({level: level, input: key})
            });

            if (!postResponse.ok) {
                throw new Error('Error sending key press to server');
            }
            const postData = await postResponse.json();
            console.log('Server response:', postData);
            if(postData === -35){
                setAction('idle');
                setDirection('right');
                returnMain();
                return;
            }
            else if(postData === -36){
                setAction('idle');
                setDirection('right');
                Exit();
                return;
            }

            // После успешной отправки данных, запросить обновлённое игровое поле
            const getResponse = await fetch('http://localhost:8080/game/map', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            const getMove = await fetch('http://localhost:8080/game/animation', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            const getHeroX = await fetch('http://localhost:8080/game/heroX', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            const getHeroY = await fetch('http://localhost:8080/game/heroY', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            const getSteps = await fetch('http://localhost:8080/game/steps', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });


            const HeroX = await getHeroX.json();
            const HeroY = await getHeroY.json();
            const move = await getMove.json();

            if (!getResponse.ok) {
                throw new Error('Failed to fetch the updated game board');
            }

            console.log('TEST:', move);
            await checkMove(move, HeroX, HeroY);

            const updatedBoard = await getResponse.json();
            const steps = await getSteps.json();
            if (steps === -33) {
                const step = 0;

                setTimeout(() => {
                    setGameState(prevState => ({
                        ...prevState, // сохраняем предыдущее состояние
                        step: step // обновляем только step
                    }));

                    console.log('Updated board:', updatedBoard);
                    setGameState(prevState => ({
                        ...prevState,
                        currentLevelBoard: updatedBoard
                    }));
                    setAction('win');
                }, 500);

                fetchHidden().then(hidden => {
                    setHiddenSpike(hidden)
                });
                if (level + 1 !== maxLevel) {
                    const response = await fetch(`http://localhost:8080/game/level${gameState.level + 1}`);
                    const data = await response.json();

                    const getStep = await fetch('http://localhost:8080/game/steps', {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    });
                    const newStepsResponse = await getStep.json(); // предполагается, что getSteps - это другой запрос для получения шагов
                    console.log("NEW STEP:", newStepsResponse);
                    const newScore = gameState.score + getRandomNumber();
                    setTimeout(() => {
                        if (response.ok) {
                            if (Array.isArray(data)) {
                                setGameState({
                                    level: gameState.level + 1, // увеличиваем уровень на 1
                                    currentLevelBoard: data,
                                    score: newScore,
                                    step: newStepsResponse // обновляем значение шагов
                                });
                            }
                        }
                        setAction('idle');
                    }, 3000);
                } else {
                    setFinishGame(true)
                    gameState.score = score + getRandomNumber();
                    setTimeout(() => {
                        setGameState({
                            level: null, // увеличиваем уровень на 1
                            currentLevelBoard: null,
                            score: null,
                            step: null // обновляем значение шагов
                        });
                        setAction('idle');
                        setDirection('right');
                    }, 3000);
                }
            } else {
                setTimeout(() => {
                    setGameState(prevState => ({
                        ...prevState, // сохраняем предыдущее состояние
                        step: steps // обновляем только step
                    }));

                    console.log('Updated board:', updatedBoard);
                    setGameState(prevState => ({
                        ...prevState,
                        currentLevelBoard: updatedBoard
                    }));
                }, 500);
            }
        } catch (error) {
            console.error('Error during the key press handling:', error);
        }
    };


    const startGame = async () => {
        fetchGameBoard();
        setTimer(true);
    };

    const fetchGameBoard = async () => {
        try {

            if (chapterLevel === 0) {
                const response = await fetch('http://localhost:8080/game/start');
                const data = await response.json();
                if (response.ok) {
                    if (Array.isArray(data)) {
                        setGameState(prevState => ({
                            ...prevState,
                            currentLevelBoard: data,
                        }));
                    } else {
                        throw new Error("Invalid data structure received");
                    }
                } else {
                    throw new Error(data.message || "Error fetching initial game state");
                }
            } else {

                gameState.level = chapterLevel;
                const response = await fetch(`http://localhost:8080/game/level${chapterLevel}`);
                const data = await response.json();
                if (response.ok) {
                    if (Array.isArray(data)) {
                        setGameState(prevState => ({
                            ...prevState,
                            currentLevelBoard: data,
                        }));
                    } else {
                        throw new Error("Invalid data structure received");
                    }
                } else {
                    throw new Error(data.message || "Error fetching initial game state");
                }
                fetchHidden().then(hidden => {
                    setHiddenSpike(hidden)
                });
            }
            const getSteps = await fetch('http://localhost:8080/game/steps', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            const steps = await getSteps.json();
            setGameState(prevState => ({
                ...prevState, // сохраняем предыдущее состояние
                step: steps // обновляем только step
            }));
        } catch (error) {
            console.error("Failed to fetch initial game state:", error);
        }
    };

    async function fetchHidden() {
        try {
            const response = await fetch('http://localhost:8080/game/hidden');
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const hidden = await response.json(); // Парсинг JSON ответа в объект JavaScript
            console.log('Hidden status:', hidden);
            return hidden; // Возвращает значение hidden
        } catch (error) {
            console.error('Failed to fetch hidden status:', error);
        }
    }

    const checkMove = (move, HeroX, HeroY) => {
        if (move === -1)
            handleMoveLeft()
        else if (move === -2)
            handleMoveRight()
        else if (move === -3)
            handleMoveDown()
        else if (move === -4)
            handleMoveUp()
        else if (move === -5)
            handlePunchSkeletonLeft(HeroX, HeroY)
        else if (move === -6)
            handlePunchSkeletonRight(HeroX, HeroY)
        else if (move === -7)
            handlePunchSkeletonDown(HeroX, HeroY)
        else if (move === -8)
            handlePunchSkeletonUp(HeroX, HeroY);
        else if (move === -13)
            handlePunchRockLeft(HeroX, HeroY)
        else if (move === -14)
            handlePunchRockRight(HeroX, HeroY)
        else if (move === -15)
            handlePunchRockDown(HeroX, HeroY)
        else if (move === -16)
            handlePunchRockUp(HeroX, HeroY)
        else if (move === -17)
            handleCantPunchRockLeft()
        else if (move === -18)
            handleCantPunchRockRight()
        else if (move === -19)
            handleCantPunchRockDown()
        else if (move === -20)
            handleCantPunchRockUp()
        else if (move === -21)
            handlePunchRockLeft(HeroX, HeroY)
        else if (move === -22)
            handlePunchRockRight(HeroX, HeroY)
        else if (move === -23)
            handlePunchRockDown(HeroX, HeroY)
        else if (move === -24)
            handlePunchRockUp(HeroX, HeroY)
        else if(move === -29)
            handleCantMoveLeft()
        else if(move === -30)
            handleCantMoveRight()
        else if(move === -31 || move === -32)
            handleCantMoveUpDown()
        else if(move === -33)
            setAction('idle')
        else if(move === -34) {
            setAction('idle')
            setDirection('right')
        }
    }

    const handleMoveDown = () => {
        setAction('moveDown'); // Устанавливаем действие движения вниз
        setTimeout(() => {
            // Здесь ваш код для обновления позиции на доске
            setAction('idle'); // Возвращаем в исходное состояние после завершения анимации
        }, 500); // Длительность анимации
    };
    const handleMoveUp = () => {
        setAction('moveUp'); // Устанавливаем действие движения вниз
        setTimeout(() => {
            // Здесь ваш код для обновления позиции на доске
            setAction('idle'); // Возвращаем в исходное состояние после завершения анимации
        }, 500); // Длительность анимации
    };
    const handleMoveLeft = () => {
        setAction('moveLeft'); // Устанавливаем действие движения вниз
        setDirection('left');
        setTimeout(() => {
            // Здесь ваш код для обновления позиции на доске
            setAction('idle'); // Возвращаем в исходное состояние после завершения анимации
        }, 500); // Длительность анимации
    };

    const handleMoveRight = () => {
        setAction('moveRight'); // Устанавливаем действие движения вниз
        setDirection('right');
        setTimeout(() => {
            // Здесь ваш код для обновления позиции на доске
            setAction('idle'); // Возвращаем в исходное состояние после завершения анимации
        }, 500); // Длительность анимации
    };

    const handlePunchSkeletonLeft = (HeroX, HeroY) => {

        const rowIndex = HeroX; // Индекс строки
        const cellIndex = HeroY - 1; // Индекс столбца
        // Строим уникальный идентификатор для скелета с использованием индексов строки и столбца
        const skeletonId = `skeleton-${rowIndex}-${cellIndex}`;
        // Получаем элемент скелета по его уникальному идентификатору
        const skeletonElement = document.getElementById(skeletonId);
        setAction('punch');
        setDirection('left');
        if (skeletonElement) {
            // Добавляем класс, который запускает анимацию
            skeletonElement.classList.add('moving', 'left', 'skull');
        }
        setTimeout(() => {
            setAction('idle');
            if (skeletonElement) {
                // Добавляем класс, который запускает анимацию
                skeletonElement.classList.remove('moving', 'left', 'skull');
            }
        }, 500);
    };

    const handlePunchSkeletonRight = (HeroX, HeroY) => {

        const rowIndex = HeroX; // Индекс строки
        const cellIndex = HeroY + 1; // Индекс столбца
        // Строим уникальный идентификатор для скелета с использованием индексов строки и столбца
        const skeletonId = `skeleton-${rowIndex}-${cellIndex}`;
        // Получаем элемент скелета по его уникальному идентификатору
        const skeletonElement = document.getElementById(skeletonId);
        setAction('punch');
        setDirection('right');
        if (skeletonElement) {
            // Добавляем класс, который запускает анимацию
            skeletonElement.classList.add('moving', 'right', 'skull');
        }
        setTimeout(() => {
            setAction('idle');
            if (skeletonElement) {
                // Добавляем класс, который запускает анимацию
                skeletonElement.classList.remove('moving', 'right', 'skull');
            }
        }, 500);
    };

    const handlePunchSkeletonDown = (HeroX, HeroY) => {

        const rowIndex = HeroX + 1; // Индекс строки
        const cellIndex = HeroY; // Индекс столбца
        // Строим уникальный идентификатор для скелета с использованием индексов строки и столбца
        const skeletonId = `skeleton-${rowIndex}-${cellIndex}`;
        // Получаем элемент скелета по его уникальному идентификатору
        const skeletonElement = document.getElementById(skeletonId);
        setAction('punch');
        //  setDirection('left');
        if (skeletonElement) {
            // Добавляем класс, который запускает анимацию
            skeletonElement.classList.add('moving', 'down', 'skull');
        }
        setTimeout(() => {
            setAction('idle');
            if (skeletonElement) {
                // Добавляем класс, который запускает анимацию
                skeletonElement.classList.remove('moving', 'down', 'skull');
            }
        }, 500);
    };

    const handlePunchSkeletonUp = (HeroX, HeroY) => {

        const rowIndex = HeroX - 1; // Индекс строки
        const cellIndex = HeroY; // Индекс столбца
        // Строим уникальный идентификатор для скелета с использованием индексов строки и столбца
        const skeletonId = `skeleton-${rowIndex}-${cellIndex}`;
        // Получаем элемент скелета по его уникальному идентификатору
        const skeletonElement = document.getElementById(skeletonId);
        setAction('punch');
        // setDirection('left');
        if (skeletonElement) {
            // Добавляем класс, который запускает анимацию
            skeletonElement.classList.add('moving', 'up', 'skull');
        }
        setTimeout(() => {
            setAction('idle');
            if (skeletonElement) {
                // Добавляем класс, который запускает анимацию
                skeletonElement.classList.remove('moving', 'up', 'skull');
            }
        }, 500);
    };


    const handlePunchRockLeft = (HeroX, HeroY) => {

        const rowIndex = HeroX; // Индекс строки
        const cellIndex = HeroY - 1; // Индекс столбца
        // Строим уникальный идентификатор для скелета с использованием индексов строки и столбца
        const rockId = `rock-${rowIndex}-${cellIndex}`;
        // Получаем элемент скелета по его уникальному идентификатору
        const rockElement = document.getElementById(rockId);
        setAction('punch');
        setDirection('left');
        if (rockElement) {
            // Добавляем класс, который запускает анимацию
            rockElement.classList.add('moving', 'left', 'rock');
        }
        setTimeout(() => {
            setAction('idle');
            if (rockElement) {
                // Добавляем класс, который запускает анимацию
                rockElement.classList.remove('moving', 'left', 'rock');
            }
        }, 500);
    };

    const handlePunchRockRight = (HeroX, HeroY) => {

        const rowIndex = HeroX; // Индекс строки
        const cellIndex = HeroY + 1; // Индекс столбца
        // Строим уникальный идентификатор для скелета с использованием индексов строки и столбца
        const rockId = `rock-${rowIndex}-${cellIndex}`;
        // Получаем элемент скелета по его уникальному идентификатору
        const rockElement = document.getElementById(rockId);
        setAction('punch');
        setDirection('right');
        if (rockElement) {
            // Добавляем класс, который запускает анимацию
            rockElement.classList.add('moving', 'right', 'rock');
        }
        setTimeout(() => {
            setAction('idle');
            if (rockElement) {
                // Добавляем класс, который запускает анимацию
                rockElement.classList.remove('moving', 'right', 'rock');
            }
        }, 500);
    };


    const handlePunchRockDown = (HeroX, HeroY) => {

        const rowIndex = HeroX + 1; // Индекс строки
        const cellIndex = HeroY; // Индекс столбца
        // Строим уникальный идентификатор для скелета с использованием индексов строки и столбца
        const rockId = `rock-${rowIndex}-${cellIndex}`;
        // Получаем элемент скелета по его уникальному идентификатору
        const rockElement = document.getElementById(rockId);
        setAction('punch');
        if (rockElement) {
            // Добавляем класс, который запускает анимацию
            rockElement.classList.add('moving', 'down', 'rock');
        }
        setTimeout(() => {
            setAction('idle');
            if (rockElement) {
                // Добавляем класс, который запускает анимацию
                rockElement.classList.remove('moving', 'down', 'rock');
            }
        }, 500);
    };

    const handlePunchRockUp = (HeroX, HeroY) => {

        const rowIndex = HeroX - 1; // Индекс строки
        const cellIndex = HeroY; // Индекс столбца
        // Строим уникальный идентификатор для скелета с использованием индексов строки и столбца
        const rockId = `rock-${rowIndex}-${cellIndex}`;
        // Получаем элемент скелета по его уникальному идентификатору
        const rockElement = document.getElementById(rockId);
        setAction('punch');
        if (rockElement) {
            // Добавляем класс, который запускает анимацию
            rockElement.classList.add('moving', 'up', 'rock');
        }
        setTimeout(() => {
            setAction('idle');
            if (rockElement) {
                // Добавляем класс, который запускает анимацию
                rockElement.classList.remove('moving', 'up', 'rock');
            }
        }, 500);
    };


    const handleCantPunchRockLeft = () => {

        setAction('punch');
        setDirection('left');

        setTimeout(() => {
            setAction('idle');
        }, 500);
    };

    const handleCantPunchRockRight = () => {

        setAction('punch');
        setDirection('right');

        setTimeout(() => {
            setAction('idle');
        }, 500);
    };


    const handleCantPunchRockDown = () => {

        setAction('punch');

        setTimeout(() => {
            setAction('idle');
        }, 500);
    };

    const handleCantPunchRockUp = () => {

        setAction('punch');

        setTimeout(() => {
            setAction('idle');
        }, 500);
    };


    // const handlePunchRockForSpikeLeft = (HeroX, HeroY) => {
    //
    //     const rowIndex = HeroX; // Индекс строки
    //     const cellIndex = HeroY - 1; // Индекс столбца
    //     const rockId = `spike-and-rock-${rowIndex}-${cellIndex}`;
    //     const rockElement = document.getElementById(rockId);
    //
    //     setAction('punch');
    //     setDirection('left')
    //
    //     if (rockElement) {
    //         rockElement.classList.add('animate-rock-left');
    //         setTimeout(() => {
    //             setAction('idle')
    //             rockElement.classList.remove('animate-rock-left');
    //         }, 500); // Удаление класса анимации после ее завершения
    //     }
    // };
    //
    // const handlePunchRockForSpikeRight = (HeroX, HeroY) => {
    //
    //     const rowIndex = HeroX; // Индекс строки
    //     const cellIndex = HeroY + 1; // Индекс столбца
    //     const rockId = `spike-and-rock-${rowIndex}-${cellIndex}`;
    //     const rockElement = document.getElementById(rockId);
    //
    //     setAction('punch');
    //     setDirection('right')
    //
    //     if (rockElement) {
    //         rockElement.classList.add('animate-rock-right');
    //         setTimeout(() => {
    //             setAction('idle')
    //             rockElement.classList.remove('animate-rock-right');
    //         }, 500); // Удаление класса анимации после ее завершения
    //     }
    // };
    //
    //
    // const handlePunchRockForSpikeDown = (HeroX, HeroY) => {
    //
    //     const rowIndex = HeroX + 1; // Индекс строки
    //     const cellIndex = HeroY;
    //     const rockId = `spike-and-rock-${rowIndex}-${cellIndex}`;
    //     const rockElement = document.getElementById(rockId);
    //
    //     setAction('punch');
    //
    //     if (rockElement) {
    //         rockElement.classList.add('animate-rock-down');
    //         setTimeout(() => {
    //             setAction('idle')
    //             rockElement.classList.remove('animate-rock-down');
    //         }, 500); // Удаление класса анимации после ее завершения
    //     }
    // };
    //
    // const handlePunchRockForSpikeUp = (HeroX, HeroY) => {
    //     const rowIndex = HeroX - 1;
    //     const cellIndex = HeroY;
    //     const rockId = `spike-and-rock-${rowIndex}-${cellIndex}`;
    //     const rockElement = document.getElementById(rockId);
    //
    //     setAction('punch');
    //
    //     if (rockElement) {
    //         rockElement.classList.add('animate-rock-up');
    //         setTimeout(() => {
    //             setAction('idle')
    //             rockElement.classList.remove('animate-rock-up');
    //         }, 500); // Удаление класса анимации после ее завершения
    //     }
    // };

    const handleCantMoveRight = () => {
        setAction('moveCantRight'); // Устанавливаем действие движения вниз
        setDirection('right');
        setTimeout(() => {
            // Здесь ваш код для обновления позиции на доске
            setAction('idle'); // Возвращаем в исходное состояние после завершения анимации
        }, 500); // Длительность анимации
    };

    const handleCantMoveLeft = () => {
        setAction('moveCantLeft'); // Устанавливаем действие движения вниз
        setDirection('left');
        setTimeout(() => {
            // Здесь ваш код для обновления позиции на доске
            setAction('idle'); // Возвращаем в исходное состояние после завершения анимации
        }, 500); // Длительность анимации
    };

    const handleCantMoveUpDown = () => {
        if (direction === 'right') {
            setAction('moveCantUp-DownRight'); // Устанавливаем действие движения вниз
            setTimeout(() => {
                // Здесь ваш код для обновления позиции на доске
                setAction('idle'); // Возвращаем в исходное состояние после завершения анимации
            }, 500); // Длительность анимации
        }
        else {
            setAction('moveCantUp-DownLeft'); // Устанавливаем действие движения вниз
            setTimeout(() => {
                // Здесь ваш код для обновления позиции на доске
                setAction('idle'); // Возвращаем в исходное состояние после завершения анимации
            }, 500); // Длительность анимации
        }
    };
    useEffect(() => {
        if (finishGame) {
            fetchTopPlayers(gameState.level).then(players => {
                setTopPlayers(players);
            });
        }
    }, [finishGame, gameState.level]);

    useEffect(() => {
        if(gameState.score !== null)
        setScore(score + gameState.score);
    }, [gameState.score]);


    async function fetchTopPlayers() {
        try {
            if(namePlayer !== null && gameState.score === null)
                await addScore(namePlayer,'Helltaker', score, new Date().toISOString());

            // Замените URL на актуальный адрес вашего сервера.
            const response = await fetch(`http://localhost:8080/api/score/Helltaker`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Error fetching top players:", error);
            return []; // Возвращаем пустой массив в случае ошибки
        }
    }



    const addScore = async (player, game, points, playedOn) => {
        const scoreData = { player, game, points, playedOn, time: secondsElapsed.toString() };

        try {
            const response = await fetch('http://localhost:8080/api/score', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(scoreData)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            console.log("Score added successfully");
        } catch (error) {
            console.error("Error adding score:", error);
        }
    };


    return (
        <div className="game-container">
            {finishGame ? (
                <div>
                    <div className="gif-container left"></div>
                    <div className="game-container-score">
                        <h1>Top 10 Players</h1>
                        {topPlayers.length > 0 ? (
                            <ul className="top-players">
                                {topPlayers.map((player, index) => (
                                    <li key={index}>
                                        Name: {player.player}, Points: {player.points}
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>No players have been loaded or the list is empty.</p>
                        )}
                        <div className="rating-display">
                            Your Current Rating: {score}
                        </div>
                        <button className="menu-button" onClick={returnMain}>Return to Main Menu</button>
                        <button className="exit-button" onClick={Exit}>Exit Game</button>
                    </div>
                    <div className="gif-container right"></div>
                </div>
            ) : (
                <>
                    <h1>Level: {gameState.level + 1}, Score: {gameState.score}, Step: {gameState.step}</h1>
                    <div className="game-board">
                        {Array.isArray(gameState.currentLevelBoard) && gameState.currentLevelBoard.map((row, rowIndex) => (
                            <div key={rowIndex} className="game-row">
                                {Array.isArray(row) && row.map((cell, cellIndex) => (
                                    <div key={cellIndex} className={`game-cell ${
                                        cell === '□' ? 'black-square' :
                                            cell === '.' ? 'red-square' : ''
                                    }`}>
                                        {cell === '■' ? <div className="rock-sprite" id={`rock-${rowIndex}-${cellIndex}`}></div> :
                                            cell === '♀' ? <div className={`player-sprite ${
                                                    action === 'idle' ? 'idle-shake' :
                                                        action === 'moveDown' ? (direction === 'right' ? 'moving down right' : 'moving down left') :
                                                            action === 'moveUp' ? (direction === 'right' ? 'moving up right' : 'moving up left') :
                                                                action === 'moveLeft' ? 'moving left' :
                                                                    action === 'moveRight' ? 'moving right' :
                                                                        action === 'moveCantRight' ? 'moving cant right' :
                                                                            action === 'moveCantLeft' ? 'moving cant left' :
                                                                                action === 'moveCantUp-DownLeft' ? 'moving cant up-down left' :
                                                                                    action === 'moveCantUp-DownRight' ? 'moving cant up-down right' :
                                                                                        action === 'punch' ? (direction === 'right' ? 'punch-right' : 'punch-left') :
                                                                                            action === 'win' ? 'player win' :
                                                                                                ''} ${direction === 'right' ? 'player-right' : 'player-left'}`
                                                }></div> :
                                                cell === '☠' ? <div id={`skeleton-${rowIndex}-${cellIndex}`}
                                                                    className={`skull-sprite ${
                                                                        actionScelet === 'idle' ? 'idle-shake-skull' : ''
                                                                    }`}></div> :
                                                    cell === '❤' ? <div className={`heart-sprite ${
                                                            gameState.level === 7 ? 'heart-sprite7 idle-level-7' :
                                                                `heart-sprite idle-level-${gameState.level}`
                                                        }`}></div> :
                                                        cell === '☰' ? <div className={`spike-sprite ${
                                                                hiddenSpike ? 'spike-open' : 'spike-static-open'
                                                            }`}></div> :
                                                            cell === '◙' ? <div className={`spike-and-rock-sprite ${
                                                                    hiddenSpike ? 'spike-open' : 'spike-static-open'
                                                                }`} id={`spike-and-rock-${rowIndex}-${cellIndex}`}></div> :
                                                                cell === '_' ? <div className={`spike-sprite ${
                                                                        hiddenSpike ? 'spike-close' : 'spike-static-close'
                                                                    }`}></div> :
                                                                    cell === '◘' ? <div className={`spike-and-rock-sprite ${
                                                                            hiddenSpike ? 'spike-close' : 'spike-static-close'
                                                                        }`} id={`spike-and-rock-${rowIndex}-${cellIndex}`}></div> :
                                                                        cell === '†' ? <>
                                                                                <div className={`spike-sprite ${
                                                                                    hiddenSpike ? 'spike-close' : ''
                                                                                }`}></div>
                                                                                <div className={`skull-sprite idle-shake-skull`}></div>
                                                                            </> :
                                                                            cell === '☒' ? <div className={`chest-sprite`}></div> :
                                                                                cell === '⚷' ? <div className={'key'}></div> : null}
                                    </div>
                                ))}
                            </div>
                        ))}
                    </div>
                </>
            )}
        </div>
    );
}
export default GamePlay;
