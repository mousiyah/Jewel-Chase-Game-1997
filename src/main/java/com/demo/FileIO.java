package com.demo;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileIO class to read and write to files.
 * @author Muslima Karimova 2130288
 * @version 2.0
 */

public abstract class FileIO {
    public static ArrayList<String> data;
    public static String line;

    /**
     * Read level file.
     * @param levelNumber
     * @return data
     */
    public static ArrayList<String> readLevel(int levelNumber) {
        data = new ArrayList<>();
        try {
            File levelFile = new File("data/levels/" + "level" + levelNumber + ".txt");
            Scanner in = new Scanner(levelFile);
            while (in.hasNextLine()) {
                data.add(in.nextLine());
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading level file.");
        }

        return data;
    }

    /**
     * Read level state.
     * @param file source file
     * @return data
     */
    public static ArrayList<String> readLevelState(File file) {
        data = new ArrayList<>();
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                data.add(in.nextLine());
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading level file.");
        }

        return data;
    }

    /**
     * Add some data to the file.
     * @param fileName
     * @param data
     */
    public static void addToTheFile(String fileName, String data) {
        try {
            Files.write(Paths.get("data/" + fileName), data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("An error occurred while adding the data/" + fileName + " file.");
        }
    }

    /**
     * Read Profiles.
     * @return data
     */
    public static ArrayList<String> readProfiles() {
        data = new ArrayList<>();
        try {
            File levelFile = new File("data/profiles.txt");
            Scanner in = new Scanner(levelFile);
            while (in.hasNextLine()) {
                line = in.nextLine();
                if (line.split(" ")[0].equals("%user%")) {
                    data.add(line.split(" ")[1] + " " +
                            line.split(" ")[2] + " " + line.split(" ")[3]);
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading level file.");
        }

        return data;
    }

    /**
     * Remove Profile from profiles file.
     * @param username of a Profile
     */
    public static void removeProfile(String username) throws IOException {
        File temp = new File("data/profilesTemp.txt");
        File file = new File("data/profiles.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        while ((line = reader.readLine()) != null) {
            if (line.split(" ")[1].equals(username)) {
                continue;
            }
            writer.write(line);
            writer.write("\n");
        }
        writer.close();
        reader.close();
        file.delete();
        temp.renameTo(file);
    }

    /**
     * Write profiles list from main to the file.
     */
    public static void writeProfiles() {
        try {
            PrintWriter file = new PrintWriter("data/profiles.txt");
            file.print("");
            for (int i = 0; i < Main.profiles.size(); i++) {
                file.write("%user% " + Main.profiles.get(i).getName() + " "
                        + Main.profiles.get(i).getMaxLevelUnlocked() + " "
                        + Main.profiles.get(i).getScore());
                file.write("\n");
            }
            file.close();
        } catch (IOException e) {
            System.out.println("An error occurred while updating profiles file");
        }
    }

    /**
     * Write saved level state to the file.
     * @param levelData data of the saved level state
     * @param profile profile who was logged in
     */
    public static void writeLevelState(ArrayList<String> levelData, Profile profile) {
        try {
            FileWriter fileWriter = new FileWriter("data/levelStates/"
                    + profile.getName() + Main.level.levelNumber + ".txt");
            for (int i = 0; i < levelData.size(); i++) {
                fileWriter.write(levelData.get(i));
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the game state");
        }
    }

    /**
     * Check if level state exists.
     * @param username Profile username for saved level state
     * @param levelNum level number for saved level state
     * @return the file if yes, null if no
     */
    public static File levelStateExists(String username, int levelNum) {
        File file = new File("data/levelStates/" + username + levelNum + ".txt");
        if (file.exists() && !file.isDirectory()) {
            return file;
        }
        return null;
    }

    /**
     * Delete all level states for a particular Profile.
     * @param username Profile username for saved level state
     */
    public static void deleteAllLevelStates(String username) {
        for (int i = 1; i <= Main.LEVELS; i++) {
            File file = new File("data/levelStates/" + username + i + ".txt");
            if (file.exists() && !file.isDirectory()) {
                file.delete();
            }
        }
    }

    /**
     * Delete particular level state for a particular Profile.
     * @param username Profile username for saved level state
     * @param levelNum level number for saved level state
     */
    public static void deleteLevelState(String username, int levelNum) {
        File file = new File("data/levelStates/" + username + levelNum + ".txt");
        if (file.exists() && !file.isDirectory()) {
            file.delete();
        }
    }

    /**
     * Read high score table for a particular level.
     * @param levelNum level number to read high score for
     * @return high score table
     */
    public static ArrayList<String> readHighScoreTable(String levelNum) throws IOException {
        File file = new File("data/highScoreTables/table" + levelNum + ".txt");
        file.createNewFile();
        data = new ArrayList<>();
        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            data.add(in.nextLine());
        }
        in.close();
        return data;
    }

    /**
     * Update high score table for a particular level.
     * @param levelNum level number high score table needs to be updated for
     * @param username username of the Profile
     * @param score score of the profile for that level
     */
    public static void updateHighScoreTable(int levelNum, String username, int score) throws IOException {
        File file = new File("data/highScoreTables/table" + levelNum + ".txt");

        if (file.length() == 0) {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(username + " " + score + "\n");
            fileWriter.close();
        } else {
            Scanner in = new Scanner(file);
            ArrayList<String> newTable = new ArrayList<>();
            boolean scoreWritten = false;
            while (in.hasNextLine()) {
                if (newTable.size() >= 10) {
                    break;
                }
                line = in.nextLine();
                if (score > Integer.parseInt(line.split(" ")[1]) && !scoreWritten) {
                    newTable.add(username + " " + score);
                    newTable.add(line);
                    scoreWritten = true;
                } else {
                    newTable.add(line);
                }
            }

            // clear the file
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();

            Path out = Paths.get(file.getPath());
            Files.write(out, newTable, Charset.defaultCharset());
        }
    }
}
