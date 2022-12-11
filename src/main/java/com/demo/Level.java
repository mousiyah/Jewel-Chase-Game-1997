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
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Objects;

public class Level {

    private final int CANVAS_WIDTH;
    private final int CANVAS_HEIGHT;

    private Canvas canvas;

    public int levelNumber;
    public int width;
    public int height;
    public boolean isWon;
    public boolean isLost;

    private ArrayList<String> data;
    private Tile[][] tiles;

    private Player player;
    private ArrayList<MovingEntity> movingEntities = new ArrayList<>();

    private Door door;
    Pair <Integer, Integer> randomCoordinates;

    private Time time;
    private Timeline gameTimeline;
    private Timeline drawTimeline;
    public static EventHandler<KeyEvent> keyEventHandler;
    public static Scene scene;

    public Level(int levelNumber) {

        this.levelNumber = levelNumber;

        // Read level file
        data = FileIO.readLevel(levelNumber);


        // level width and height in tiles
        width = Integer.parseInt(data.get(0).split(" ")[0]);
        height = Integer.parseInt(data.get(0).split(" ")[1]);

        // set time limit
        time = new Time(Integer.parseInt(data.get(1)));
        tiles = new Tile[height][width];

        CANVAS_WIDTH = Tile.TILE_SIZE * getWidth();
        CANVAS_HEIGHT =  Tile.TILE_SIZE * getHeight();

        initializeTiles();
        initializeEntities();
        tick();
    }

    public void tick() {
        setGameTimeline(new Timeline(new KeyFrame(Duration.seconds(1),
                event -> {
                    Time.setTimeLeft(Time.getTimeLeft()-1); // -1
                    Main.controller.changeProgressBar(Time.getTimeLeft(), Time.getTimeLimit());

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
        if (player.isDead() || Time.getTimeLeft() < 0 || door.isClosedForever()) {
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
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
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

    public void initializeEntities() {
        Entity.setTiles(tiles);

        // 3 = starting point to read entities
        for(int i = height+3; i < data.size(); i++) {
            switch (data.get(i).split(" ")[0]) {
                case "P" -> initializePlayer(i);
                case "door" -> initializeDoor(i);
                case "FA" -> initializeMovingEntities(i, new FlyingAssassin());
                case "FFT" -> initializeMovingEntities(i, new FloorFollowingThief());
                case "ST" -> initializeMovingEntities(i, new SmartThief());
                case "loot" -> initializeItems(i, new Loot());
                case "gate" -> initializeItems(i, new Gate());
                case "bomb" -> initializeItems(i, new Bomb());
                case "clock" -> initializeItems(i, new Clock());
                case "lever" -> initializeItems(i, new Lever());
            }
        }

        setImg();
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


    private void initializeTiles() {
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                tiles[i][j] = new Tile(data.get(2+i).split(" ")[j], i, j);
            }
        }
    }

    private void initializeMovingEntities(int row, MovingEntity movingEntity) {
        String direction = data.get(row).split(" ")[1];
        String colorToFollow = data.get(row).split(" ")[2];
        int x = Integer.parseInt(data.get(row).split(" ")[3])-1;
        int y = Integer.parseInt(data.get(row).split(" ")[4])-1;

        if (!colorToFollow.equals("null")) {
            movingEntity.setColorToFollow(colorToFollow);
        }

        movingEntity.setDirection(MovingEntity.getKeyCodeFromString(direction));
        movingEntity.setPositions(x, y);

        Tile.getAllObjectsCoordinates().add(new Pair(x,y));

        movingEntities.add(movingEntity);
        movingEntity.tick();
    }

    private void initializeItems(int row, Item item) {
        int n = Integer.parseInt(data.get(row).split(" ")[2]);
        String type = data.get(row).split(" ")[1];
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

    private void initializePlayer(int row) {
        int x = Integer.parseInt(data.get(row).split(" ")[1])-1;
        int y = Integer.parseInt(data.get(row).split(" ")[2])-1;
        player = new Player();
        player.setPositions(x, y);
        Tile.getAllObjectsCoordinates().add(new Pair(x,y));
    }

    public void initializeDoor(int row) {
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int length) {
        this.width = length;
    }

    public void setGameTimeline(Timeline gameTimeline) {
        this.gameTimeline = gameTimeline;
    }

    public Timeline getGameTimeline() {
        return gameTimeline;
    }

    public Timeline getDrawTimeline() {
        return drawTimeline;
    }

    public void setDrawTimeline(Timeline drawTimeline) {
        this.drawTimeline = drawTimeline;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }
}
