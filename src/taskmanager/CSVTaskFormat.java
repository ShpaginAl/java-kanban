package taskmanager;

import task.*;

import java.util.ArrayList;
import java.util.List;

public class CSVTaskFormat {

    public static String toString(Task task) {

        if (task.getType() == TypeTask.SUBTASK) {
            Subtask subtask = (Subtask) task;
            return subtask.getId() + "," + subtask.getType() + "," + subtask.getName() + "," + subtask.getStatus() + "," + subtask.getDescription() + "," + subtask.getEpicId();
        }
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription();
    }

    public static Task taskFromString(String value) {
        final String[] partOfTask = value.split(",");
        final String name = partOfTask[2];
        final TypeTask type = TypeTask.valueOf(partOfTask[1]);
        final String description = partOfTask[4];
        final Status status = Status.valueOf(partOfTask[3]);
        int id = Integer.parseInt(partOfTask[0]);

        if (type == TypeTask.SUBTASK) {
            return new Subtask(name, description, Integer.parseInt(partOfTask[5]), status, id);
        } else if (type == TypeTask.EPIC) {
            return new Epic(name, description, status, id);
        } else {
            return new Task(name, description, status, id);
        }
    }

    public static String toString(HistoryManager historyManager) {
        StringBuilder join = new StringBuilder();
        for (Task task : historyManager.getHistory()) {
            if (join.length() == 0) {
                join.append(task.getId());
            } else {
                join.append(",").append(task.getId());
            }
        }
        return join.toString();
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> historyInListFromFile = new ArrayList<>();
        String[] historyFromFile = value.split(",");
        for (int i = 0; i < historyFromFile.length; i++) {
            historyInListFromFile.add(Integer.parseInt(historyFromFile[i]));
        }
        return historyInListFromFile;
    }

}
