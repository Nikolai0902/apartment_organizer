package com.apartment_organizer.repository;

import com.apartment_organizer.exception.FileOperationException;
import com.apartment_organizer.model.Apartment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Чтение и запись в файл.
 */
@Repository
public class ApartmentRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Apartment> readFromFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new FileOperationException("Файл не найден: " + filename);
            }
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (Exception e) {
            throw new FileOperationException("Ошибка чтения файла: " + filename, e);
        }
    }

    public void writeToFile(Map<String, List<Apartment>> buildingsData, String filename) {
        try {
            File file = new File(filename);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                throw new FileOperationException("Не удалось создать директорию для файла: " + filename);
            }

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, buildingsData);

        } catch (Exception e) {
            throw new FileOperationException("Ошибка записи в файл: " + filename, e);
        }
    }
}
