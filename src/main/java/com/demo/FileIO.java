package com.demo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileIO {
    public static ArrayList<String> data;
    public static String line;

    public static ArrayList<String> readLevel (int levelNumber) {
        data = new ArrayList<>();
        try {
            File levelFile = new File("data/" + "level" + levelNumber + ".txt");
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

}
