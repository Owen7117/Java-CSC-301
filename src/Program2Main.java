import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Owen O'Neil
//10/16/25
//Program 2: AVL and Red Black Trees

//Main program that runs and times each method for Binary, AVL and Red Black trees

public class Program2Main {
    public static void main(String[] args){
        // Create the trees
        BinaryTree binaryTree = new BinaryTree();
        AVLTree avlTree = new AVLTree();
        RedBlackTree redblackTree = new RedBlackTree();
        // Insert, search, and remove the data while timing each into the Binary tree
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
        // Insert, search, and remove the data while timing each into the AVL tree
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
        // Insert, search, and remove the data while timing each into the Red Black tree
        try{
            File file = new File("SciFiLiBooks.txt");
            Scanner scanner = new Scanner(file);
            long startTimeRBTInsert = System.nanoTime();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                redblackTree.insert(line);
            }
            scanner.close();
            long endTimeRBTInsert = System.nanoTime();
            System.out.println("Red black tree insert took " + (endTimeRBTInsert - startTimeRBTInsert) + " nanoseconds.");
            long startTimeRBTSearch = System.nanoTime();
            redblackTree.search("SomeBookNotInTre");
            long endTimeRBTSearch = System.nanoTime();
            System.out.println("Red black tree search took " + (endTimeRBTSearch - startTimeRBTSearch) + " nanoseconds.");
            long startTimeRBTRemove = System.nanoTime();
            redblackTree.remove("Anathem");
            long endTimeRBTRemove= System.nanoTime();
            System.out.println("Red black tree remove took " + (endTimeRBTRemove- startTimeRBTRemove) + " nanoseconds.");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}

/*
Binary trees are the simplest tree to use, but as the amount of data getting inserted increases, the inserts become
slower because the tree isn't balanced. AVL trees are a little harder to implement, but it keeps the tree balanced which
make the searching faster but makes insertion and removal slower because you need to keep track of the height of each
node. Red Black trees are the hardest to implement because of their balancing rules with red and black nodes, but it
also allows for the insertion to be faster than AVL and Binary while still keeping the search and removal speed quick.
Binary trees are best for small datasets so the tree won't have enough data to become super unbalanced. ALV trees are
best for application where searching is the most important. Red Black trees are best then there are frequent inerts and
deletes.
 */