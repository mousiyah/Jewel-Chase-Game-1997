package com.demo;
import java.io.IOException;

/**
* 
* @author
* @author
* @author 
* @version 2.0
*/

public class Profile {
    private String name;
    private int maxLevelUnlocked;
    private int score;

    /**
    *
    * 
    * @param name
    * @throws IOException
    * @see IOException
    */
    public Profile(String name) throws IOException {
        this.name = name;
        maxLevelUnlocked = 1;
        score = 0;
        saveProfileData();
    }

    /**
    *
    * 
    * @param name
    * @param level_reached
    * @param score
    * @throws IOException
    * @see IOException
    */
    public Profile(String name, int maxLevelUnlocked, int score) throws IOException {
        this.name = name;
        this.maxLevelUnlocked = maxLevelUnlocked;
        this.score = score;
    }

    /**
    *
    * 
    */
    public static void updateProfilesData() {
        FileIO.writeProfiles();
    }

    /**
    *
    * 
    */
    public void saveProfileData() {
        FileIO.addToTheFile("profiles.txt",
                "%user% " + name + " 1 0" + "\n");
    }

    /**
    *
    * 
    * @param controller
    * @throws IOException
    * @see IOException
    */
    public void setLevelBtns(Controller controller) throws IOException {
        for (int i = 0; i < controller.getLevelBtns().size(); i++) {
            if (i+1 <= maxLevelUnlocked) {
                controller.levelBtns.get(i).setDisable(false);
            } else {
                controller.levelBtns.get(i).setDisable(true);
            }
        }

    }

    /**
    * @return name
    */
    public String getName() {
        return name;
    }

    /**
    * @param name
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * @return max level reached by player
    */
    public int getMaxLevelUnlocked() {
        return maxLevelUnlocked;
    }

    /**
    * @param max level reached by player
    */
    public void setMaxLevelUnlocked(int maxLevelUnlocked) {
        this.maxLevelUnlocked = maxLevelUnlocked;
    }

    /**
    * @return score
    */
    public int getScore() {
        return score;
    }

    /**
    * @param score
    */
    public void setScore(int score) {
        this.score = score;
    }
}
