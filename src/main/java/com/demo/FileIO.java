package com.demo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

/** FileIO.java
 * 
 * @author user
 * @version 2.0
 */
public abstract class FileIO {
    
	public static ArrayList<String> data;
    public static String line;

    public static ArrayList<String> readLevel (int levelNumber) {
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

    public static ArrayList<String> readLevelState (File file) {
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

    public static void addToTheFile(String fileName, String data){
        try {
            Files.write(Paths.get("data/" + fileName), data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("An error occurred while adding the data/" + fileName + " file.");
        }
    }

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

    public static void removeProfile(String username) throws IOException {
        File temp = new File("data/profilesTemp.txt");
        File file = new File("data/profiles.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        while((line = reader.readLine()) != null) {
            if(line.split(" ")[1].equals(username)) {
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

    public static File levelStateExists(String username, String levelNum) {
        File file = new File("data/levelStates/" + username + levelNum + ".txt");
        if(file.exists() && !file.isDirectory()) {
            return file;
        }
        return null;
    }

    public static void deleteAllLevelStates(String username) {
        for (int i = 1; i <= Main.LEVELS; i ++) {
            File file = new File("data/levelStates/" + username + i + ".txt");
            if(file.exists() && !file.isDirectory()) {
                file.delete();
            }
        }
    }

    public static void deleteLevelState(String username, int levelNum) {
        File file = new File("data/levelStates/" + username + levelNum + ".txt");
        if(file.exists() && !file.isDirectory()) {
            file.delete();
        }
    }
}
