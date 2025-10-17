import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Owen O'Neil
//10/16/25
//Program 2: AVL and Red Black Trees


public class Program2Main {
    public static void main(String[] args){
        BinaryTree binaryTree = new BinaryTree();
        AVLTree avlTree = new AVLTree();
        try{
            File file = new File("SciFiLiBooks.txt");
            Scanner scanner = new Scanner(file);
            long startTimeBin = System.nanoTime();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                binaryTree.insert(line);
                }
            scanner.close();
            long endTimeBin = System.nanoTime();
            System.out.println("Binary tree insert took " + (endTimeBin - startTimeBin) + " nanoseconds.");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        try{
            File file = new File("SciFiLiBooks.txt");
            Scanner scanner = new Scanner(file);
            long startTimeAVL = System.nanoTime();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                avlTree.insert(line);
            }
            scanner.close();
            long endTimeAVL = System.nanoTime();
            System.out.println("AVL tree insert took " + (endTimeAVL - startTimeAVL) + " nanoseconds.");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
