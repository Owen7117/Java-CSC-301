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
            long startTimeBinInsert = System.nanoTime();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                binaryTree.insert(line);
                }
            scanner.close();
            long endTimeBinInsert = System.nanoTime();
            System.out.println("Binary tree insert took " + (endTimeBinInsert - startTimeBinInsert) + " nanoseconds.");
            long startTimeBinSearch = System.nanoTime();
            binaryTree.search("SomeBookNotInTre");
            long endTimeBinSearch = System.nanoTime();
            System.out.println("Binary tree search took " + (endTimeBinSearch - startTimeBinSearch) + " nanoseconds.");
            long startTimeBinRemove = System.nanoTime();
            binaryTree.remove("Anathem");
            long endTimeBinRemove= System.nanoTime();
            System.out.println("Binary tree remove took " + (endTimeBinRemove- startTimeBinRemove) + " nanoseconds.");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        try{
            File file = new File("SciFiLiBooks.txt");
            Scanner scanner = new Scanner(file);
            long startTimeAVLInsert = System.nanoTime();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                avlTree.insert(line);
            }
            scanner.close();
            long endTimeAVLInsert = System.nanoTime();
            System.out.println("AVL tree insert took " + (endTimeAVLInsert - startTimeAVLInsert) + " nanoseconds.");
            long startTimeAVLSearch = System.nanoTime();
            avlTree.search("SomeBookNotInTre");
            long endTimeAVLSearch = System.nanoTime();
            System.out.println("AVL tree search took " + (endTimeAVLSearch - startTimeAVLSearch) + " nanoseconds.");
            long startTimeAVLRemove = System.nanoTime();
            avlTree.remove("Anathem");
            long endTimeAVLRemove= System.nanoTime();
            System.out.println("AVL tree remove took " + (endTimeAVLRemove- startTimeAVLRemove) + " nanoseconds.");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
