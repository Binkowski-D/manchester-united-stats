package com.manutd.reddevilstats.controller;

import com.manutd.reddevilstats.model.PlayerData;
import com.manutd.reddevilstats.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping()
    public String showHomePage(@RequestParam(value = "action", required = false) String action,
                               @RequestParam(value = "position", required = false) String position,
                               @RequestParam(value = "nationality", required = false) String nationality,
                               @RequestParam(value = "age", required = false) String age,
                               Model model) {
        logger.info("User accessed the home page.");

        model.addAttribute("topRated", playerService.getTopRatedPlayers(3));
        model.addAttribute("topScorers", playerService.getTopScorers(3));
        model.addAttribute("topAssistants", playerService.getTopAssistants(3));

        model.addAttribute("position", position);
        model.addAttribute("nationality", nationality);
        model.addAttribute("age", age);

        List<PlayerData> filteredPlayers = List.of();
        boolean noPlayersFound = false;

        if ("viewAll".equals(action)) {
            logger.info("User requested to view all players.");
            filteredPlayers = playerService.getAllPlayersData();
            model.addAttribute("filteredPlayers", filteredPlayers);
            noPlayersFound = filteredPlayers.isEmpty();
        } else if ("filter".equals(action)) {
            logger.info("Filtering players with position: {}, nationality: {}, age: {}", position, nationality, age);
            filteredPlayers = playerService.filterPlayers(position, nationality, age);
            noPlayersFound = filteredPlayers.isEmpty();
        }

        model.addAttribute("filteredPlayers", filteredPlayers);
        model.addAttribute("noPlayersFound", noPlayersFound);

        return "index";
    }

    @GetMapping("/details/{number}")
    public String showPlayerDetails(@PathVariable("number") int number, Model model){
        logger.info("User requested details for player with number: {}", number);

        PlayerData playerData = playerService.getPlayerByNumber(number);
        model.addAttribute("playerData", playerData);

        logger.info("Player details loaded: {}", playerData.getPlayer().getName());

        return "player-details";
    }
}
