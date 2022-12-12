package com.demo;
import java.io.IOException;

/**
* Profile class represents game user's profile.
* @author Muslima Karimova 2130288
* @version 2.0
*/

public class Profile {
    private String name;
    private int maxLevelUnlocked;
    private int score;

    /**
    * Constructor to create new Profile.
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
    * Constructor to create a profile which exists in the system.
    * @param name
    * @param maxLevelUnlocked
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
    * Save profile data to the file.
    */
    public void saveProfileData() {
        FileIO.addToTheFile("profiles.txt",
                "%user% " + name + " 1 0" + "\n");
    }

    /**
     * Write profile data to the file.
     */
    public static void updateProfilesData() {
        FileIO.writeProfiles();
    }

    /**
    * Set disabled/enabled level buttons depending on maximum level unlocked.
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
    * @param maxLevelUnlocked maximum level reached by player
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
