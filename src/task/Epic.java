package task;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasks;
    public Epic (String name, String description, Status status) {
        super(name, description, status);
        subtasks = new ArrayList<>();
    }
    public Epic (String name, String description, Status status, int id) {
        super(name, description, status, id);
        subtasks = new ArrayList<>();
    }

    public void setSubtasks(int idSubtask) {
        subtasks.add(idSubtask);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + getId() +
                '}';
    }
}
