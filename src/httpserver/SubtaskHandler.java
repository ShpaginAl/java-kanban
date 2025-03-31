package httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import task.Subtask;
import task.Task;
import taskmanager.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;

    public SubtaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String[] path = uri.getPath().split("/");
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                getSubtask(exchange, path);
                break;
            case "POST":
                createSubtask(exchange);
                break;
            case "DELETE":
                deleteSubtask(exchange, path);
                break;
        }
    }

    public void createSubtask(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String requestBodyInString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Subtask subtask = gson.fromJson(requestBodyInString, Subtask.class);
        Integer idSubtask = subtask.getId();
        if (idSubtask == null) {
            int id = taskManager.createSubtask(subtask);
            if (id == 0) {
                sendHasInteractions(exchange, 406);
            } else {
                sendText(exchange, "Задача сохранена", 201);
            }
        } else {
            taskManager.updateSubtask(subtask);
            sendText(exchange, "Задача обновлена", 201);
        }
    }

    public void getSubtask(HttpExchange exchange, String[] path) throws IOException {
        if (path.length == 3) {
            Subtask subtask = taskManager.getSubtask(Integer.parseInt(path[2]));
            if (subtask != null) {
                String jsonResponse = gson.toJson(subtask);
                System.out.println("Получили задачу с id" + path[2]);
                sendText(exchange, jsonResponse, 200);
            } else {
                sendNotFound(exchange, 404);
            }
        } else if (path.length == 2) {
            ArrayList<Task> allSubtasksInArray = new ArrayList<>();
            HashMap<Integer, Subtask> allSubtasksInMap = taskManager.printSubtasks();
            allSubtasksInArray.addAll(allSubtasksInMap.values());
            String jsonResponse = gson.toJson(allSubtasksInArray);
            System.out.println("Получили все задачи");
            sendText(exchange, jsonResponse, 200);
        }
    }

    public void deleteSubtask(HttpExchange exchange, String[] path) throws IOException {
        Subtask subtask = taskManager.getSubtask(Integer.parseInt(path[2]));
        if (subtask != null) {
            taskManager.deleteSubtask(Integer.parseInt(path[2]));
            sendText(exchange, "Задача успешна удалена", 200);
        } else {
            sendNotFound(exchange, 404);
        }
    }
}
