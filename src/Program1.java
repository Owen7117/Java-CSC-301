import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program1 {
     public static void main(String[] args) {

         List<Character> linkedlist = new List<>();
         try {
             File file = new File("prog1input1.txt"); // file in project root
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
         int size = linkedlist.GetSize();
         linkedlist.First();
         for(int j = 0; j < size; j++) {
             System.out.print(linkedlist.GetValue());
             linkedlist.Next();

         }
     }
}