package com.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class to receive an encrypted puzzle from a domain,send the decrypted puzzle back and receive the message of the day.
 * @author Osian Sutton
 * @version 1.1
 */

public class Puzzle {
    static final String PUZZLE_URL = "http://cswebcat.swansea.ac.uk/";
    static final String USER_AGENT = "Software Engineering CS-230, 'Jewel Chase', Group 28";
    static final String MODULE_ID = "CS-230";

    /**
     * Outputs the Message of the Day from the Computer Science Puzzle website.
     * @return Message of the Day from the website, or any error message that is received
     * @throws IOException If an error occurs when accessing the website, return an error message
     */
    public String Puzzle() throws IOException {
        String code = getCode("puzzle/");
        System.out.println(code);
        String solvedCode = solvePuzzle(code);
        System.out.println(solvedCode);
        String message = getCode("message?solution=" + solvedCode);
        System.out.println(message);

        return getCode("message?solution=" + solvedCode);
    }


    /**
     * Access the website and return the result.
     * @param urlDirectory URL to access
     * @return Output from the website
     * @throws IOException
     */
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

    /**
     * Decrypts a String passed to this method.
     * @param code Encrypted String
     * @return A decrypted String
     */
    public static String solvePuzzle(String code) {
        String solved = "";

        for (int i = 0; i != code.length(); i++) {
            solved += solveChar(code.charAt(i), i);
        }
        solved += MODULE_ID;
        solved = solved.length() + solved;
        return solved;
    }

    /**
     * Changes a character's value by following the rules of the cipher being used.
     * @param currentChar Current character in the sequence
     * @param charNumber Current character's location in the sequence
     * @return The current character after having its value changed and verified
     */
    public static char solveChar(char currentChar, int charNumber) {
        charNumber++;
        if ((charNumber) % 2 != 0) {
            charNumber = -charNumber;
        }
        char solvedChar = (char) ((int) currentChar + charNumber);
        return verifyChar(solvedChar);
    }

    /**
     * Recursive method that verifies that the character is still a character ranging from A - Z.
     * @param testChar Character passed from the solveChar method, this character is to be tested
     * @return Character within the range of A - Z
     */
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
