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
         String word = "Ickle";
         int size = linkedlist.GetSize();
         long startTimeBrute = System.currentTimeMillis();
         for (int i = 0; i <= size - word.length(); i++) {
             linkedlist.First();
             //set the head every time so you can get back to the right node after every check
             for (int j = 0; j < i; j++) {
                 linkedlist.Next();
                 // this is the next node in the list based on the index of the previous loop
             }
             int k = 0;
             boolean same = true;
             while (k < word.length()) {
                 if (linkedlist.GetValue() != word.charAt(k)) {
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
}
