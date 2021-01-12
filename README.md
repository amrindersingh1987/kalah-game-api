## Kalah Game

This web service should enable to let 2 human players to play the game, each in his own computer. There is no AI
required.

#### Endpoint
```
1. Creation of the game should be possible with the command:
 curl --header "Content-Type: application/json" \
 --request POST \
 http://<host>:<port>/games
 Response:
 HTTP code: 201
 Response Body: { "id": "1234", "uri": "http://<host>:<port>/games/1234" }
id: unique identifier of a game
url: link to the game created

```
```
2. Make a move:
 curl --header "Content-Type: application/json" \
 --request PUT \
 http://<host>:<port>/games/{gameId}/pits/{pitId}
gameId: unique identifier of a game
pitId: id of the pit selected to make a move. Pits are numbered from 1 to 14 where 7 and 14 are the kalah (or house)
of each player
 Response:
 HTTP code: 200
 Response Body:
{"id":"1234","url":"http://<host>:<port>/games/1234","status":{"1":"4","2":"4","3":"4","4":"4","5":"4","6":"4","7":"0","8":"4","
9":"4","10":"4","11":"4","12":"4","13":"4","14":"0"}}
status: json object key-value, where key is the pitId and value is the number of stones in the pit
```

### Implementation

### To Run the application


```
mvn spring-boot:run
```
###OR
```
Import in IDE and run the KalahApp.java
```
### API Documentation

```
http://localhost:8080/swagger-ui.html
```

### Running the tests

```
mvn test
```

##Run the 

####Player 1st join
```
http://localhost:8080/games/
```

####Player 2nd join it will create 2nd player with different session
```
http://127.0.0.1:8080/games/
```

####Player turn
http://localhost:8080/games/{gameid}/pit/{if}


       


