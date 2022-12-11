package com.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Puzzle {
    static final String PUZZLE_URL = "http://cswebcat.swansea.ac.uk/";
    static final String USER_AGENT = "Software Engineering CS-230, 'Jewel Chase', Group 28";
    static final String MODULE_ID = "CS-230";

    public String Puzzle() throws IOException {
        String code = getCode("puzzle/");
        System.out.println(code);
        String solvedCode = solvePuzzle(code);
        System.out.println(solvedCode);
        String message = getCode("message?solution=" + solvedCode);
        System.out.println(message);

        return getCode("message?solution=" + solvedCode);
    }

    public static String getCode(String urlDirectory) throws IOException {
        URL url = new URL(PUZZLE_URL + urlDirectory);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        BufferedReader lol = new BufferedReader (new InputStreamReader(connection.getInputStream()));
        String currentLine;
        StringBuffer response = new StringBuffer();

        while ((currentLine = lol.readLine()) != null){
            response.append(currentLine);
        }

        lol.close();

        return response.toString();
    }

    public static String solvePuzzle(String code) {
        String solved = "";

        for (int i = 0; i != code.length(); i++) {
            solved += solveChar(code.charAt(i), i);
        }
        solved += MODULE_ID;
        solved = solved.length() + solved;
        return solved;
    }

    public static char solveChar(char currentChar, int charNumber) {
        charNumber++;
        if ((charNumber) % 2 != 0) {
            charNumber = -charNumber;
        }
        char solvedChar = (char) ((int) currentChar + charNumber);
        return verifyChar(solvedChar);
    }

    public static char verifyChar(char testChar) {
        boolean isInRange = true;
        char workingChar = testChar;
        if (testChar > 90) {
            workingChar = (char) (testChar - 26);
            isInRange ^= false;
        }
        if (workingChar < 65) {
            workingChar = (char) (workingChar + 26);
            isInRange = false;
        }
        if (!isInRange) {
            verifyChar(workingChar);
        }
        return workingChar;
    }
}
