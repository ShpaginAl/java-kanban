package taskmanager;

import task.Task;

import java.util.ArrayList;

public interface HistoryManager {

    ArrayList<Task> getHistory();

    void addValueInHistory(Task task);

    void remove(int id);
}
