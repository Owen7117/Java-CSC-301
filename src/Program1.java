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
            File file = new File("prog1input2.txt");
            Scanner scanner = new Scanner(file);
            // Take in the file and insert each character into a linked list node
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
        String pattern = "mmmmm";
        // Do the time calculation for BruteForce, KMP, and BoyerMoore
        long startTimeBrute = System.nanoTime();
        BruteForce(linkedlist, pattern);
        long endTimeBrute = System.nanoTime();
        System.out.println("Brute force took " + (endTimeBrute - startTimeBrute) + " nanoseconds.");
        long startTimeKMP = System.nanoTime();
        KMP(linkedlist, pattern);
        long endTimeKMP = System.nanoTime();
        System.out.println("KMP took " + (endTimeKMP - startTimeKMP) + " nanoseconds");
        long startTimeBoyerMoore = System.nanoTime();
        BoyerMoore(linkedlist, pattern);
        long endTimeBoyerMoore = System.nanoTime();
        System.out.println("BoyerMoore took " + (endTimeBoyerMoore - startTimeBoyerMoore) + " nanoseconds");
    }

    private static void BruteForce(List<Character> linkedlist, String pattern) {
        int size = linkedlist.GetSize();
        for (int i = 0; i <= size - pattern.length(); i++) {
            linkedlist.First();
            // Set the head every time, so you can get back to the right node after every check
            for (int j = 0; j < i; j++) {
                linkedlist.Next();
                // This is the next node in the list based on the index of the previous loop
            }
            int k = 0;
            boolean same = true;
            // Check if the characters are the same in the list and the pattern
            while (k < pattern.length()) {
                // If the characters are ever not the same then return false
                if (linkedlist.GetValue() != pattern.charAt(k)) {
                    same = false;
                    break;
                }
                // The character is the same, so go to the next node
                linkedlist.Next();
                // Go to the next character in the pattern
                k++;
            }
            if (same) {
                System.out.println("Pattern found at index " + i);
            }
        }
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
                 Chat.GPT helped me write/ this part and understand how conceptually, the fallback is the same for both the failure function and KMP
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

    public static void BoyerMoore(List<Character> linkedlist, String pattern) {
        // Get sizes of text and pattern
        int textSize = linkedlist.GetSize();
        int patternSize = pattern.length();
        int textPos = 0;
        // Searching through the list as long as the pattern can fit in the remaining text
        while (textPos <= textSize - patternSize) {
            // Start comparing from the last character of the pattern
            int patternIndex = patternSize - 1;
            // Count how many characters match from the end(chat let me understand that this is needed for bad character and good suffix)
            int matchedSuffixLen = 0;
            // Set the current node to the text character that corresponds with the last pattern character
            linkedlist.SetPos(textPos + patternIndex);
            // Compare the pattern backwards against the text
            while (patternIndex >= 0 && linkedlist.GetValue() == pattern.charAt(patternIndex)) {
                patternIndex--;
                matchedSuffixLen++;
                // Move to the previous character in the linked list if we haven't finished comparing
                if (patternIndex >= 0) {
                    linkedlist.Prev();
                }
            }
            // If the entire pattern matched (matchedSuffixLen is also the size of the pattern)
            if (patternIndex < 0) {
                // Print the starting index of the match
                System.out.println("Pattern found at index " + textPos);
                // Move past this match to continue searching
                textPos += patternSize;
            }
            else {
                // Calculate the bad character shift
                int bcShift = badCharacter(linkedlist, pattern, matchedSuffixLen);
                // Default good suffix shift is 1
                int gsShift = 1;
                // If some characters matched, calculate the good suffix shift
                if (matchedSuffixLen > 0) {
                    gsShift = goodSuffix(pattern, matchedSuffixLen);
                }
                // Move forward by the larger of the two shifts(what ever move the search faster)
                if (bcShift > gsShift) {
                    textPos += bcShift;
                }
                else {
                    textPos += gsShift;
                }
            }
        }
    }

    /*
    // Chat.GPT helped me write/understand that matched suffix is needed so you can determine where the mismatch occurred
    and calculate the distance to its last occurrence
    */
    private static int badCharacter(List<Character> linkedlist, String pattern, int matchedSuffixLen) {
        // Position in the pattern where the mismatch occurred
        int patternPos = pattern.length() - 1 - matchedSuffixLen;
        // Character in the text that caused the mismatch
        char badChar = linkedlist.GetValue();
        // Find last occurrence of the bad character in the pattern
        int lastOccurrence = -1;
        // Go through the pattern and find the last occurrence of the bad character
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == badChar) {
                // Update the last occurrence
                lastOccurrence = i;
            }
        }
        // Calculate the distance from the mismatch to the last occurrence
        int shift = patternPos - lastOccurrence;
        if (shift < 1) {
            // Minimum shift is 1
            shift = 1;
        }
        return shift;
    }

    private static int goodSuffix(String pattern, int matchedSuffixLen) {
        int patternSize = pattern.length();
        // Get the matched suffix as a string
        String suffix = pattern.substring(patternSize - matchedSuffixLen);
        // Chat wrote this part
        // Search for the rightmost occurrence of the suffix in the pattern (excluding the suffix itself)
        for (int i = patternSize - matchedSuffixLen - 1; i >= 0; i--) {
            if (pattern.startsWith(suffix, i)) {
                // Return how far to shift the pattern to align with this occurrence
                return patternSize - i - matchedSuffixLen;
            }
        }
        // If there is no other occurrence found, shift by the suffix length
        return matchedSuffixLen;
    }
}

/*
Brute force is a simple algorithm but is slow when it comes to long texts, so if you have a small text, Brute
force is good to use. KMP avoids the rechecking matched characters problem that Brute force has, making it more efficient,
especially for repeating patterns. BoyerMoore if the fastest out of both of the algorithms, as can be
seen by the data, because its bad character and good suffix rules allow it to skip sections that it knows won't have the
pattern. This makes it good for longer patterns or texts. Choosing one of the algorithms depends on the pattern length and
text size. Brute force is good for small texts, KMP is better for texts that have lots of overlap or repeat patterns, and
BoyerMoore is good for longer texts and patterns.
 */

