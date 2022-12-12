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

/** Controller.java
 * 
 * @author user
 * @version 2.0
 *
 */
public class Controller {

    @FXML
    Text score;

    @FXML
    Text levelScore;

    @FXML
    protected void changeScore() {
        score.setText(Integer.toString(Score.getScore()));
    }

    @FXML
    ProgressBar progressBar;
    protected void changeProgressBar(int timeLeft, int timeLimit) {
        float progress = (float) timeLeft / (float) timeLimit;
        progressBar.setProgress(progress);
        progressBar.setAccessibleText(timeLeft + " seconds");
    }

    @FXML
    protected void updateScore() {
        levelScore.setText(Integer.toString(Score.getScore()));
        Main.currentProfile.setScore(Main.currentProfile.getScore()+Score.getScore());
        Profile.updateProfilesData();
    }

    @FXML
    Text levelNumber;

    @FXML
    public void setLevelNumber(int n) {
        levelNumber.setText(Integer.toString(n));
    }

    @FXML
    public void replay(ActionEvent event) throws IOException {
        Main.level.stopGame();
        Main.openLevel(Main.currentLevel);
        FileIO.deleteLevelState(Main.currentProfile.getName(),
                Main.currentLevel);
    }

    @FXML
    public void nextLevel(ActionEvent event) throws IOException {
        if (Main.currentLevel <= Main.LEVELS) {
            Main.currentLevel++;
            Main.currentProfile.setMaxLevelUnlocked(Main.currentLevel);
            Profile.updateProfilesData();
            Main.openLevel(Main.currentLevel);
        }
    }

    @FXML
    public void goHome(ActionEvent event) throws IOException {
        if (Main.level != null) {
            Main.level.stopGame();
        }
        Main.stage.setScene(Main.createScene(new FXMLLoader(Main.fxmlHome)));
    }

    @FXML
    public void goLevels(ActionEvent event) throws IOException {
        Main.stage.setScene(Main.createScene(new FXMLLoader(Main.fxmlLevels)));
        Main.controller.setLevelBtns();
        Main.currentProfile.setLevelBtns(Main.controller);
    }


    @FXML
    Button resumeGameBtn;

    @FXML
    Button pauseGameBtn;

    @FXML
    Button saveGameStateBtn;


    @FXML
    public void resumeGame(ActionEvent event) {
        Main.level.resumeGame();
        pauseGameBtn.setDisable(false);
        resumeGameBtn.setDisable(true);
        saveGameStateBtn.setDisable(true);
    }

    @FXML
    public void pauseGame(ActionEvent event) {
        Main.level.pauseGame();
        pauseGameBtn.setDisable(true);
        resumeGameBtn.setDisable(false);
        saveGameStateBtn.setDisable(false);
    }

    @FXML
    public void saveGameState(ActionEvent event) throws IOException {
        Main.saveGameState();
        goHome(event);
    }

    @FXML
    public void newUserBtn(ActionEvent event) throws IOException {
        Main.stage.setScene(Main.createScene(new FXMLLoader(Main.fxmlUserReg)));
    }

    @FXML
    public void scoreBoardBtn(ActionEvent event) {
    }

    @FXML
    ListView usersList;

    @FXML
    public void usersBtn(ActionEvent event) throws IOException {
        Main.stage.setScene(Main.createScene(new FXMLLoader(Main.fxmlUsers)));
        Main.setProfilesList(usersList);
    }

    @FXML
    public void openLevelsScene() throws IOException {
        if (usersList.getSelectionModel().getSelectedItem() != null) {
            Main.openProfileLevels((String) usersList.getSelectionModel().getSelectedItem());
        }
    }

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

    @FXML
    public void createNewUser(ActionEvent event) throws IOException {
        if (!newUserName.getText().isEmpty()) {
            Main.createNewProfile(newUserName.getText());
        }
    }

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

    public ArrayList<Button> levelBtns;

    public void setLevelBtns() {
        levelBtns = new ArrayList<>();
        levelBtns.add(levelbtn1);
        levelBtns.add(levelbtn2);
        levelBtns.add(levelbtn3);
        levelBtns.add(levelbtn4);
        levelBtns.add(levelbtn5);
        levelBtns.add(levelbtn6);
    }

    public ArrayList<Button> getLevelBtns() {
        return levelBtns;
    }


    @FXML
    public void openLevel(ActionEvent event) throws IOException {
        Button levelBtn = (Button) event.getTarget();
        int levelNum = Integer.parseInt(levelBtn.getId().
                replaceAll("levelbtn", ""));

        File file = null;
        if (Main.currentProfile != null) {
            file = FileIO.levelStateExists(Main.currentProfile.getName(),
                    String.valueOf(levelNum));
        }

        if (file == null) {
            Main.openLevel(levelNum);
        } else {
            Main.playGameFromSavedState(file, levelNum);
        }
    }
}