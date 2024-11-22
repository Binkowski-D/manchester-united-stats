package com.manutd.reddevilstats.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manutd.reddevilstats.model.PlayerData;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PlayerRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlayerRepository.class);
    private List<PlayerData> playersData = new ArrayList<>();
    private File file = new File("src/main/resources/united.json");

    @PostConstruct
    public void init(){
        loadPlayersData();
    }

    private void loadPlayersData(){
        ObjectMapper mapper = new ObjectMapper();

        if(!file.exists()){
            logger.error("JSON file with player data not found at: {}", file.getAbsolutePath());
            playersData = Collections.emptyList();
            return;
        }

        try{
            playersData = List.of(mapper.readValue(file, PlayerData[].class));
            logger.info("Successfully loaded players data from JSON file.");
            playersData.forEach(playerData -> logger.debug("Player: {} \nStatistics: {}",
                    playerData.getPlayer().getName(),
                    playerData.getStatistics().stream()
                            .map(stat -> "Appearences: " + stat.getGames().getAppearences() +
                                    ", Minutes: " + stat.getGames().getMinutes() +
                                    ", Position: " + stat.getGames().getPosition() +
                                    ", Rating: " + stat.getGames().getRating() +
                                    ", Goals: " + stat.getGoals().getTotal() +
                                    ", Assists: " + stat.getGoals().getAssists() +
                                    ", Saves: " + stat.getGoals().getSaves() +
                                    ", Passes: " + stat.getPasses().getTotal() +
                                    ", Key Passes: " + stat.getPasses().getKey() +
                                    ", Passes Accuracy: " + stat.getPasses().getAccuracy() +
                                    ", Yellow Cards: " + stat.getCards().getYellow() +
                                    ", Red Cards: " + stat.getCards().getRed())
                            .collect(Collectors.joining("; "))));


        } catch (Exception e) {
            logger.error("Error while parsing JSON file: {}", e.getMessage(), e);
            playersData = Collections.emptyList();
        }
    }

    public List<PlayerData> getPlayersData(){
        return playersData;
    }
}
