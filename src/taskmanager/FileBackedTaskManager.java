package taskmanager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;


    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    protected void save() {
        Path pathOfFile = file.toPath();
        if (!Files.exists(pathOfFile)) {
            try {
                System.out.println(pathOfFile);
                Files.createFile(pathOfFile);
            } catch (IOException exc) {
                throw new ManagerSaveException("Не удалось создать файл");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,epic\n");
            for (Task task : tasks.values()) {
                writer.write(CSVTaskFormat.toString(task) + "\n");
            }
            for (Epic epic : epics.values()) {
                writer.write(CSVTaskFormat.toString(epic) + "\n");
            }
            for (Subtask subtask : subtasks.values()) {
                writer.write(CSVTaskFormat.toString(subtask) + "\n");
            }
            if (historyManager.getHistory() != null) {
                writer.write(CSVTaskFormat.toString(historyManager));
            } else {
                writer.write("No value");
            }
        } catch (IOException exc) {
            throw new ManagerSaveException("Ошибка сохранения в файл");
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        final FileBackedTaskManager taskManagerFromFile = new FileBackedTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String currentLine = br.readLine();
            String nextLine = br.readLine();
            while (nextLine != null) {
                Task task = CSVTaskFormat.taskFromString(currentLine);
                switch (task.getType()) {
                    case TASK:
                        taskManagerFromFile.tasks.put(task.getId(), task);
                        taskManagerFromFile.id++;
                        break;
                    case EPIC:
                        taskManagerFromFile.epics.put(task.getId(), (Epic) task);
                        taskManagerFromFile.id++;
                        break;
                    case SUBTASK:
                        taskManagerFromFile.subtasks.put(task.getId(), (Subtask) task);
                        taskManagerFromFile.id++;
                        break;
                }
                currentLine = nextLine;
                nextLine = br.readLine();
            }
            if (!currentLine.equals("No value")) {
                List<Integer> listOfHistory = CSVTaskFormat.historyFromString(currentLine);
                for (int taskInHistory : listOfHistory) {
                    if (taskManagerFromFile.tasks.containsKey(taskInHistory)) {
                        Task task = taskManagerFromFile.tasks.get(taskInHistory);
                        taskManagerFromFile.historyManager.addValueInHistory(task);
                    } else if (taskManagerFromFile.epics.containsKey(taskInHistory)) {
                        Epic epic = taskManagerFromFile.epics.get(taskInHistory);
                        taskManagerFromFile.historyManager.addValueInHistory(epic);
                    } else if (taskManagerFromFile.subtasks.containsKey(taskInHistory)) {
                        Subtask subtask = taskManagerFromFile.subtasks.get(taskInHistory);
                        taskManagerFromFile.historyManager.addValueInHistory(subtask);
                    }
                }
            }
        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка считывания файла");
        }
        return taskManagerFromFile;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public Task getTask(int id) {
        if (tasks.containsKey(id)) {
            historyManager.addValueInHistory(tasks.get(id));
            save();
            return tasks.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.containsKey(id)) {
            historyManager.addValueInHistory(epics.get(id));
            save();
            return epics.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Subtask getSubtask(int id) {
        if (subtasks.containsKey(id)) {
            historyManager.addValueInHistory(subtasks.get(id));
            save();
            return subtasks.get(id);
        } else {
            return null;
        }
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }
}
