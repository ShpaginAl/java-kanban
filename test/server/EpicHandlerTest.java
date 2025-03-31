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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EpicHandlerTest {
    private InMemoryTaskManager taskManager;
    private HttpTaskServer taskServer;
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .create();
    Epic epic1;
    Epic epic2;
    Subtask subtask1;

    @BeforeEach
    public void beforeEach() throws IOException {
        taskManager = new InMemoryTaskManager();
        taskServer = new HttpTaskServer(taskManager);
        taskServer.start();
        epic1 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        epic2 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        subtask1 = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля", epic1.getId(), Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2024, 7, 30, 20, 50));
        taskManager.createSubtask(subtask1);
    }

    @AfterEach
    public void afterEach() {
        taskServer.stop();
    }

    @Test
    public void postMethod() throws IOException, InterruptedException {
        taskManager.createSubtask(subtask1);
        URI uri = URI.create("http://localhost:8080/epics");
        String jsonRequest = gson.toJson(epic1);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder.header("Content-Type", "application/json").uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonRequest)).version(HttpClient.Version.HTTP_1_1).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        //      assertEquals(1, taskManager.printEpics().size());
    }

    @Test
    public void testTasksForIdGET() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/" + epic1.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body(); // получаем JSON-строку из ответа
        Epic receivedTask = gson.fromJson(responseBody, Epic.class);// десериализуем JSON-строку в объект Task
        assertEquals(200, response.statusCode(), "TaskHandler вернул не тот ответ при запросе Таски");
        assertEquals(epic1, receivedTask, "TaskHandler вернул не ту Таску");
    }

    @Test
    public void deleteTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/" + epic1.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "TaskHandler вернул не тот ответ при запросе Таски");
        assertNull(taskManager.getEpic(epic1.getId()));
    }

}