package taskmanager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        return new FileBackedTaskManager(new File("C:\\Users\\Aleksandr\\IdeaProjects\\java-kanban\\task.csv"));
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
