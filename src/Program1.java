import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program1 {
     public static void main(String[] args) {

         List<Character> linkedlist = new List<>();
         try {
             File file = new File("prog1input1.txt");
             Scanner scanner = new Scanner(file);
             while (scanner.hasNextLine()) {
                 String line = scanner.nextLine();
                 for(int i = 0; i < line.length(); i++) {
                     char c = line.charAt(i);
                     linkedlist.InsertAfter(c);
                 }
             }
             scanner.close();
         } catch (FileNotFoundException e) {
             System.out.println("File not found: " + e.getMessage());
         }
         String pattern = "Ickle";
         int size = linkedlist.GetSize();
         long startTimeBrute = System.currentTimeMillis();
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
             if(same){
                 System.out.println(i);
             }
         }
         long endTimeBrute = System.currentTimeMillis();
         System.out.println("Brute force took " + (endTimeBrute - startTimeBrute) + " milliseconds");
     }

    /*
    ChatGPT helped me understand that the failure function keeps track of the longest matching prefix
    and suffix in the pattern. The length increases when characters match and regresses when there’s
    a mismatch, allowing us to check the same index again after falling back.
    */
    private static int[] FailureFunction(pattern) {
        // Create the failure function array
        int[] fail = new int[pattern.length()];
        // Set the first index to 0 since it the first comparison will always be 0
        fail[0] = 0;
        // Create the length value that hold the correct number of letters that match
        int len = 0;

        // For the length of the pattern
        for (int i = 1; i < pattern.length(); i++) {
            // If the character and pattern index i and pattern index (length) match
            if (pattern.charAt(i) == pattern.charAt(len)) {
                // increase the length
                len++;
                // And set the failure function array at index i to that length and then move on to the next index to compare
                fail[i] = len;
            }
            // If they dont match
            else {
                // And the length is not 0 (Chat.gpt helped understand this part)
                if (len != 0) {
                    // Fall back to previous prefix-suffix length
                    len = fail[len - 1];
                    // Retry this index with shorter prefix
                    // i--;
                }
                // If the length is 0 then set the failure function array to 0 at the corresponding index
                else {
                    fail[i] = 0;
                }
            }
        }
        return fail;
    }
}


