package httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import task.Task;
import taskmanager.TaskManager;

import java.io.IOException;
import java.util.ArrayList;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;

    public PrioritizedHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void handle(HttpExchange exchange) throws IOException {
        ArrayList<Task> historyOfPrioritizedTask = new ArrayList<>(taskManager.getPrioritizedTasks());
        String jsonResponse = gson.toJson(historyOfPrioritizedTask);
        sendText(exchange, jsonResponse, 200);
    }
}

