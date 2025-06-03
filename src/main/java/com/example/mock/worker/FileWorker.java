package com.example.mock.worker;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.Random;

@Component
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
        int totalLines = 10;
        int randomLineNumber = new Random().nextInt(totalLines); // от 0 до 9

        try (BufferedReader reader = new BufferedReader(new FileReader(READ_FILE_PATH))) {
            for (int i = 0; i < randomLineNumber; i++) {
                reader.readLine(); // пропускаем строки
            }
            String line = reader.readLine(); // читаем нужную строку
            return line != null ? line : "Строка не найдена.";
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + e.getMessage(), e);
        }
    }



}
