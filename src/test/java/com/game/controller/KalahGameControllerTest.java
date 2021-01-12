package com.game.controller;

import com.game.exception.ApplicationException;
import com.game.exception.ErrorType;
import com.game.action.KalahGame;
import com.game.model.Player;
import com.game.model.GameResponse;
import com.game.service.KalahGameService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class KalahGameControllerTest {

    @Autowired
    private KalahGameController controller;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private KalahGameService service;

    private MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * Integration test case via call the controller method
     * @throws Exception
     */
    @Test
    public void testCreatOrGetGame() throws Exception {
        ResponseEntity<GameResponse> response = controller.createOrGetGame(httpSession);
        Assert.assertTrue(response.getStatusCode() == HttpStatus.CREATED);
        GameResponse game = response.getBody();
        Assert.assertNotNull(game.getId());
        Assert.assertNotNull(game.getUri());
        KalahGame kalahGame = this.service.createOrGetGame();
        Player secondPlayer = new Player("2");
        this.service.joinPlayer(kalahGame, secondPlayer);
    }


    /**
     * Integration test case via call api url
     * @throws Exception
     */
    @Test
    public void testPlay() throws Exception {
        KalahGame game = this.service.createOrGetGame();
        Player firstPlayer = new Player("1");
        this.service.joinPlayer(game, firstPlayer);
        Player secondPlayer = new Player("2");
        this.service.joinPlayer(game, secondPlayer);

        final MockHttpServletRequestBuilder playGameRequest =
                MockMvcRequestBuilders.put("/games/" + game.getGameId() + "/pits/1");
        this.mockMvc.perform(playGameRequest).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getGameId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uri").value(Matchers.endsWith(game.getGameId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.1").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.2").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.3").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.4").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.5").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.6").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.7").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.8").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.9").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.10").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.11").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.12").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.13").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.14").value("0")).andReturn();
    }
}
