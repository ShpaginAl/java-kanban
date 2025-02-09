package taskmanager;

import task.*;

public class Node {
    Task task;
    Node prev;
    Node next;

    public Node(Node prev, Task task, Node next) {
        this.task = task;
        this.next = next;
        this.prev = prev;
    }


}
