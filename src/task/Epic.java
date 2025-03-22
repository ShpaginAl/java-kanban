package task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksOfEpic;
    LocalDateTime endTime;


    public Epic(String name, String description, Status status) {
        super(name, description, status);
        subtasksOfEpic = new ArrayList<>();
    }

    public Epic(String name, String description, Status status, int id) {
        super(name, description, status, id);
        subtasksOfEpic = new ArrayList<>();
    }

    public void setSubtasksOfEpic(int idSubtask) {
        subtasksOfEpic.add(idSubtask);
    }

    public ArrayList<Integer> getSubtasksOfEpic() {
        return subtasksOfEpic;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public TypeTask getType() {
        return TypeTask.EPIC;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasksOfEpic=" + subtasksOfEpic +
                ", endTime=" + endTime +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}
