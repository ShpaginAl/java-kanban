package httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import task.Epic;
import taskmanager.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;

    public EpicHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String[] path = uri.getPath().split("/");
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                getEpic(exchange, path);
                break;
            case "POST":
                createEpic(exchange);
                break;
            case "DELETE":
                deleteEpic(exchange, path);
                break;
        }
    }

    public void createEpic(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String requestBodyInString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(requestBodyInString, Epic.class);
        if (epic == null) {
            sendText(exchange, "Невалидная инфа при создании таски", 400);
        }
        taskManager.createEpic(epic);
        sendText(exchange, "Задача сохранена", 201);
    }

    public void getEpic(HttpExchange exchange, String[] path) throws IOException {
        if (path.length == 3) {
            Epic epic = taskManager.getEpic(Integer.parseInt(path[2]));
            if (epic != null) {
                String jsonResponse = gson.toJson(epic);
                System.out.println("Получили задачу с id" + path[2]);
                sendText(exchange, jsonResponse, 200);
            } else {
                sendNotFound(exchange, 404);
            }
        } else if (path.length == 2) {
            try {
                ArrayList<Epic> allEpicsInArray = new ArrayList<>();
                HashMap<Integer, Epic> allEpicsInMap = taskManager.printEpics();
                System.out.println(allEpicsInMap);
                allEpicsInArray.addAll(allEpicsInMap.values());
                String jsonResponse = gson.toJson(allEpicsInArray);
                sendText(exchange, jsonResponse, 200);
            } catch (Exception exp) {
                sendText(exchange, "При выполнении запроса возникла ошибка " + exp.getMessage(), 404);
            }
        } else if (path.length == 4) {
            try {
                if (taskManager.getEpic(Integer.parseInt(path[3])) != null) {
                    List<Integer> subtasksOfEpic = taskManager.getSubtasksOfEpic(Integer.parseInt(path[3]));
                    String jsonResponse = gson.toJson(subtasksOfEpic);
                    sendText(exchange, jsonResponse, 200);
                } else {
                    sendNotFound(exchange, 404);
                }
            } catch (Exception exp) {
                sendText(exchange, "При выполнении запроса возникла ошибка " + exp.getMessage(), 404);
            }

        }

    }

    public void deleteEpic(HttpExchange exchange, String[] path) throws IOException {
        Epic epic = taskManager.getEpic(Integer.parseInt(path[2]));
        if (epic != null) {
            taskManager.deleteEpic(Integer.parseInt(path[2]));
            sendText(exchange, "Задача успешна удалена", 200);
        } else {
            sendNotFound(exchange, 404);
        }
    }
}
