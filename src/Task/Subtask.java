package Task;

public class Subtask extends Task {
    private final int epicId;
    public Subtask (String name, String description, int epicId, Status status) {
        super(name, description, status);
        this.epicId = epicId;
    }
    public Subtask (String name, String description, int epicId, Status status, int id) {
        super(name, description, status, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + getId() +
                '}';
    }
}
