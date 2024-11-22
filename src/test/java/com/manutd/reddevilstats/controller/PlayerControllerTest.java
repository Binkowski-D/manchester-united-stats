package com.manutd.reddevilstats.controller;

import com.manutd.reddevilstats.model.Player;
import com.manutd.reddevilstats.model.PlayerData;
import com.manutd.reddevilstats.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private Model model;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void showHomePage_NoAction() {
        when(playerService.getTopRatedPlayers(3)).thenReturn(List.of());
        when(playerService.getTopScorers(3)).thenReturn(List.of());
        when(playerService.getTopAssistants(3)).thenReturn(List.of());

        String viewName = playerController.showHomePage(null, null, null, null, model);

        assertEquals("index", viewName, "View name should be 'index'");
        verify(model).addAttribute("topRated", List.of());
        verify(model).addAttribute("topScorers", List.of());
        verify(model).addAttribute("topAssistants", List.of());
        verify(model).addAttribute("filteredPlayers", List.of());
    }

    @Test
    public void showHomePage_ViewAllAction() {
        PlayerData player1 = new PlayerData(new Player("Player 1", 28, "England", 10, "photo1"), List.of());
        PlayerData player2 = new PlayerData(new Player("Player 2", 25, "Brazil", 8, "photo2"), List.of());
        List<PlayerData> allPlayers = List.of(player1, player2);

        when(playerService.getTopRatedPlayers(3)).thenReturn(List.of());
        when(playerService.getTopScorers(3)).thenReturn(List.of());
        when(playerService.getTopAssistants(3)).thenReturn(List.of());
        when(playerService.getAllPlayersData()).thenReturn(allPlayers);

        String viewName = playerController.showHomePage("viewAll", null, null, null, model);

        assertEquals("index", viewName, "View name should be 'index'");
        verify(model).addAttribute("topRated", List.of());
        verify(model).addAttribute("topScorers", List.of());
        verify(model).addAttribute("topAssistants", List.of());
        verify(model).addAttribute("filteredPlayers", allPlayers);
    }

    @Test
    public void showHomePage_FilterAction() {
        PlayerData player1 = new PlayerData(new Player("Player 1", 28, "England", 10, "photo1"), List.of());
        List<PlayerData> filteredPlayers = List.of(player1);

        when(playerService.getTopRatedPlayers(3)).thenReturn(List.of());
        when(playerService.getTopScorers(3)).thenReturn(List.of());
        when(playerService.getTopAssistants(3)).thenReturn(List.of());
        when(playerService.filterPlayers("Attacker", "England", "26-30")).thenReturn(filteredPlayers);

        String viewName = playerController.showHomePage("filter", "Attacker", "England", "26-30", model);

        assertEquals("index", viewName, "View name should be 'index'");
        verify(model).addAttribute("topRated", List.of());
        verify(model).addAttribute("topScorers", List.of());
        verify(model).addAttribute("topAssistants", List.of());
        verify(model).addAttribute("filteredPlayers", filteredPlayers);
    }

    @Test
    public void showPlayerDetailsTest() {
        PlayerData mockPlayerData = new PlayerData(new Player("Player", 28, "England", 10, "photo"), List.of());
        when(playerService.getPlayerByNumber(10)).thenReturn(mockPlayerData);

        String viewName = playerController.showPlayerDetails(10, model);

        assertEquals("player-details", viewName, "View name should be 'player-details'");
        verify(model).addAttribute("player", mockPlayerData);
    }
}
