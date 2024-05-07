import React, { useState, useEffect, useCallback } from 'react';
import './Menu.css';
import GamePlay from "./GamePlay";

function MainMenu({ onLoginMenuSelect, namePlayer }) {
    // Состояния и методы, которые уже были в вашем компоненте...
    const [currentOption, setCurrentOption] = useState(0);
    const [startGame, setStartGame] = useState(false);
    const [comments, setComments] = useState([]);
    const [loading, setLoading] = useState(false);
    const [showComments, setShowComments] = useState(false);
    const [showCommentForm, setShowCommentForm] = useState(false);
    const [newComment, setNewComment] = useState('');
    const [averageRating, setAverageRating] = useState(0);
    const [showChapterSelect, setShowChapterSelect] = useState(false);
    const menuOptions = ['NEW GAME', 'CHAPTER SELECT', 'REVIEWS', 'LOGIN MENU'];
    const [chapterLevel , setChapterLevel] = useState(0);


    useEffect(() => {
        console.log("LEVEL = ", chapterLevel);
    }, [chapterLevel]);

    const handleChapterSelect = useCallback((level) => {
        console.log(`Chapter selected: Level ${level}`);
        setChapterLevel(level);
        setShowChapterSelect(false); // Assuming you want to hide the selector after choice
        setStartGame(true);
    }, []);

        const fetchComments = useCallback(async () => {
        setLoading(true);
        try {
            const response = await fetch('http://localhost:8080/api/comment/Helltaker');
            const data = await response.json();
            setComments(data);
            setShowComments(true);
        } catch (error) {
            console.error('Failed to fetch comments:', error);
        } finally {
            setLoading(false);
        }
    }, []);

    const confirmOption = useCallback((index) => {
        console.log(`Selected option: ${menuOptions[index]}`);
        switch (menuOptions[index]) {
            case 'LOGIN MENU':
                onLoginMenuSelect();
                break;
            case 'NEW GAME':
                setStartGame(true);
                break;
            case 'CHAPTER SELECT':
                setShowChapterSelect(true);
                break;
            case 'REVIEWS':
                fetchComments();
                break;
            default:
                break;
        }
    }, [onLoginMenuSelect, fetchComments]);


    const handleKeyPress = useCallback((event) => {
        switch (event.key) {
            case 'w':
            case 'ArrowUp':
                setCurrentOption(prev => (prev + menuOptions.length - 1) % menuOptions.length);
                break;
            case 's':
            case 'ArrowDown':
                setCurrentOption(prev => (prev + 1) % menuOptions.length);
                break;
            case 'Enter':
                confirmOption(currentOption);
                break;
            default:
                break;
        }
    }, [currentOption, menuOptions.length, confirmOption]);

    useEffect(() => {
        window.addEventListener('keydown', handleKeyPress);
        return () => {
            window.removeEventListener('keydown', handleKeyPress);
        };
    }, [handleKeyPress]);

    const handleSubmitComment = async (e) => {
        e.preventDefault();

        // Создание объекта комментария для отправки на сервер
        const commentToSend = {
            game: "Helltaker",  // Пример названия игры
            player: namePlayer, // Имя игрока, возможно, получаемое из состояния или контекста
            comment: newComment,
            commentedOn: new Date().toISOString() // Сериализуем текущую дату в формат ISO для отправки
        };

        try {
            const response = await fetch('http://localhost:8080/api/comment', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(commentToSend)
            });

            if (response.ok) {
                console.log('Comment successfully submitted');
                setComments([commentToSend, ...comments]); // Добавляем новый комментарий в начало массива
            } else {
                throw new Error('Failed to submit comment');
            }
        } catch (error) {
            console.error('Error submitting comment:', error);
        }

        setNewComment(''); // Очистка поля ввода после отправки
        setShowCommentForm(false); // Скрытие формы после отправки
    };

    const fetchAverageRating = useCallback(async () => {
        try {
            const response = await fetch('http://localhost:8080/api/rating/average/Helltaker');
            const avgRating = await response.json();
            setAverageRating(avgRating);
        } catch (error) {
            console.error('Failed to fetch average rating:', error);
        }
    }, []);

    const submitRating = async (rating) => {
        if (!namePlayer) return;

        const ratingData = {
            player: namePlayer,
            game: "Helltaker",
            rating,
            ratedOn: new Date().toISOString()  // Добавляем текущую дату и время в формате ISO
        };

        try {
            const response = await fetch('http://localhost:8080/api/rating', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(ratingData)
            });

            if (response.ok) {
                console.log('Rating successfully submitted');
                fetchAverageRating();
            } else {
                throw new Error('Failed to submit rating');
            }
        } catch (error) {
            console.error('Error submitting rating:', error);
        }
    };


    useEffect(() => {
        fetchAverageRating();
        window.addEventListener('keydown', handleKeyPress);
        return () => {
            window.removeEventListener('keydown', handleKeyPress);
        };
    }, [handleKeyPress, fetchAverageRating]);

    return (
        <div className="main-container">
            {startGame ? (
                <GamePlay chapterLevel={chapterLevel}/>
            ) : showChapterSelect ? (
                <div className="chapter-select-container">
                    <div className="sides-image-left"></div>  // Проверь названия классов, чтобы они соответствовали CSS
                    <div className="chapters-container">
                        {Array.from({length: 8}, (_, i) => (
                            <button key={i} className="chapter-button" onClick={() => handleChapterSelect(i)}>
                                Level {i + 1}
                            </button>
                        ))}
                        <button className="chapter-button" onClick={() => {
                            setShowChapterSelect(false);
                            setCurrentOption(0);
                        }}>Return to main menu
                        </button>
                    </div>
                    <div className="sides-image-right"></div>
                    // Проверь названия классов, чтобы они соответствовали CSS
                </div>
            ) : showComments ? (
                <>
                    <div className="side-images side-image-left"></div>
                    <div className="side-images side-image-right"></div>
                    <div className="comments-container">
                        {loading ? (
                            <p>Loading comments...</p>
                        ) : (
                            comments.map((comment, index) => (
                                <div key={index} className="comment">
                                    <p><strong>Comment by {comment.player}</strong>: {comment.comment}</p>
                                    <p>Date: {new Date(comment.commentedOn).toLocaleString('en-US')}</p>
                                </div>
                            ))
                        )}
                        {namePlayer ? (
                            <>
                                <button onClick={() => setShowCommentForm(!showCommentForm)}>
                                    {showCommentForm ? 'Hide Form' : 'Add Comment'}
                                </button>
                                {showCommentForm && (
                                    <form onSubmit={handleSubmitComment}>
                                    <textarea
                                        value={newComment}
                                        onChange={(e) => setNewComment(e.target.value)}
                                        placeholder="Enter your comment here..."
                                        required
                                    />
                                        <button type="submit">Submit</button>
                                    </form>
                                )}
                            </>
                        ) : (
                            <p>You must log in to leave a comment.</p>
                        )}
                        <button onClick={() => { setShowComments(false); setCurrentOption(0); }}>Return to Main Menu</button>
                    </div>
                </>
            ) : (
                <div>
                    <div className="menu-container">
                        {/* Background image content */}
                    </div>
                    <div className="buttons-container">
                        {menuOptions.map((option, index) => (
                            <div
                                key={index}
                                className={`menu-option ${index === currentOption ? 'active' : ''}`}
                                onClick={() => confirmOption(index)}
                                tabIndex={0}
                            >
                                {option}
                            </div>
                        ))}
                        <div className="menu-option">
                            <span className="rating-text">AVERAGE RATING: {averageRating}</span>
                            {Array.from({ length: 5 }, (_, index) => (
                                <span
                                    key={index}
                                    className={`star ${index < averageRating ? 'filled' : ''}`}
                                    style={{ cursor: namePlayer ? 'pointer' : 'default', verticalAlign: 'middle' }}
                                    onClick={namePlayer ? () => submitRating(index + 1) : null}
                                >
                            ☆
                            </span>
                            ))}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default MainMenu;