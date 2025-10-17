import java.util.*;

public class List<Type> {

    public static final int MAX_SIZE = 40000;
    private Node<Type> head;
    private Node<Type> tail;
    private Node<Type> curr;
    private int num_items;

    public List() {
        head = null;
        tail = null;
        curr = null;
        num_items = 0;
    }

    public List(List<Type> l) {
        Node<Type> n = l.head;
        this.head = this.tail = this.curr = null;
        this.num_items = 0;
        while (n != null) {
            this.InsertAfter(n.getData());
            n = n.getLink();
        }
    }

    // If the linked list is empty it will return is true if not it will return false
    public boolean IsEmpty() {
        return num_items == 0;
    }

    public void First() {
        if (head != null) {
            curr = head;
        }
    }

    public void Last() {
        if (tail != null) {
            curr = tail;
        }
    }

    public void SetPos(int pos) {
        // Check if the position is valid and list is not empty.
        if (pos < 0 || pos >= this.num_items || head == null) {
            return;
        }
        curr = head;
        // Traverse from the head to the given position
        for (int i = 0; i < pos; i++) {
            curr = curr.getLink();
        }
    }

    // Chat.gpt to help me figure out how to create a new node and set them as temp nodes
    public void Prev() {
        // Cannot move past the head
        if (curr == null || curr == head) {
            return;
        }
        // Create and set the temp node at the head
        Node<Type> temp = head;
        // Traverse from the head until the node just before curr
        while (temp.getLink() != curr) {
            temp = temp.getLink();
        }
        // Set curr to the previous node
        curr = temp;
    }

    public void Next() {
        if (curr != null && curr.getLink() != null) {
            curr = curr.getLink();
        }
    }

    // Chat.gpt helped me again setting temp nodes and traversing the list until you reach the current node
    public int GetPos() {
        // if the list is empty return -1
        if (head == null || curr == null) {
            return -1;
        }
        // Create and set the temp node to the head
        Node<Type> temp = head;
        // initialize the position
        int pos = 0;
        // Traverse the list from the head, counting positions until you are at the curren node and return the position
        while (temp != null) {
            if (temp == curr) {
                return pos;
            }
            temp = temp.getLink();
            pos++;
        }

        return -1;
    }

    public Type GetValue() {
        if (curr == null) {
            return null;
        }
        return curr.getData();
    }

    public int GetSize() {
        return this.num_items;
    }

    // Chat.gpt helped me write and understand how to create a new node while putting in the new data and set the link between the current node and the newly created node
    public void InsertAfter(Type data) {
        // Checks if the list has reached its maximum size so it doesn't go out of bounds when inserting into the linked list if there are to many nodes
        if (num_items >= MAX_SIZE) {
            System.out.println("List is full");
            return;
        }
        // Create a new node
        Node<Type> newNode = new Node<>();
        // insert the data into the new node
        newNode.setData(data);
        // The list is empty so the new node is both the head, tail, and curr
        if (head == null) {
            head = tail = curr = newNode;
        }
        // If there are already nodes
        else {
            // Set the new node after the curr by adjusting the links
            newNode.setLink(curr.getLink());
            curr.setLink(newNode);
            // If the curr is at the end of the list make the new node the tail
            if (curr == tail) {
                tail = newNode;
            }
            // Set the curr to the new node
            curr = newNode;
        }
        // Also increase the size of the list
        num_items++;
    }
    /*
    // Chat.GPT wrote this part because I was stuck, but I now understand that the temp link
    must be set as the head so that the new node can be the setLink to curr thus inserting before.
    I guess it's just the opposite of insert after
     */
    public void InsertBefore(Type data) {
        // If the list is full
        if (num_items >= MAX_SIZE) {
            System.out.println("List is full");
            return;
        }
        // create the new node
        Node<Type> newNode = new Node<>();
        // Insert the data
        newNode.setData(data);
        // If list is empty the new node is the head, curr, and tail
        if (head == null) {
            head = tail = curr = newNode;
        }
        // If curr is at head
        else if (curr == head) {
            // Set the link to the head
            newNode.setLink(head);
            // Set the head as a node and curr as a new node
            head = newNode;
            curr = newNode;
        }
        // If curr is somewhere in the middle
        else {
            // Set the temp node as the head
            Node<Type> temp = head;
            // Go until the link is at the current node
            while (temp.getLink() != curr) {
                temp = temp.getLink();
            }
            // Now temp is the node just before curr
            temp.setLink(newNode);
            // Set the new node link to curr
            newNode.setLink(curr);
            // set new node as curr
            curr = newNode;
        }

        num_items++;
    }
    public void Replace(Type data) {
        // Replace the data in the node
        if (curr != null) {
            curr.setData(data);
        }
    }
}


