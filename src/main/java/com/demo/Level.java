package com.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/** Level.java
 *
 * @author user
 * @version 2.0
 */
public class Level {

    private int canvasHeight;
    private int canvasWidth;

    private Canvas canvas;

    public int levelNumber;
    public int width;
    public int height;
    public boolean isWon;
    public boolean isLost;

    private ArrayList<String> levelData;
    private ArrayList<String> levelState;
    private static Tile[][] tiles;

    private Player player;
    private ArrayList<MovingEntity> movingEntities;

    private Door door;
    Pair <Integer, Integer> randomCoordinates;

    private LevelTimer levelTimer;
    private Timeline gameTimeline;
    private Timeline drawTimeline;
    public static EventHandler<KeyEvent> keyEventHandler;
    public static Scene scene;

    /** Constructs a level.
     *
     * @param levelNumber integer indicating the current level
     */
    public Level(int levelNumber) {

        this.levelNumber = levelNumber;

        // Read level file
        levelData = FileIO.readLevel(levelNumber);

        initializeLevel(levelData);

        initializeMovingEntities(levelData);
        initializeItems();
        setImg();
        tick();
    }

    public Level(int levelNumber, ArrayList<String> savedData) {

        this.levelNumber = levelNumber;

        // Read level file
        levelData = FileIO.readLevel(levelNumber);

        initializeLevel(savedData);

        LevelTimer.setTimeLeft(Integer.parseInt(savedData.get(height+2).split(" ")[1]));
        Score.setScore(Integer.parseInt(savedData.get(height+3).split(" ")[1]));

        initializeMovingEntities(savedData);
        initializeSavedItems(savedData);
        setImg();
        tick();
    }

    public void initializeLevel(ArrayList<String> data) {
        // level width and height in tiles
        width = Integer.parseInt(data.get(0).split(" ")[0]);
        height = Integer.parseInt(data.get(0).split(" ")[1]);

        // set time limit
        levelTimer = new LevelTimer(Integer.parseInt(data.get(1)));
        tiles = new Tile[height][width];

        canvasHeight = Tile.TILE_SIZE * getWidth();
        canvasWidth =  Tile.TILE_SIZE * getHeight();

        initializeTiles(data);
        Entity.setTiles(tiles);
    }


    public void tick() {
        setGameTimeline(new Timeline(new KeyFrame(Duration.seconds(1),
                event -> {
                    LevelTimer.setTimeLeft(LevelTimer.getTimeLeft()-1); // -1
                    Main.controller.changeProgressBar(LevelTimer.getTimeLeft(), LevelTimer.getTimeLimit());

                })));
        getGameTimeline().setCycleCount(Timeline.INDEFINITE);
        getGameTimeline().play();
    }


    public void stopGame() {
        pauseGame();
        gameTimeline.stop();
        drawTimeline.stop();
    }

    public void pauseGame() {
        gameTimeline.pause();
        drawTimeline.pause();
        for (MovingEntity movingEntity: movingEntities) {
            movingEntity.getTimeLine().pause();
        }
        scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyEventHandler);
    }

    public void resumeGame() {
        gameTimeline.play();
        drawTimeline.play();
        for (MovingEntity movingEntity: movingEntities) {
            movingEntity.getTimeLine().play();
        }
        addEventFilter();
    }

    public void addEventFilter() {
        // Register an event handler for key presses.
        keyEventHandler = event -> {
            player.move(event.getCode());
            Main.controller.changeScore();
            drawGame();
            event.consume();
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEventHandler);

    }

    /** Returns the scene that constructs the game.
     *
     * @return Scene
     * @throws IOException if files aren't found
     */
    public Scene getScene() throws IOException {
        BorderPane root = new BorderPane();
        root.setId("root");

        VBox content = new VBox();

        // Load fxml
        FXMLLoader fxmlBarLoader = new FXMLLoader(Main.class.getResource("gameBar.fxml"));
        fxmlBarLoader.setController(Main.controller);

        content.setAlignment(Pos.CENTER);
        content.getChildren().add(fxmlBarLoader.load());
        content.getChildren().add(buildCanvas());

        root.setCenter(content);

        // Create a scene from the GUI
        scene = new Scene(root, Main.screenSize.getWidth(), Main.screenSize.getHeight());

        // CSS
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());

        // Register an event handler for key presses.
        addEventFilter();

        setWon(false);
        setLost(false);

        return scene;
    }

    public void play() {
        drawGame();
        checkGameState();
    }

    public void drawGame() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawTiles(gc);
        drawMovingEntities(gc);
    }

    public void checkGameState() {
        checkCollusion();
        if (player.isDead() || LevelTimer.getTimeLeft() < 0 || door.isClosedForever()) {
            setLost(true);
        }
        if (Door.noLootsAndGatesLeft()) {
            door.activate();
        }
        if (door.isReached()) {
            setWon(true);
        }
    }

    private Pane buildCanvas() throws IOException {
        canvas = new Canvas(canvasHeight, canvasWidth);
        BorderPane canvasPane = new BorderPane();
        canvasPane.setCenter(canvas);
        canvas.setId("canvas");
        return canvasPane;
    }


    public void checkCollusion() {
        for (int i = 0; i < movingEntities.size(); i++) {
            if (movingEntities.get(i) instanceof FlyingAssassin) {
                if(((FlyingAssassin) movingEntities.get(i)).collusionWithPlayer(player.getX(), player.getY())) {
                    player.setDead(true);
                }
                ((FlyingAssassin) movingEntities.get(i)).collusionWithThief(movingEntities);
            }
        }
    }

    public void initializeMovingEntities(ArrayList<String> data) {
        movingEntities = new ArrayList<>();

        // 3 = starting point to read entities
        for(int i = height; i < data.size(); i++) {
            switch (data.get(i).split(" ")[0]) {
                case "P" -> initializePlayer(i, data);
                case "door" -> initializeDoor(i, data);
                case "FA" -> initializeMovingEntity(i, new FlyingAssassin(), data);
                case "FFT" -> initializeMovingEntity(i, new FloorFollowingThief(), data);
                case "ST" -> initializeMovingEntity(i, new SmartThief(), data);
            }
        }
    }


    public void initializeItems() {
        for(int i = height; i < levelData.size(); i++) {
            switch (levelData.get(i).split(" ")[0]) {
                case "loot" -> initializeRandomItem(i, new Loot());
                case "gate" -> initializeRandomItem(i, new Gate());
                case "bomb" -> initializeRandomItem(i, new Bomb());
                case "clock" -> initializeRandomItem(i, new Clock());
                case "lever" -> initializeRandomItem(i, new Lever());
            }
        }
    }

    public void initializeSavedItems(ArrayList<String> savedData) {
        for(int i = height; i < savedData.size(); i++) {
            switch (savedData.get(i).split(" ")[0]) {
                case "loot" -> initializeSavedItem(i, new Loot(), savedData);
                case "gate" -> initializeSavedItem(i, new Gate(), savedData);
                case "bomb" -> initializeSavedItem(i, new Bomb(), savedData);
                case "clock" -> initializeSavedItem(i, new Clock(), savedData);
                case "lever" -> initializeSavedItem(i, new Lever(), savedData);
            }
        }
    }

    private void drawTiles(GraphicsContext gc) {
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                tiles[i][j].drawTile(gc, i, j, Tile.TILE_SIZE);
            }
        }
    }

    private void drawMovingEntities(GraphicsContext gc) {
        // draw player
        gc.drawImage(player.getImg(), player.getX() * Tile.TILE_SIZE + Tile.TILE_MARGIN,
                player.getY() * Tile.TILE_SIZE + Tile.TILE_MARGIN);

        // draw other moving entities
        for (MovingEntity movingEntity: movingEntities) {
            gc.drawImage(movingEntity.getImg(),
                    movingEntity.getX() * Tile.TILE_SIZE + Tile.TILE_MARGIN,
                    movingEntity.getY() * Tile.TILE_SIZE + Tile.TILE_MARGIN);
        }
    }


    private void initializeTiles(ArrayList<String> data) {
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                tiles[i][j] = new Tile(data.get(2+i).split(" ")[j], i, j);
            }
        }
    }

    private void initializeMovingEntity(int row, MovingEntity movingEntity,
                                          ArrayList<String> data) {
        String direction = data.get(row).split(" ")[1];
        String colorToFollow = data.get(row).split(" ")[2];
        int x = Integer.parseInt(data.get(row).split(" ")[3]);
        int y = Integer.parseInt(data.get(row).split(" ")[4]);

        if (!colorToFollow.equals("null")) {
            movingEntity.setColorToFollow(colorToFollow);
        }

        movingEntity.setDirection(MovingEntity.getKeyCodeFromString(direction));
        movingEntity.setPositions(x, y);

        Tile.getAllObjectsCoordinates().add(new Pair(x,y));

        movingEntities.add(movingEntity);
        movingEntity.tick();
    }

    private void initializeSavedItem(int row, Item item,
                                     ArrayList<String> data) {
        String type = data.get(row).split(" ")[1];
        int x = Integer.parseInt(data.get(row).split(" ")[2]);
        int y = Integer.parseInt(data.get(row).split(" ")[3]);

        item.setPositions(x, y);
        item.setType(type);
        tiles[y][x].setItem(item);
        if (item instanceof Bomb) {
            setBombAreas(x, y);
        }

        Tile.getAllObjectsCoordinates().add(new Pair(x,y));
    }

    private void initializeRandomItem(int row, Item item) {
        int n = Integer.parseInt(levelData.get(row).split(" ")[2]);
        String type = levelData.get(row).split(" ")[1];
        int x, y;

        for (int i = 0; i < n; i++) {
            randomCoordinates = generateRandomXY();
            x = randomCoordinates.getKey();
            y = randomCoordinates.getValue();

            if (item instanceof Loot) {
                tiles[y][x].setItem(new Loot(x, y, type));
            } else if (item instanceof Gate) {
                tiles[y][x].setItem(new Gate(x, y, type));
            } else if (item instanceof Lever) {
                tiles[y][x].setItem(new Lever(x, y, type));
            } else if (item instanceof Clock) {
                tiles[y][x].setItem(new Clock(x, y));
            } else if (item instanceof Bomb) {
                tiles[y][x].setItem(new Bomb(x, y));
                setBombAreas(x, y);
            }
        }

        Tile.getAllObjectsCoordinates().add(randomCoordinates);
    }

    /** Sets the areas that would trigger the bomb.
     *
     * @param x x-coordinate of the bomb
     * @param y y-coordinate of the bomb
     */
    private void setBombAreas(int x, int y) {
        if (!isOutOfEdge(x, y+1)) {
            tiles[y+1][x].setBombArea(tiles[y][x].getItem());
        }
        if (!isOutOfEdge(x+1, y+1)) {
            tiles[y+1][x+1].setBombArea(tiles[y][x].getItem());
        }
        if (!isOutOfEdge(x+1, y)) {
            tiles[y][x+1].setBombArea(tiles[y][x].getItem());
        }
        if (!isOutOfEdge(x+1, y-1)) {
            tiles[y-1][x+1].setBombArea(tiles[y][x].getItem());
        }
        if (!isOutOfEdge(x, y-1)) {
            tiles[y-1][x].setBombArea(tiles[y][x].getItem());
        }
        if (!isOutOfEdge(x-1, y-1)) {
            tiles[y-1][x-1].setBombArea(tiles[y][x].getItem());
        }
        if (!isOutOfEdge(x-1, y)) {
            tiles[y][x-1].setBombArea(tiles[y][x].getItem());
        }
        if (!isOutOfEdge(x+1, y-1)) {
            tiles[y-1][x+1].setBombArea(tiles[y][x].getItem());
        }
    }

    public boolean isOutOfEdge(int x, int y) {
        return x < 0 || x >= tiles[0].length || y < 0 || y >= tiles.length;
    }

    private void initializePlayer(int row, ArrayList<String> data) {
        int x = Integer.parseInt(data.get(row).split(" ")[1]);
        int y = Integer.parseInt(data.get(row).split(" ")[2]);
        player = new Player();
        player.setPositions(x, y);
        Tile.getAllObjectsCoordinates().add(new Pair(x,y));
    }

    public void initializeDoor(int row, ArrayList<String> data) {
        int x = Integer.parseInt(data.get(row).split(" ")[1]);
        int y = Integer.parseInt(data.get(row).split(" ")[2]);

        door = new Door();
        door.setPositions(x, y);
        tiles[y][x].setItem(door);
        Tile.getAllObjectsCoordinates().add(new Pair(x,y));
    }

    public Pair<Integer,Integer> generateRandomXY() {
        int x, y;
        do{
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        } while(Tile.getAllObjectsCoordinates().contains(new Pair(x,y)));
        return new Pair<>(x, y);
    }

    public void saveLevelState(Profile profile) {
        levelState = new ArrayList<>();
        levelState.add(width + " " + height);

        levelState.add(String.valueOf(LevelTimer.getTimeLimit()));
        for (int i = 0; i < height; i++) {
            levelState.add(levelData.get(i+2));
        }

        levelState.add("timeLeft " + LevelTimer.getTimeLeft());
        levelState.add("score " + Score.getScore());

        levelState.add("P" + " " + player.getX() + " " + player.getY());
        levelState.add("door" + " " + door.getX() + " " + door.getY());

        String movingEntityData = "";
        for (MovingEntity movingEntity: movingEntities) {
            if (movingEntity instanceof FlyingAssassin) {
                movingEntityData = ("FA");
            } else if (movingEntity instanceof FloorFollowingThief) {
                movingEntityData = ("FFT");
            }if (movingEntity instanceof SmartThief) {
                movingEntityData = ("ST");
            }
            movingEntityData += " " + movingEntity.getDirection();
            movingEntityData += " " + movingEntity.getColorToFollow();
            movingEntityData += " " + movingEntity.getX() + " " + movingEntity.getY();
            levelState.add(movingEntityData);
        }

        String itemData = "";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].hasItem()) {
                    if(tiles[i][j].getItem() instanceof Loot) {
                        itemData = "loot";
                    } else if(tiles[i][j].getItem() instanceof Lever) {
                        itemData = "lever";
                    } else if(tiles[i][j].getItem() instanceof Gate) {
                        itemData = "gate";
                    } else if(tiles[i][j].getItem() instanceof Clock) {
                        itemData = "clock";
                    } else if(tiles[i][j].getItem() instanceof Bomb) {
                        itemData = "bomb";
                    } else if(tiles[i][j].getItem() instanceof Door) {
                        break;
                    }
                    itemData += " " + tiles[i][j].getItem().getType() + " " + j + " " + i;
                    levelState.add(itemData);
                }
            }
        }

        FileIO.writeLevelState(levelState, profile);
    }

    /** Sets the images for the player and the NPCs.
     *
     */
    private void setImg() {
        player.setImg(new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("img/player.png"))));

        for (MovingEntity movingEntity: movingEntities) {
            if (movingEntity instanceof FlyingAssassin) {
                movingEntity.setImg(new Image(Objects.requireNonNull(getClass().
                        getResourceAsStream("img/FA.png"))));
            } else if (movingEntity instanceof FloorFollowingThief) {
                movingEntity.setImg(new Image(Objects.requireNonNull(getClass().
                        getResourceAsStream("img/FFT.png"))));
            } else if (movingEntity instanceof SmartThief) {
                movingEntity.setImg(new Image(Objects.requireNonNull(getClass().
                        getResourceAsStream("img/ST.png"))));
            }
        }

        setItemImg();
    }

    /** Sets the images for all items.
     *
     */
    private void setItemImg() {
        for (int k = 0; k < tiles.length; k++) {
            for (int l = 0; l < tiles[k].length; l++) {

                Item item = tiles[k][l].getItem();

                if (item instanceof Loot) {
                    for (int i = 0; i < item.getTypes().length; i++){
                        if (item.getType().equals(item.getTypes()[i])) {
                            item.setImg(new Image(Objects.requireNonNull(getClass().
                                    getResourceAsStream("img/" + item.getType() + ".png"))));
                        }
                    }
                } else if (item instanceof Gate) {
                    for (int i = 0; i < item.getTypes().length; i++){
                        if (item.getType().equals(item.getTypes()[i])) {
                            item.setImg(new Image(Objects.requireNonNull(getClass().
                                    getResourceAsStream("img/" + item.getType() + "Gate" + ".png"))));
                        }
                    }
                } else if (item instanceof Lever) {
                    for (int i = 0; i < item.getTypes().length; i++){
                        if (item.getType().equals(item.getTypes()[i])) {
                            item.setImg(new Image(Objects.requireNonNull(getClass().
                                    getResourceAsStream("img/" + item.getType() + "Lever" + ".png"))));
                        }
                    }
                } else if (item instanceof Clock) {
                    item.setImg(new Image(Objects.requireNonNull(getClass().
                            getResourceAsStream("img/clock.png"))));
                } else if (item instanceof Bomb) {
                    item.setImg(new Image(Objects.requireNonNull(getClass().
                            getResourceAsStream("img/bomb.png"))));
                } else if (item instanceof Door) {
                    item.setImg(new Image(Objects.requireNonNull(getClass().
                            getResourceAsStream("img/door.png"))));
                }
            }
        }
    }

    /** Returns the height of the game.
     *
     * @return integer indicating the height of the game
     */
    public int getHeight() {
        return height;
    }

    /** Sets the height of the game.
     *
     * @param height integer indicating the height of the game
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /** Returns the width of the game.
     *
     * @return integer indicating the width of the game
     */
    public int getWidth() {
        return width;
    }

    /** Sets the width of the game.
     *
     * @param length integer indicating the width of the game
     */
    public void setWidth(int length) {
        this.width = length;
    }

    /** Sets the timeline for the game.
     *
     * @param gameTimeline Timeline of the game
     */
    public void setGameTimeline(Timeline gameTimeline) {
        this.gameTimeline = gameTimeline;
    }

    /** Returns the timeline for the game.
     *
     * @return the timeline for the game
     */
    public Timeline getGameTimeline() {
        return gameTimeline;
    }

    /** Returns the timeline that will be drawn to the screen.
     *
     * @return Timeline to be drawn
     */
    public Timeline getDrawTimeline() {
        return drawTimeline;
    }

    /** Sets the timeline that will be drawn.
     *
     * @param drawTimeline Timeline
     */
    public void setDrawTimeline(Timeline drawTimeline) {
        this.drawTimeline = drawTimeline;
    }

    public boolean isWon() {
        return isWon;
    }

    /** Sets isWon to true if the player completes the level, otherwise it returns false.
     *
     * @param won boolean indicating if player has completed the level
     */
    public void setWon(boolean won) {
        isWon = won;
    }

    public boolean isLost() {
        return isLost;
    }

    /** Sets isLost to true if level is unwinnable, otherwise method returns false.
     *
     * @param lost boolean indicating if player has lost the level
     */
    public void setLost(boolean lost) {
        isLost = lost;
    }
}
