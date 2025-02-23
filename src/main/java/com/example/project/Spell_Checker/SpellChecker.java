package com.example.project.Spell_Checker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker {
    private ArrayList<String> dictionary;
    private int loopCounter = 0;

    public SpellChecker() {
        dictionary = new ArrayList<>();
        importDictionary();
    }

    public ArrayList<String> getDictionary() {
        return dictionary;
    }

    public int getLoopCounter() {
        return loopCounter;
    }

    /**
     * This uses LINEAR search to find a word in the dictionary and returns
     * true if the word is in the dictionary and false otherwise.
     *
     * It also tracks the number of words checked (loop iterations) and
     * prints that value out before returning.
     */
    public boolean linearSpellCheck(String word) {
        loopCounter = 0; // Reset the loop counter
        String lowerCaseWord = word.toLowerCase();
        for (String s : dictionary) {
            loopCounter++; // Increment the loop counter
            if (lowerCaseWord.equals(s.toLowerCase())) {
                System.out.println("-- LINEAR SEARCH: Number of words checked (loop iterations): " + loopCounter);
                return true;
            }
        }
        System.out.println("-- LINEAR SEARCH: Number of words checked (loop iterations): " + loopCounter);
        return false;
    }

    /**
     * This uses BINARY search to find a word in the dictionary and returns
     * true if the word is in the dictionary and false otherwise.
     *
     * It also tracks the number of words checked (loop iterations) and
     * prints that value out before returning.
     */
    public boolean binarySpellCheck(String word) {
        loopCounter = 0; // Reset the loop counter
        String lowerCaseWord = word.toLowerCase();
        int leftIdx = 0;
        int rightIdx = dictionary.size() - 1;

        while (leftIdx <= rightIdx) {
            loopCounter++; // Increment the loop counter
            int middle = (leftIdx + rightIdx) / 2;
            String middleWord = dictionary.get(middle).toLowerCase();

            if (lowerCaseWord.compareTo(middleWord) > 0) {
                leftIdx = middle + 1;
            } else if (lowerCaseWord.compareTo(middleWord) < 0) {
                rightIdx = middle - 1;
            } else {
                System.out.println("-- BINARY SEARCH: Number of words checked (loop iterations): " + loopCounter);
                return true;
            }
        }
        System.out.println("-- BINARY SEARCH: Number of words checked (loop iterations): " + loopCounter);
        return false;
    }

    /**
     * Private helper method, called in the constructor, which loads the words
     * from the dictionary.txt text file into the "dictionary" instance variable.
     */
    private void importDictionary() {
        try {
            File myFile = new File("src/main/java/com/example/project/Spell_Checker/dictionary.txt");
            Scanner fileScanner = new Scanner(myFile);
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                dictionary.add(data);
            }
            dictionary.sort(String::compareToIgnoreCase); // Sort the dictionary
            System.out.println("\ndictionary.txt file imported and sorted successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker();
        System.out.println("----------------------------- SPELL CHECKER -------------------------------");
        System.out.println("The spell checker dictionary has " + checker.getDictionary().size() + " words in it!");

        System.out.println("Enter a word to look up; if the word is not found, it is not");
        System.out.print("spelled correctly (or is simply not a word), or type q to quit: ");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine().trim();

        while (!word.equalsIgnoreCase("q")) {
            if (checker.binarySpellCheck(word)) {
                System.out.println("-- " + word + " WAS found in the dictionary (so it's spelled correctly)");
            } else {
                System.out.println("-- " + word + " was NOT found in the dictionary (so it's not spelled correctly)");
            }
            System.out.print("Enter a word to look up or q to quit: ");
            word = scan.nextLine().trim();
        }
        scan.close();
    }
}