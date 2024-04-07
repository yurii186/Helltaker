package test.sk.tuke.gamestudio.game;

import main.sk.tuke.gamestudio.game.helltaker.gamecore.GameManager;
import main.sk.tuke.gamestudio.game.helltaker.observer.GameObserver;
import main.sk.tuke.gamestudio.game.helltaker.utils.UserInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class GameManagerTest {
    private GameManager gameManager;
    private UserInterface uiMock;
    private GameObserver gameObserverMock;

    @BeforeEach
    void setUp() {
        uiMock = mock(UserInterface.class);
        gameObserverMock = mock(GameObserver.class);
        //gameManager = new GameManager(uiMock, gameObserverMock);

        // Настройка мока UserInterface для возвращения "e" при запросе ввода,
        // имитируя пользователя, желающего выйти из игры.
        when(uiMock.requestInput()).thenReturn("test");
    }

    @Test
    void testGameFlow() {
        // Вызовите startGame и проверьте взаимодействие с моками
        gameManager.setLevel(10);
        gameManager.startGame();

        // Проверяем, что были сделаны вызовы к UI для завершения игры
        verify(uiMock).onGameEnd(anyInt());
        verify(uiMock, atLeastOnce()).displayMessage(anyString());

        // Проверяем, что игра запросила у пользователя ввод, хотя бы один раз,
        // и пользователь ввел "e" для выхода.
        verify(uiMock, atLeastOnce()).requestInput();
    }
}
