package com.manutd.reddevilstats.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void showHomePageTest() throws Exception{
        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("topRated"))
                .andExpect(model().attributeExists("topScorers"))
                .andExpect(model().attributeExists("topAssistants"));
    }

    @Test
    public void showPlayerDetailsTest() throws Exception{
        mockMvc.perform(get("/players/details/10"))
                .andExpect(status().isOk())
                .andExpect(view().name("player-details"))
                .andExpect(model().attributeExists("playerData"))
                .andExpect(model().attribute("playerData",
                        Matchers.hasProperty("player")));
    }

    @Test
    public void viewAllPlayersTest() throws Exception{
        mockMvc.perform(get("/players")
                .param("action", "viewAll"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("filteredPlayers"))
                .andExpect(model().attribute("filteredPlayers",
                        Matchers.not(Matchers.empty())));
    }

    @Test
    public void filterPlayersByCombinedCriteriaTest() throws Exception{
        mockMvc.perform(get("/players")
                .param("action", "filter")
                .param("position", "attacker")
                .param("nationality", "England")
                .param("age", "20-25"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("filteredPlayers"))
                .andExpect(model().attribute("position", "attacker"))
                .andExpect(model().attribute("nationality", "England"))
                .andExpect(model().attribute("age", "20-25"));
    }

    @Test
    public void noPlayersFoundTest() throws Exception{
        mockMvc.perform(get("/players")
                .param("action", "filter")
                .param("position", "goalkeeper")
                .param("nationality", "England")
                .param("age", "20-25"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("noPlayersFound", true))
                .andExpect(model().attribute("filteredPlayers",
                        Matchers.empty()));
    }

    @Test
    public void detailsButtonViewTest() throws Exception{
        mockMvc.perform(get("/players/details/10"))
                .andExpect(status().isOk())
                .andExpect(view().name("player-details"))
                .andExpect(model().attributeExists("playerData"))
                .andExpect(model().attribute("playerData",
                        Matchers.hasProperty("player")))
                .andExpect(model().attribute("playerData",
                        Matchers.hasProperty("statistics")));
    }
}
