package com.game.controller;

import javax.servlet.http.HttpSession;

import com.game.model.GameError;
import com.game.utils.KalahGameUtils;
import com.game.action.KalahGame;
import com.game.model.GameResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.game.model.Player;
import com.game.service.KalahGameService;

@RestController
public class KalahGameController {

	@Autowired
    @Qualifier(value = "kalahService")
    private KalahGameService kalahGameService;

    @ApiOperation(value = "Create or get game",  response = GameResponse.class,
            tags = {"Games"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the game detail with url", response = GameResponse.class),
            @ApiResponse(code = 404, message = "Error Details", response = GameError.class)})
    @PostMapping("/games")
    public ResponseEntity<GameResponse> createOrGetGame(HttpSession session) {
        Player player = new Player(session.getId());
        KalahGame kalahGame= kalahGameService.createOrGetGame();
        kalahGameService.joinPlayer(kalahGame, player);
        final GameResponse gameResponse = new GameResponse(kalahGame.getGameId(), KalahGameUtils.getGameUrl(kalahGame.getGameId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(gameResponse);

    }

    @ApiOperation(value = "Play the game with pit value", notes = "", response = GameResponse.class,
            tags = {"Game Play"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the game info with status", response = GameResponse.class),
            @ApiResponse(code = 404, message = "Error Details", response = GameError.class),
            @ApiResponse(code = 400, message = "Error Details", response = GameError.class),
            @ApiResponse(code = 401, message = "Error Details", response = GameError.class)})
    @PutMapping("/games/{gameId}/pits/{pitId}")
    public ResponseEntity<GameResponse> playGame(HttpSession session, @PathVariable("gameId") String gameId,
                                           @PathVariable("pitId") int pitId)  {
        Player player = new Player(session.getId());
        KalahGame kalahGame = kalahGameService.playGame(player, gameId, pitId);
        final GameResponse gameResponse = new GameResponse(kalahGame.getGameId(), KalahGameUtils.getGameUrl(kalahGame.getGameId()),kalahGame.getPitStoneCountMap() );
        return ResponseEntity.status(HttpStatus.OK).body(gameResponse);
    }



}
