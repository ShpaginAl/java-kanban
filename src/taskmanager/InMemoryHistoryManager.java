package taskmanager;

import task.*;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node head;
    private Node tail;

    private void linkLast(Task task) {
        final Node oldTail = tail;
        final Node newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> listOfTasksInHistory = new ArrayList<>();
        Node node = head;
        if (node != null) {
            listOfTasksInHistory.add(node.task);
            while (node.next != null) {
                node = node.next;
                listOfTasksInHistory.add(node.task);
            }
            return listOfTasksInHistory;
        } else {
            return null;
        }
    }

    private void add(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            removeNode(task.getId());
        }
        linkLast(task);
        nodeMap.put(task.getId(), tail);
    }

    private void removeNode(int id) {
        final Node node = nodeMap.remove(id);
        if (node.prev == null) {
            head = node.next;
            if (head != null) {
                node.next.prev = null;
            }
        } else if (node.next == null) {
            tail = node.prev;
            if (tail != null) {
                node.prev.next = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }


    @Override
    public ArrayList<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void addValueInHistory(Task task) {
        add(task);
    }


    @Override
    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            removeNode(id);
        }
    }

    //не забыть добавить удаление подзадач при удалении эпика
}
