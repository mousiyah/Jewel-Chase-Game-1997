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
     * This method sends a GET request to a page on the website and returns the response inside a String.
     *
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
     * Each character is encrypted based on its location within the String, therefore each character
     * , and their positioning is passed to the solveChar method.
     * After each character is decrypted, the course module ID is appended to the end of the String.
     * The length of the String is then counted, and then appended to the beginning of the String.
     *
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
     * In order to decrypt each character we must know what the position of the character is within the sequence.
     * For characters that have an odd numbered position, denoted by x,
     * its value should be replaced with the value of the character x characters backwards within the English alphabet.
     * For characters that have an even numbered position, denoted by y,
     * its value should be replaced with the value of the character y characters forward within the English alphabet.
     * This method determines if the character is in an odd or even position in the sequence,
     * and then changes the value according to the rules of the cipher.
     * It then tests to see if that character is within the range of A - Z.
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
     * The range of ASCII IDs 65 and 90 are populated by the captial letters of the English alphabet in ascending order.
     * This method first tests if the character is below ID 65, and adds 26 to its ID if that is the case.
     * It then tests if the character is above ID 90, and subtracts 26 if that is the case.
     * This method calls itself until the ID does not need to be changed.
     *
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
