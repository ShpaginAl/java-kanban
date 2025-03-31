package httpserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class BaseHttpHandler {

    protected void sendText(HttpExchange h, String text, int code) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(code, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendNotFound(HttpExchange h, int code) throws IOException {
        h.sendResponseHeaders(code, 0);
        try (OutputStream os = h.getResponseBody()) {
            os.write("Такой объект не найден".getBytes(StandardCharsets.UTF_8));
        }
    }

    protected void sendHasInteractions(HttpExchange h, int code) throws IOException {
        h.sendResponseHeaders(code, 0);
        try (OutputStream os = h.getResponseBody()) {
            os.write("Задача пересекается с уже существующими задачами. Невозможно сохранить такую задачу".getBytes(Charset.defaultCharset()));
        }
    }

    protected Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .create();
}
