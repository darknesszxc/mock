package com.example.mock.worker;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Random;

public class FileWorker {

    private static final String WRITE_FILE_PATH = "user-log.txt";
    private static final String READ_FILE_PATH = "predefined-users.txt";

    // Метод для записи JSON строки в файл (добавление в конец)
    public void writeToFile(String jsonLine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(WRITE_FILE_PATH, true))) {
            writer.write(jsonLine);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи в файл: " + e.getMessage(), e);
        }
    }

    // Метод для чтения случайной строки из файла
    public String readRandomLine() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(READ_FILE_PATH));
            if (lines.isEmpty()) return "Файл пустой.";
            int randomIndex = new Random().nextInt(lines.size());
            return lines.get(randomIndex);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + e.getMessage(), e);
        }
    }


}
