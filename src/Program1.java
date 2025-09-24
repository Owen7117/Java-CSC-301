// Owen O'Neil
//9/12/25
//CSC-301 Program 1

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Program1 {
    public static void main(String[] args) {
        // Initialize the linked list
        List<Character> linkedlist = new List<>();
        try {
            // Set the input file
            File file = new File("prog1input1.txt");
            Scanner scanner = new Scanner(file);
            // Take in the file and insert the characters into the linked list
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    linkedlist.InsertAfter(c);
                }
            }
            scanner.close();
            // In case the file is not found, there is a message displayed to let us know
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        // Set the pattern
        String pattern = "Ickle";
        long startTimeBrute = System.nanoTime();
        BruteForce(linkedlist, pattern);
        long endTimeBrute = System.nanoTime();
        System.out.println("Brute force took " + (endTimeBrute - startTimeBrute) + " nanoseconds.");
        long startTimeKMP = System.nanoTime();
        KMP(linkedlist, pattern);
        long endTimeKMP = System.nanoTime();
        System.out.println("KMP took " + (endTimeKMP - startTimeKMP) + " nanoseconds");
        BoyerMoore(linkedlist, pattern);
    }

    private static void BruteForce(List<Character> linkedlist, String pattern) {
        int size = linkedlist.GetSize();
        for (int i = 0; i <= size - pattern.length(); i++) {
            linkedlist.First();
            //set the head every time so you can get back to the right node after every check
            for (int j = 0; j < i; j++) {
                linkedlist.Next();
                // this is the next node in the list based on the index of the previous loop
            }
            int k = 0;
            boolean same = true;
            while (k < pattern.length()) {
                if (linkedlist.GetValue() != pattern.charAt(k)) {
                    same = false;
                    break;
                }
                linkedlist.Next();
                k++;
            }
            if (same) {
                System.out.println("Pattern found at index " + i);
            }
        }
    }

    /*
    ChatGPT helped me understand that the failure function keeps track of the longest matching prefix
    and suffix in the pattern. The length increases when characters match and regresses when there’s
    a mismatch, allowing us to check the same index again after falling back.
    */
    private static List<Integer> FailureFunction(String pattern) {
        // Create the failure function linked list
        List<Integer> fail = new List<>();
        // The first index will always be 0
        fail.InsertAfter(0);
        int len = 0;
        // Start at the second character when comparing
        int patternPos = 1;
        // while the pattern position is less than the pattern length.
        while (patternPos < pattern.length()) {
            // If the two indexes have the same character then increase the match length and add the length to the linked list, as well as increase the pattern positon
            if (pattern.charAt(patternPos) == pattern.charAt(len)) {
                len++;
                fail.InsertAfter(len);
                patternPos++;
                // if the characters are not the same
            } else {
                /*
                Chat.GPT helped me understand that when a character fails to match, and the length is not 0(there was a previous match),
                 we move the back to the last matched index and set its corresponding value in the list as the length
                 so we can check previous matches rather than completely restarting(like brute force)
                 */
                if (len != 0) {
                    // There was a mismatch so go back to the previous longest match in the failure function and set the correct fallback length
                    fail.SetPos(len - 1);
                    len = fail.GetValue();
                } else {
                    // There was no match and the length is 0 so then just insert 0 at the index and move the pattern position to the next index
                    fail.InsertAfter(0);
                    patternPos++;
                }
            }
        }
        return fail;
    }

    private static void KMP(List<Character> linkedlist, String pattern) {
        // Call the failure function linked list and pass through the pattern
        List<Integer> fail = FailureFunction(pattern);
        int llsize = linkedlist.GetSize();
        int pl = pattern.length();
        int textIndex = 0;
        int patternIndex = 0;
        // While there is enough character for the pattern to be possible
        while (textIndex < llsize) {
            // Go through the linked list text
            linkedlist.SetPos(textIndex);
            // If there is a character match in the linked list
            if (linkedlist.GetValue() == pattern.charAt(patternIndex)) {
                // Increment the two indexes
                textIndex++;
                patternIndex++;
                // if the pattern index is the same as the pattern length(all the characters match)
                if (patternIndex == pl) {
                    // Print the starting index it was found
                    System.out.println("Pattern found at index " + (textIndex - patternIndex));
                    // There is a chance that the pattern could overlap so you must fall back and check as if the pattern failed
                    fail.SetPos(patternIndex - 1);
                    patternIndex = fail.GetValue();
                }
            } else {
                /*
                 Chat.GPT helped me understand this for the failure function and conceptually the fallback is the same for KMP
                */
                if (patternIndex != 0) {
                    fail.SetPos(patternIndex - 1);
                    patternIndex = fail.GetValue();
                }
                // Move to the next index in the linked list
                else {
                    textIndex++;
                }
            }
        }
    }

    private static void BoyerMoore(List<Character> textList, String pattern) {
        int textSize = textList.GetSize();
        int patLen = pattern.length();
        // Linked lists to store characters and their last occurrence
        List<Character> patChars = new List<>();
        List<Integer> patPositions = new List<>();
        // Fill lists
        for (int i = 0; i < patLen; i++) {
            patChars.InsertAfter(pattern.charAt(i));
            patPositions.InsertAfter(i);
        }
        int shift = 0;
        while (shift <= textSize - patLen) {
            int j = patLen - 1;
            while (j >= 0) {
                textList.SetPos(shift + j);
                if (textList.GetValue() != pattern.charAt(j)) {
                    break;
                }
                j--;
            }
            if (j < 0) {

                System.out.println("Pattern found at index " + shift);
                shift++;
            }
            else {
                textList.SetPos(shift + j);
                char badChar = textList.GetValue();
                int lastOccur = -1;
                patChars.First();
                patPositions.First();
                for (int k = 0; k < patLen; k++) {
                    if (patChars.GetValue() == badChar) {
                        lastOccur = patPositions.GetValue();
                    }
                    patChars.Next();
                    patPositions.Next();
                }
                int skip = Math.max(1, j - lastOccur);
                shift += skip;
            }
        }
    }
}

