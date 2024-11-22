package com.manutd.reddevilstats.service;

import com.manutd.reddevilstats.model.Games;
import com.manutd.reddevilstats.model.Player;
import com.manutd.reddevilstats.model.PlayerData;
import com.manutd.reddevilstats.model.Statistics;
import com.manutd.reddevilstats.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllPlayersTest() {
        PlayerData player1 = new PlayerData(new Player("Player 1", 32, "Country 2", 10, "photo"), List.of());
        PlayerData player2 = new PlayerData(new Player("Player 2", 21, "Country 1", 6, "photo"), List.of());

        List<PlayerData> mockPlayers = List.of(player1, player2);

        when(playerRepository.getPlayersData()).thenReturn(mockPlayers);

        List<PlayerData> result = playerService.getAllPlayersData();

        assertEquals(2, result.size(), "There should be two players in the list.");
        assertEquals("Player 1", result.get(0).getPlayer().getName(), "The first player should be named 'Player 1'.");
        assertEquals(6, result.get(1).getPlayer().getNumber(), "The second player should have number '6'.");

        verify(playerRepository, times(1)).getPlayersData();
    }

    @Test
    public void getPlayersByPositionTest() {
        PlayerData player1 = new PlayerData(new Player("Player 1", 32, "Country 2", 10, "photo"),
                List.of(new Statistics(new Games(0, 0, "Midfielder", "0.0"), null, null, null))
        );

        PlayerData player2 = new PlayerData(new Player("Player 2", 21, "Country 1", 12, "photo"),
                List.of(new Statistics(new Games(0, 0, "Attacker", "0.0"), null, null, null))
        );


        List<PlayerData> mockPlayers = List.of(player1, player2);

        when(playerRepository.getPlayersData()).thenReturn(mockPlayers);

        List<PlayerData> result = playerService.getPlayersByPosition("Attacker");

        assertEquals(1, result.size(), "There should be one attacker in the list.");
        assertEquals("Player 2", result.get(0).getPlayer().getName(), "The attacker should be 'Player 2'.");
    }

    @Test
    public void getPlayersByNationalityTest() {
        PlayerData player1 = new PlayerData(new Player("Player 1", 32, "England", 10, "photo"), List.of());
        PlayerData player2 = new PlayerData(new Player("Player 2", 21, "England", 6, "photo"), List.of());

        List<PlayerData> mockPlayers = List.of(player1, player2);

        when(playerRepository.getPlayersData()).thenReturn(mockPlayers);

        List<PlayerData> result = playerService.getPlayersByNationality("England");

        assertEquals(2, result.size(), "There should be two players from England.");
    }

    @Test
    public void getPlayersByAgeTest(){
        PlayerData player1 = new PlayerData(new Player("Player 1", 28, "England", 10, "photo"), List.of());
        PlayerData player2 = new PlayerData(new Player("Player 2", 21, "England", 6, "photo"), List.of());

        List<PlayerData> mockPlayers = List.of(player1, player2);

        when(playerRepository.getPlayersData()).thenReturn(mockPlayers);

        List<PlayerData> result = playerService.getPlayersByAge("26-30");

        assertEquals(1, result.size(), "There should be one player with age range '26-30'.");
        assertEquals("Player 1", result.get(0).getPlayer().getName(), "Player name should be 'Player 1'.");
    }

    @Test
    public void getTopRatedPlayersTest() {
        PlayerData player1 = new PlayerData(new Player("Player 1", 32, "Country 2", 10, "photo"),
                List.of(new Statistics(new Games(0, 0, "Midfielder", "6.7"), null, null, null))
        );

        PlayerData player2 = new PlayerData(new Player("Player 2", 21, "Country 1", 12, "photo"),
                List.of(new Statistics(new Games(0, 0, "Attacker", "8.2"), null, null, null))
        );

        PlayerData player3 = new PlayerData(new Player("Player 3", 22, "Country 1", 8, "photo"),
                List.of(new Statistics(new Games(0, 0, "Attacker", "6.8"), null, null, null))
        );

        List<PlayerData> mockPlayers = List.of(player1, player2, player3);

        when(playerRepository.getPlayersData()).thenReturn(mockPlayers);

        List<PlayerData> result = playerService.getTopRatedPlayers(3);

        assertEquals("Player 2", result.get(0).getPlayer().getName(), "The top-rated player should be 'Player 2'.");
        assertEquals("Player 3", result.get(1).getPlayer().getName(), "The second top-rated player should be 'Player 3'.");
        assertEquals("Player 1", result.get(2).getPlayer().getName(), "The third top-rated player should be 'Player 1'.");
    }

    @Test
    public void filterPlayersTest() {
        PlayerData player1 = new PlayerData(
                new Player("Player 1", 28, "England", 10, "photo"),
                List.of(new Statistics(new Games(0, 0, "Midfielder", "7.0"), null, null, null))
        );

        PlayerData player2 = new PlayerData(
                new Player("Player 2", 22, "Brazil", 8, "photo"),
                List.of(new Statistics(new Games(0, 0, "Attacker", "6.8"), null, null, null))
        );

        PlayerData player3 = new PlayerData(
                new Player("Player 3", 19, "England", 12, "photo"),
                List.of(new Statistics(new Games(0, 0, "Defender", "7.5"), null, null, null))
        );

        List<PlayerData> mockPlayers = List.of(player1, player2, player3);

        when(playerRepository.getPlayersData()).thenReturn(mockPlayers);

        List<PlayerData> result = playerService.filterPlayers("Midfielder", null, null);
        assertEquals(1, result.size(), "Should return one midfielder.");
        assertEquals("Player 1", result.get(0).getPlayer().getName(), "The midfielder should be 'Player 1'.");

        result = playerService.filterPlayers(null, "England", null);
        assertEquals(2, result.size(), "Should return two players from England.");

        result = playerService.filterPlayers(null, null, "<20");
        assertEquals(1, result.size(), "Should return one player under 20.");
        assertEquals("Player 3", result.get(0).getPlayer().getName(), "The player under 20 should be 'Player 3'.");

        result = playerService.filterPlayers("Defender", "England", "<20");
        assertEquals(1, result.size(), "Should return one defender from England under 20.");
        assertEquals("Player 3", result.get(0).getPlayer().getName(), "The player should be 'Player 3'.");
    }

    @Test
    public void getPlayerByNumberTest() {
        PlayerData player1 = new PlayerData(new Player("Player 1", 32, "Country 2", 10, "photo"), List.of());

        when(playerRepository.getPlayersData()).thenReturn(List.of(player1));

        PlayerData result = playerService.getPlayerByNumber(10);

        assertEquals("Player 1", result.getPlayer().getName(), "Player with number 10 should be 'Player 1'.");
    }

    @Test
    public void getPlayerByNumberNotFoundTest() {
        when(playerRepository.getPlayersData()).thenReturn(List.of());

        Exception exception = assertThrows(NoSuchElementException.class, () -> playerService.getPlayerByNumber(10));

        assertEquals("Player with number 10 not found", exception.getMessage());
    }
}
