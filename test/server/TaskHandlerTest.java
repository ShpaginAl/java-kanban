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
import task.Status;
import task.Task;
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

public class TaskHandlerTest {
    private InMemoryTaskManager taskManager;
    private HttpTaskServer taskServer;
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .create();
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    public void beforeEach() throws IOException {
        taskManager = new InMemoryTaskManager();
        taskServer = new HttpTaskServer(taskManager);
        taskServer.start();
        task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW, Duration.ofMinutes(90), LocalDateTime.of(2024, 6, 30, 20, 30));
        task2 = new Task("Утренная пробежка", "В 10:00 необходимо сделать утреннюю пробежку", Status.NEW, Duration.ofMinutes(90), LocalDateTime.of(2024, 7, 30, 20, 30));
        task3 = new Task("Утренная зарядка", "В 14:00 необходимо сделать утреннюю пробежку", Status.NEW, Duration.ofMinutes(90), LocalDateTime.of(2024, 6, 30, 20, 30));
    }

    @AfterEach
    public void afterEach() {
        taskServer.stop();
    }

    @Test
    public void postMethod() throws IOException, InterruptedException {
        URI uri = URI.create("http://localhost:8080/tasks");
        String jsonRequest = gson.toJson(task1);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder.header("Content-Type", "application/json").uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonRequest)).version(HttpClient.Version.HTTP_1_1).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
//      assertEquals("Задача сохранена", response.body());
        assertEquals(1, taskManager.printTasks().size());
    }

    @Test
    public void createTaskWithIntersaction() throws IOException, InterruptedException {
        taskManager.createTask(task1);
        URI uri = URI.create("http://localhost:8080/tasks");
        String jsonRequest = gson.toJson(task3);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder.header("Content-Type", "application/json").uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonRequest)).version(HttpClient.Version.HTTP_1_1).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(406, response.statusCode());
        assertEquals("Задача пересекается с уже существующими задачами. Невозможно сохранить такую задачу", response.body());
//      assertEquals(1, taskManager.printTasks().size());
    }

    @Test
    public void testTasksForIdGET() throws IOException, InterruptedException {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/" + task1.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body(); // получаем JSON-строку из ответа
        Task receivedTask = gson.fromJson(responseBody, Task.class);// десериализуем JSON-строку в объект Task
        assertEquals(200, response.statusCode(), "TaskHandler вернул не тот ответ при запросе Таски");
        assertEquals(task1, receivedTask, "TaskHandler вернул не ту Таску");
    }

    @Test
    public void testTasksGET() throws IOException, InterruptedException {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body(); // получаем JSON-строку из ответа
        List<Task> receivedTasks = gson.fromJson(responseBody, new TypeToken<List<Task>>() {
        }.getType());// десериализуем JSON-строку в объект Task

        assertEquals(200, response.statusCode(), "TaskHandler вернул не тот ответ при запросе Таски");
        assertEquals(task1, receivedTasks.getFirst(), "TaskHandler вернул не ту Таску");
        assertEquals(task2, receivedTasks.getLast());
    }

    @Test
    public void deleteTask() throws IOException, InterruptedException {
        taskManager.createTask(task1);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/" + task1.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "TaskHandler вернул не тот ответ при запросе Таски");
        assertNull(taskManager.getTask(task1.getId()));
    }

}
