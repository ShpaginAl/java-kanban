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
import taskmanager.InMemoryTaskManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SubtaskHandlerTest {
    private InMemoryTaskManager taskManager;
    private HttpTaskServer taskServer;
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .create();
    Subtask subtask1;
    Subtask subtask2;
    Subtask subtask3;
    Epic epic1;

    @BeforeEach
    public void beforeEach() throws IOException {
        taskManager = new InMemoryTaskManager();
        taskServer = new HttpTaskServer(taskManager);
        taskServer.start();
        epic1 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        taskManager.createEpic(epic1);
        subtask1 = new Subtask("Выбор марки автомобиля0", "Необходимо выбрать марку автомобиля", epic1.getId(), Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2024, 7, 30, 20, 50));
        subtask2 = new Subtask("Выбор марки автомобиля1", "Необходимо выбрать марку автомобиля", epic1.getId(), Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2025, 7, 30, 20, 50));
        subtask3 = new Subtask("Выбор марки автомобиля2", "Необходимо выбрать марку автомобиля", epic1.getId(), Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2024, 7, 30, 20, 50));
    }

    @AfterEach
    public void afterEach() {
        taskServer.stop();
    }

    @Test
    public void postMethod() throws IOException, InterruptedException {
        URI uri = URI.create("http://localhost:8080/subtasks");
        String jsonRequest = gson.toJson(subtask1);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder.header("Content-Type", "application/json").uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonRequest)).version(HttpClient.Version.HTTP_1_1).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
//      assertEquals("Задача сохранена", response.body());
        assertEquals(1, taskManager.printSubtasks().size());
    }

    @Test
    public void createTaskWithIntersaction() throws IOException, InterruptedException {
        taskManager.createSubtask(subtask1);
        URI uri = URI.create("http://localhost:8080/subtasks");
        String jsonRequest = gson.toJson(subtask3);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder.header("Content-Type", "application/json").uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonRequest)).version(HttpClient.Version.HTTP_1_1).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(406, response.statusCode());
        assertEquals("Задача пересекается с уже существующими задачами. Невозможно сохранить такую задачу", response.body());
    }

    @Test
    public void testTasksForIdGET() throws IOException, InterruptedException {
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/" + subtask1.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body(); // получаем JSON-строку из ответа
        Subtask receivedTask = gson.fromJson(responseBody, Subtask.class);// десериализуем JSON-строку в объект Task
        assertEquals(200, response.statusCode(), "TaskHandler вернул не тот ответ при запросе Таски");
        assertEquals(subtask1, receivedTask, "TaskHandler вернул не ту Таску");
    }

    @Test
    public void testTasksGET() throws IOException, InterruptedException {
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body(); // получаем JSON-строку из ответа
        List<Subtask> receivedTasks = gson.fromJson(responseBody, new TypeToken<List<Subtask>>() {
        }.getType());// десериализуем JSON-строку в объект Task

        assertEquals(200, response.statusCode(), "TaskHandler вернул не тот ответ при запросе Таски");
        assertEquals(subtask1, receivedTasks.getFirst(), "TaskHandler вернул не ту Таску");
        assertEquals(subtask2, receivedTasks.getLast());
    }

    @Test
    public void deleteTask() throws IOException, InterruptedException {
        taskManager.createSubtask(subtask1);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/" + subtask1.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "TaskHandler вернул не тот ответ при запросе Таски");
        assertNull(taskManager.getSubtask(subtask1.getId()));
    }

}
