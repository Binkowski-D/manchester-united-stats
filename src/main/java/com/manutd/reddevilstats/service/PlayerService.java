package com.manutd.reddevilstats.service;

import com.manutd.reddevilstats.model.PlayerData;
import com.manutd.reddevilstats.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerData> getAllPlayersData() {
        List<PlayerData> playersData = playerRepository.getPlayersData();
        if (playersData.isEmpty()) {
            logger.warn("Player data is unavailable. Returning an empty list.");
        }

        logger.info("Successfully retrieved {} players from the repository.", playersData.size());
        return playersData;
    }

    public List<PlayerData> getPlayersByPosition(String position) {
        List<PlayerData> filteredPlayers = getAllPlayersData().stream()
                .filter(playerData -> playerData.getStatistics().stream()
                        .anyMatch(stat -> stat.getGames().getPosition().equalsIgnoreCase(position)))
                .collect(Collectors.toList());
        logger.info("Found {} players with position '{}'.", filteredPlayers.size(), position);
        return filteredPlayers;
    }

    public List<PlayerData> getPlayersByNationality(String nationality) {
        List<PlayerData> filteredPlayers = getAllPlayersData().stream()
                .filter(player -> player.getPlayer().getNationality().equalsIgnoreCase(nationality))
                .collect(Collectors.toList());
        logger.info("Found {} players with nationality '{}'.", filteredPlayers.size(), nationality);
        return filteredPlayers;
    }

    public List<PlayerData> getPlayersByAge(String ageRange) {
        List<PlayerData> filteredPlayers = getAllPlayersData().stream()
                .filter(playerData -> {
                    int age = playerData.getPlayer().getAge();
                    return switch (ageRange) {
                        case "<20" -> age < 20;
                        case "20-25" -> age >= 20 && age <= 25;
                        case "26-30" -> age >= 26 && age <= 30;
                        case ">30" -> age > 30;
                        default -> false;
                    };
                })
                .collect(Collectors.toList());

        logger.info("Found {} players with age range '{}'.", filteredPlayers.size(), ageRange);
        return filteredPlayers;
    }

    public List<PlayerData> getTopRatedPlayers(int limit) {
        logger.info("getTopRatedPlayers called with limit: {}", limit);
        List<PlayerData> topRatedPlayers = getAllPlayersData().stream()
                .sorted(Comparator.comparing(
                        player -> Double.parseDouble(player.getStatistics().getFirst().getGames().getRating()),
                        Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toList());

        logger.info("Top Rated Players: {}", topRatedPlayers.stream()
                .map(playerData -> playerData.getPlayer().getName())
                .collect(Collectors.joining(", ")));

        return topRatedPlayers;
    }

    public List<PlayerData> getTopScorers(int limit) {
        List<PlayerData> topScorers = getAllPlayersData().stream()
                .sorted(Comparator.comparing(
                        player -> player.getStatistics().getFirst().getGoals().getTotal(),
                        Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toList());

        logger.info("Retrieved top {} scorers.", limit);
        return topScorers;
    }

    public List<PlayerData> getTopAssistants(int limit) {
        List<PlayerData> topAssistants = getAllPlayersData().stream()
                .sorted(Comparator.comparing(
                        player -> player.getStatistics().getFirst().getGoals().getAssists(),
                        Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toList());

        logger.info("Retrieved top {} assistants. ", limit);
        return topAssistants;
    }

    public List<PlayerData> filterPlayers(String position, String nationality, String ageRange) {
        List<PlayerData> filteredPlayers = getAllPlayersData();

        if (position != null && !position.isEmpty()) {
            filteredPlayers = filteredPlayers.stream()
                    .filter(player -> player.getStatistics().get(0).getGames().getPosition().equalsIgnoreCase(position))
                    .collect(Collectors.toList());
        }

        if (nationality != null && !nationality.isEmpty()) {
            filteredPlayers = filteredPlayers.stream()
                    .filter(player -> player.getPlayer().getNationality().equalsIgnoreCase(nationality))
                    .collect(Collectors.toList());
        }

        if (ageRange != null && !ageRange.isEmpty()) {
            filteredPlayers = getPlayersByAge(ageRange).stream()
                    .filter(filteredPlayers::contains)
                    .collect(Collectors.toList());
        }

        return filteredPlayers;
    }


    public PlayerData getPlayerByNumber(int number) {
        return getAllPlayersData().stream()
                .filter(playerData -> playerData.getPlayer().getNumber() == number)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Player with number " + number + " not found"));
    }
}
