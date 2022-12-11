package com.demo;
import java.io.IOException;

public class Profile {
    private String name;
    private int maxLevelUnlocked;
    private int score;

    public Profile(String name) throws IOException {
        this.name = name;
        maxLevelUnlocked = 1;
        score = 0;
        saveProfileData();
    }

    public Profile(String name, int maxLevelUnlocked, int score) throws IOException {
        this.name = name;
        this.maxLevelUnlocked = maxLevelUnlocked;
        this.score = score;
    }

    public static void updateProfilesData() {
        FileIO.writeProfiles();
    }

    public void saveProfileData() {
        FileIO.addToTheFile("profiles.txt",
                "%user% " + name + " 1 0" + "\n");
    }

    public void setLevelBtns(Controller controller) throws IOException {
        for (int i = 0; i < controller.getLevelBtns().size(); i++) {
            if (i+1 <= maxLevelUnlocked) {
                controller.levelBtns.get(i).setDisable(false);
            } else {
                controller.levelBtns.get(i).setDisable(true);
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxLevelUnlocked() {
        return maxLevelUnlocked;
    }

    public void setMaxLevelUnlocked(int maxLevelUnlocked) {
        this.maxLevelUnlocked = maxLevelUnlocked;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
