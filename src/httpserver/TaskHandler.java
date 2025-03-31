package httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import task.Task;
import taskmanager.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String[] path = uri.getPath().split("/");
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                getTask(exchange, path);
                break;
            case "POST":
                createTask(exchange);
                break;
            case "DELETE":
                deleteTask(exchange, path);
                break;
        }
    }

    public void createTask(HttpExchange exchange) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            String requestBodyInString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            Task task = gson.fromJson(requestBodyInString, Task.class);
            if (task == null) {
                sendText(exchange, "Невалидная инфа при создании таски", 400);
            }
            if (task.getId() == null) {
                int id = taskManager.createTask(task);
                if (id == 0) {
                    sendHasInteractions(exchange, 406);
                } else {
                    sendText(exchange, "Задача сохранена", 201);
                }
            } else {
                taskManager.updateTask(task);
                sendText(exchange, "Задача обновлена", 201);
            }
        } catch (Exception exc) {
            sendText(exchange, exc.getMessage(), 500);
        }
    }

    public void getTask(HttpExchange exchange, String[] path) throws IOException {
        if (path.length == 3) {
            Task task = taskManager.getTask(Integer.parseInt(path[2]));
            if (task != null) {
                String jsonResponse = gson.toJson(task);
                System.out.println("Получили задачу с id" + path[2]);
                sendText(exchange, jsonResponse, 200);
            } else {
                sendNotFound(exchange, 404);
            }
        } else if (path.length == 2) {
            ArrayList<Task> allTasksInArray = new ArrayList<>();
            HashMap<Integer, Task> allTasksInMap = taskManager.printTasks();
            allTasksInArray.addAll(allTasksInMap.values());
            String jsonResponse = gson.toJson(allTasksInArray);
            System.out.println("Получили все задачи");
            sendText(exchange, jsonResponse, 200);
        }
    }

    public void deleteTask(HttpExchange exchange, String[] path) throws IOException {
        Task task = taskManager.getTask(Integer.parseInt(path[2]));
        if (task != null) {
            taskManager.deleteTask(Integer.parseInt(path[2]));
            sendText(exchange, "Задача успешна удалена", 200);
        } else {
            sendNotFound(exchange, 404);
        }
    }
}
