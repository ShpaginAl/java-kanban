package task;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    protected final int epicId;

    public Subtask(String name, String description, int epicId, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, Status status, int id, Duration duration, LocalDateTime startTime) {
        super(name, description, status, id, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TypeTask getType() {
        return TypeTask.SUBTASK;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
