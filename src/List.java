import java.util.*;

public class List<Type>{
    // We don't actually have to set a max size with linked lists
// But it is a good idea.
// Just picture an infinite loop adding to the list! :O
    public static final int MAX_SIZE = 50;
    private Node<Type> head;
    private Node<Type> tail;
    private Node<Type> curr;
    private int num_items;
    // constructor
// remember that an empty list has a "size" of -1 and its "position" is at -1
    public List()
    {
    }
    // copy constructor
// clones the list l and sets the last element as the current
    public List(List<Type> l)
    {
        Node<Type> n = l.head;
        this.head = this.tail = this.curr = null;
        this.num_items = 0;
        while (n != null)
        {
            this.InsertAfter(n.getData());
            n = n.getLink();
        }
    }
    // navigates to the beginning of the list
    public void First() {
        {
            if (head != null) {
                curr = head; // just move current to the head
            }
        }
    }
    // navigates to the end of the list
// the end of the list is at the last valid item in the list
    public void Last() {
        if (tail != null) {
            curr = tail; // point to the last node
        }
    }
    // navigates to the specified element (0-index)
// this should not be possible for an empty list
// this should not be possible for invalid positions
    public void SetPos(int pos)
    {
        if (pos < 0 || pos >= this.num_items || this.head == null) {
            return;
        }
        this.curr = this.head;
        for(int i = 0; i < pos; i++) {
            this.curr = this.curr.getLink();
        }
    }
    // navigates to the previous element
// this should not be possible for an empty list
// there should be no wrap-around
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
    // navigates to the next element
// this should not be possible for an empty list
// there should be no wrap-around
    public void Next() {
        if (curr != null && curr.getLink() != null) {
            curr = curr.getLink(); // move forward
        }
    }
    // returns the location of the current element (or -1)
    public int GetPos()
    {
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

        return -1; // shouldn't happen if curr is valid
    }

    // returns the value of the current element (or -1)
    public Type GetValue() {
        if (curr == null) {
            return null;
        }
        return curr.getData();
    }
    // returns the size of the list
// size does not imply capacity
    public int GetSize() {
        return num_items;
    }
    // inserts an item before the current element
// the new element becomes the current
// this should not be possible for a full list
    public void InsertBefore(Type data)
    {
    }
    // inserts an item after the current element
// the new element becomes the current
// this should not be possible for a full list
    public void InsertAfter(Type data) {

    }

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
        return (this.head == null);
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
    public String toString() {
    }
    // replaces the value of the current element with the specified value
// this should not be possible for an empty list
    public void Replace(Type data)
    {
    }
    // returns if the list is empty
    public boolean IsEmpty()
    {
    }
    // returns if the list is full
    public boolean IsFull()
    {
    }
    // returns if two lists are equal (by value)
    public boolean Equals(List<Type> l)
    {
    }
// returns the concatenation of two lists{
}
