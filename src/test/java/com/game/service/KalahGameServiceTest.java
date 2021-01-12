package com.game.service;

import com.game.action.KalahGame;
import com.game.exception.ApplicationException;
import com.game.exception.ErrorType;
import com.game.model.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class KalahGameServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private KalahGameService service;

    private MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testJoinWithInvalidGameId() throws Exception {
        KalahGame game = this.service.createOrGetGame();
        Player firstPlayer = new Player("1");
        this.service.joinPlayer(game, firstPlayer);
        Player secondPlayer = new Player("2");
        this.service.joinPlayer(game, secondPlayer);
        final MockHttpServletRequestBuilder playGameRequest = MockMvcRequestBuilders.put("/games/" + 1234 + "/pits/1");
        this.mockMvc.perform(playGameRequest).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    }

    @Test
    public void testPlayPitWithZeroStone() throws Exception {
        KalahGame game = this.service.createOrGetGame();
        Player firstPlayer = new Player("1");
        this.service.joinPlayer(game, firstPlayer);
        Player secondPlayer = new Player("2");
        this.service.joinPlayer(game, secondPlayer);
        this.service.playGame(firstPlayer, game.getGameId(), 1);
        Assert.assertTrue("above instructions successfully executed", true);
        try {
            this.service.playGame(firstPlayer, game.getGameId(), 1);
        } catch (ApplicationException e) {
            Assert.assertEquals(e.getErrorType().getMessage(), ErrorType.NOT_ALLOWED_ZERO_MOVED.getMessage());
        }
    }

    @Test
    public void testPlayNormalPit() throws Exception {
        KalahGame game = this.service.createOrGetGame();
        Player firstPlayer = new Player("1");
        this.service.joinPlayer(game, firstPlayer);
        Player secondPlayer = new Player("2");
        this.service.joinPlayer(game, secondPlayer);
        this.service.playGame(firstPlayer, game.getGameId(), 3);
        Assert.assertTrue("above instructions successfully executed", true);
    }

    @Test
    public void testPlayWithKalahMovement() throws Exception {
        KalahGame game = this.service.createOrGetGame();
        Player firstPlayer = new Player("1");
        this.service.joinPlayer(game, firstPlayer);
        Player secondPlayer = new Player("2");
        this.service.joinPlayer(game, secondPlayer);
        try {
            this.service.playGame(firstPlayer, game.getGameId(), 7);
        } catch (ApplicationException e) {
            Assert.assertEquals(e.getErrorType().getMessage(), ErrorType.INVALID_PIT_VALUE.getMessage());
            Assert.assertEquals(e.getErrorType().getStatus().value(), 400);

        }

    }

    @Test
    public void testPlayWithPlayerNullValue() throws Exception {
        KalahGame game = this.service.createOrGetGame();
        Player firstPlayer = new Player("1");
        this.service.joinPlayer(game, firstPlayer);
        Player secondPlayer = new Player("2");
        this.service.joinPlayer(game, secondPlayer);
        try {
            this.service.playGame(null, game.getGameId(), 2);
        } catch (ApplicationException e) {
            Assert.assertEquals(e.getErrorType().getMessage(), ErrorType.INVALID_PLAYER.getMessage());
            Assert.assertEquals(e.getErrorType().getStatus().value(), 400);

        }

    }

    @Test
    public void testInValidPitMoved() throws Exception {
        KalahGame game = this.service.createOrGetGame();
        Player firstPlayer = new Player("1");
        this.service.joinPlayer(game, firstPlayer);
        Player secondPlayer = new Player("2");
        this.service.joinPlayer(game, secondPlayer);
        try {
            this.service.playGame(firstPlayer, game.getGameId(), 15);
        } catch (ApplicationException e) {
            Assert.assertEquals(e.getErrorType().getMessage(), ErrorType.INVALID_PIT_MOVED.getMessage());
            Assert.assertEquals(e.getErrorType().getStatus().value(), 400);

        }

    }

    @Test
    public void testIfGameOver() throws Exception {
        KalahGame game = this.service.createOrGetGame();
        Player firstPlayer = new Player("1");
        this.service.joinPlayer(game, firstPlayer);
        Player secondPlayer = new Player("2");
        this.service.joinPlayer(game, secondPlayer);
        try {
            this.service.playGame(firstPlayer, game.getGameId(), 2);
            Assert.assertFalse(this.service.checkGameOver(game));
        } catch (ApplicationException e) {
            Assert.assertEquals(e.getErrorType().getMessage(), ErrorType.INVALID_PIT_MOVED.getMessage());
            Assert.assertEquals(e.getErrorType().getStatus().value(), 400);

        }

    }

}
