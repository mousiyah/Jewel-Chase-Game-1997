package com.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller is a java fxml controller which reacts to the button clicks.
 * @author Muslima Karimova 2130288
 * @version 1.0
 */
public class Controller {

    @FXML
    Text levelScore;

    @FXML
    Text scoreBar;

    /**
     * Change score on the level screen.
     */
    @FXML
    protected void changeScore() {
        scoreBar.setText(Integer.toString(Score.getScore()));
    }

    @FXML
    ProgressBar progressBar;

    /**
     * Change progressBar on the level screen.
     */
    @FXML
    protected void changeProgressBar(int timeLeft, int timeLimit) {
        float progress = (float) timeLeft / (float) timeLimit;
        progressBar.setProgress(progress);
        progressBar.setAccessibleText(timeLeft + " seconds");
    }

    /**
     * Update Level Score.
     */
    @FXML
    protected void updateScore() {
        levelScore.setText(Integer.toString(Score.getScore()));
        Main.currentProfile.setScore(Main.currentProfile.getScore()+Score.getScore());
        Profile.updateProfilesData();
    }

    @FXML
    Text levelNumber;

    /**
     * Set level number.
     * @param n level number
     */
    @FXML
    public void setLevelNumberLabel(int n) {
        levelNumber.setText(Integer.toString(n));
    }

    /**
     * Change progressBar on the level screen.
     */
    @FXML
    public void replay(ActionEvent event) throws IOException {
        Main.level.stopGame();
        Main.openLevel(Main.currentLevel);
        FileIO.deleteLevelState(Main.currentProfile.getName(),
                Main.currentLevel);
    }

    /**
     * Go to the next level.
     */
    @FXML
    public void nextLevel(ActionEvent event) throws IOException {
        if (Main.currentLevel <= Main.LEVELS) {
            Main.currentLevel++;
            Main.currentProfile.setMaxLevelUnlocked(Main.currentLevel);
            Profile.updateProfilesData();
            Main.openLevel(Main.currentLevel);
        }
    }

    /**
     * Go to the home scene.
     */
    @FXML
    public void goHome(ActionEvent event) throws IOException {
        if (Main.level != null) {
            Main.level.stopGame();
        }
        Main.stage.setScene(Main.createScene(new FXMLLoader(Main.fxmlHome)));
    }

    /**
     * Go back to the levels scene.
     */
    @FXML
    public void goLevels(ActionEvent event) throws IOException {
        Main.openProfileLevels(Main.currentProfile.getName());
    }


    // level control buttons
    @FXML
    Button resumeGameBtn;
    @FXML
    Button pauseGameBtn;
    @FXML
    Button saveGameStateBtn;


    /**
     * Resume game.
     */
    @FXML
    public void resumeGame(ActionEvent event) {
        Main.level.resumeGame();
        pauseGameBtn.setDisable(false);
        resumeGameBtn.setDisable(true);
        saveGameStateBtn.setDisable(true);
    }

    /**
     * Pause game.
     */
    @FXML
    public void pauseGame(ActionEvent event) {
        Main.level.pauseGame();
        pauseGameBtn.setDisable(true);
        resumeGameBtn.setDisable(false);
        saveGameStateBtn.setDisable(false);
    }

    /**
     * Save the game state.
     */
    @FXML
    public void saveGameState(ActionEvent event) throws IOException {
        Main.saveGameState();
        goHome(event);
    }

    /**
     * Create new user.
     */
    @FXML
    public void newUserBtn(ActionEvent event) throws IOException {
        Main.stage.setScene(Main.createScene(new FXMLLoader(Main.fxmlUserReg)));
    }

    @FXML
    ListView usersList;

    /**
     * Go to the users scene.
     */
    @FXML
    public void usersBtn(ActionEvent event) throws IOException {
        Main.stage.setScene(Main.createScene(new FXMLLoader(Main.fxmlUsers)));
        Main.setProfilesList(usersList);
    }

    /**
     * Open levels scene.
     */
    @FXML
    public void openLevelsScene() throws IOException {
        if (usersList.getSelectionModel().getSelectedItem() != null) {
            Main.openProfileLevels((String) usersList.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Delete user.
     */
    @FXML
    public void deleteUser() throws IOException {
        if (usersList.getSelectionModel().getSelectedItem() != null) {
            Main.deleteProfile((String) usersList.getSelectionModel().getSelectedItem());
            FileIO.deleteAllLevelStates((String) usersList.getSelectionModel().getSelectedItem());
            Main.stage.setScene(Main.createScene(new FXMLLoader(Main.fxmlUsers)));
            Main.setProfilesList(usersList);
        }
    }

    @FXML
    TextField newUserName;

    /**
     * Create new user.
     */
    @FXML
    public void createNewUser(ActionEvent event) throws IOException {
        if (!newUserName.getText().isEmpty()) {
            Main.createNewProfile(newUserName.getText());
        }
    }

    // level buttons
    @FXML
    Button levelbtn1;
    @FXML
    Button levelbtn2;
    @FXML
    Button levelbtn3;
    @FXML
    Button levelbtn4;
    @FXML
    Button levelbtn5;
    @FXML
    Button levelbtn6;

    @FXML
    public ArrayList<Button> levelBtns;

    @FXML
    public void setLevelBtns() {
        levelBtns = new ArrayList<>();
        levelBtns.add(levelbtn1);
        levelBtns.add(levelbtn2);
        levelBtns.add(levelbtn3);
        levelBtns.add(levelbtn4);
        levelBtns.add(levelbtn5);
        levelBtns.add(levelbtn6);
    }

    @FXML
    public ArrayList<Button> getLevelBtns() {
        return levelBtns;
    }

    /**
     * Open level on the level button click.
     */
    @FXML
    public void openLevel(ActionEvent event) throws IOException {
        Button levelBtn = (Button) event.getTarget();
        int levelNum = Integer.parseInt(levelBtn.getId().
                replaceAll("levelbtn", ""));

        File file = null;
        if (Main.currentProfile != null) {
            file = FileIO.levelStateExists(Main.currentProfile.getName(), levelNum);
        }

        if (file == null) {
            Main.openLevel(levelNum);
        } else {
            Main.playGameFromSavedState(file, levelNum);
        }
    }

    /**
     * Go score board scene.
     */
    @FXML
    public void scoreBoardBtn(ActionEvent event) throws IOException {
        Main.stage.setScene(Main.createScene(new FXMLLoader(Main.fxmlScoreBoard)));
    }

    @FXML
    ListView highScoreTable;

    /**
     * Show high score table.
     */
    @FXML
    public void showHighScoreTable(ActionEvent event) throws IOException {
        highScoreTable.getItems().clear();
        Button levelBtn = (Button) event.getTarget();
        String levelNum = levelBtn.getId().replaceAll("levelbtn", "");
        ArrayList<String> table = FileIO.readHighScoreTable(levelNum);
        for (int i = 0; i < table.size(); i++) {
            highScoreTable.getItems().add(table.get(i));
        }
    }
}
