package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import httpserver.DurationTypeAdapter;
import httpserver.HttpTaskServer;
import httpserver.LocalDateTimeAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import taskmanager.InMemoryTaskManager;
import taskmanager.TaskManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrioritizedHandlerTest {
    private TaskManager manager;
    private HttpTaskServer server;
    private Task task;
    private Epic epic;
    private Subtask subtask;
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .create();

    @BeforeEach
    public void setUP() throws IOException {
        manager = new InMemoryTaskManager();
        server = new HttpTaskServer(manager);
        server.start();
        task = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW, Duration.ofMinutes(90), LocalDateTime.of(2024, 6, 30, 20, 30));
        epic = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        manager.createEpic(epic);
        subtask = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля", epic.getId(), Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2024, 7, 30, 20, 50));
        manager.createTask(task);
        manager.createSubtask(subtask);
    }

    @AfterEach
    public void close() throws IOException {
        server.stop();
    }

    @Test
    public void prioritizedGET() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        List<Task> receivedTasks = gson.fromJson(responseBody, new TypeToken<List<Task>>() {
        }.getType());

        assertEquals(200, response.statusCode(), "PrioritizedHandler вернул не тот ответ");
        assertEquals(2, receivedTasks.size(), "PrioritizedHandler не вернул List<Tasks>");
    }
}