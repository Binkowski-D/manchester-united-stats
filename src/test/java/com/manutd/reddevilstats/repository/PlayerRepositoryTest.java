package com.manutd.reddevilstats.repository;

import com.manutd.reddevilstats.model.PlayerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerRepositoryTest {
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp(){
        playerRepository = new PlayerRepository();
    }

    @Test
    public void testLoadPlayersDataFromJson(){
        File testFile = new File("src/test/java/resources/united_test.json");
        ReflectionTestUtils.setField(playerRepository, "playersData", List.of());
        ReflectionTestUtils.setField(playerRepository, "file", testFile);
        ReflectionTestUtils.invokeMethod(playerRepository, "loadPlayersData");

        List<PlayerData> playersData = playerRepository.getPlayersData();
        assertNotNull(playersData, "Player data should not be null");
        assertEquals(1, playersData.size(), "Expected one player in the test file");

        PlayerData playerData = playersData.getFirst();
        assertEquals("Bruno Fernandes", playerData.getPlayer().getName(), "Players name should be 'Bruno Fernandes'");
        assertEquals(8, playerData.getPlayer().getNumber(), "Player number should be '8'");
    }

    @Test
    public void testLoadPlayersDataWithMissingFile(){
        File testFile = new File("src/test/java/resources/non-existing.json");
        ReflectionTestUtils.setField(playerRepository, "playersData", List.of());
        ReflectionTestUtils.setField(playerRepository, "file", testFile);
        ReflectionTestUtils.invokeMethod(playerRepository, "loadPlayersData");

        List<PlayerData> playersData = playerRepository.getPlayersData();
        assertTrue(playersData.isEmpty(), "Player data should be empty when json file does not exist");
    }

    @Test
    public void testLoadPlayersDataWithInvalidFile(){
        File testFile = new File("src/test/java/resources/invalid.json");
        ReflectionTestUtils.setField(playerRepository, "playersData", List.of());
        ReflectionTestUtils.setField(playerRepository, "file", testFile);
        ReflectionTestUtils.invokeMethod(playerRepository, "loadPlayersData");

        List<PlayerData> playersData = playerRepository.getPlayersData();
        assertTrue(playersData.isEmpty(), "Player data should be empty when json is invalid");
    }
}
