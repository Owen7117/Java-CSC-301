import java.util.*;

public class List<Type> {

    public static final int MAX_SIZE = 1000;
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
        if (pos < 0 || pos >= this.num_items || head == null) {
            return;
        }
        curr = head;
        for (int i = 0; i < pos; i++) {
            curr = curr.getLink();
        }
    }
    // I used chat.gpt to help me figure out how to create a new node and set them as temp nodes
    public void Prev() {
        if (curr == null || curr == head) {
            return;
        }
        Node<Type> temp = head;
        while (temp.getLink() != curr) {
            temp = temp.getLink();
        }
        curr = temp;
    }

    public void Next() {
        if (curr != null && curr.getLink() != null) {
            curr = curr.getLink(); // move forward
        }
    }
    // Chat.gpt helped me again setting temp nodes and traversing the list until you reach the current node
    public int GetPos() {
        if (head == null || curr == null) {
            return -1;
        }
        Node<Type> temp = head;
        int pos = 0;
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
    // Chat.gpt helped me understand how to create a new node while putting in the new data and set the link between the current node and the newly created node
    public void InsertAfter(Type data) {
        if (num_items >= MAX_SIZE) {
            System.out.println("List is full");
            return;
        }
        Node<Type> newNode = new Node<>();
        newNode.setData(data);
        if (head == null) {
            head = tail = curr = newNode;
        } else {
            newNode.setLink(curr.getLink());
            curr.setLink(newNode);
            if (curr == tail) {
                tail = newNode;
            }
            curr = newNode;
        }
        num_items++;
    }
}

/*
    // inserts an item after the current element
// the new element becomes the current
// this should not be possible for a full list
    // removes the current element
// this should not be possible for an empty list
    public void Remove()
    {
    }
    // replaces the value of the current element with the specified value
// this should not be possible for an empty list
    public void Replace(Type data)
    {
    }
    // returns if the list is empty
    public boolean IsEmpty()
    {
        return (head == null);
    }
    // returns if the list is full
    public boolean IsFull()
    {
    }
    // returns if two lists are equal (by value)
    public boolean Equals(List<Type> l)
    {
    }
    // returns the concatenation of two lists
// l should not be modified
// l should be concatenated to the end of *this
// the returned list should not exceed MAX_SIZE elements
// the last element of the new list is the current
    public List<Type> Add(List<Type> l)
    {
    }
    // returns a string representation of the entire list (e.g., 1 2 3 4 5)
// the string "NULL" should be returned for an empty list
*/

