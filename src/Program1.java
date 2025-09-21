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
             File file = new File("prog1input1.txt");
             Scanner scanner = new Scanner(file);
             while (scanner.hasNextLine()) {
                 String line = scanner.nextLine();
                 for (int i = 0; i < line.length(); i++) {
                     char c = line.charAt(i);
                     linkedlist.InsertAfter(c);
                 }
             }
             scanner.close();
         } catch (FileNotFoundException e) {
             System.out.println("File not found: " + e.getMessage());
         }
         String pattern = "Ickle";
         long startTimeBrute = System.currentTimeMillis();
         BruteForce(linkedlist, pattern);
         long endTimeBrute = System.currentTimeMillis();
         System.out.println("Brute force took " + (endTimeBrute - startTimeBrute) + " milliseconds");
         KMP(linkedlist, pattern);
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
                 System.out.println(i);
             }
         }
     }

    /*
    ChatGPT helped me understand that the failure function keeps track of the longest matching prefix
    and suffix in the pattern. The length increases when characters match and regresses when there’s
    a mismatch, allowing us to check the same index again after falling back.
    */
    private static List<Integer> FailureFunction(String pattern) {
        List<Integer> fail = new List<>();
        fail.InsertAfter(0);
        int len = 0;
        int i = 1;
        while (len < pattern.length()) {
            if(pattern.charAt(i) == pattern.charAt(len)){
                len++;
                fail.InsertAfter(len);
                i++;
            }
            else {
                if(len != 0){
                    fail.SetPos(len-1);
                    len = fail.GetValue();
                }
                else {
                    fail.InsertAfter(0);
                    i++;
                }
            }
        }
        return fail;
    }
    private static void KMP(List<Character> linkedlist, String pattern) {

    }
}


