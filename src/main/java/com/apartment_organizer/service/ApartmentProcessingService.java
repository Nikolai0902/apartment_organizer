package com.apartment_organizer.service;

import com.apartment_organizer.model.Apartment;
import com.apartment_organizer.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Сервис обработки списка квартир
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentProcessingService {

    private final ApartmentRepository apartmentRepository;

    public void processApartments(String inputPath, String outputPath) {
        List<Apartment> apartments = apartmentRepository.readFromFile(inputPath);
        log.info("Прочитано {} квартир", apartments.size());

        Map<String, List<Apartment>> buildingsData = sortAndGroupApartments(apartments);
        log.info("Сгруппировано по {} домам", buildingsData.size());

        apartmentRepository.writeToFile(buildingsData, outputPath);
        log.info("Обработка завершена. Создано {} зданий", buildingsData.size());
    }


    /**
     * Сортируем квартиры в каждом доме по номеру
     * @param apartments
     * @return
     */
    private Map<String, List<Apartment>> sortAndGroupApartments(List<Apartment> apartments) {
        Map<String, List<Apartment>> result = new TreeMap<>();

        for (Apartment apt : apartments) {
            if (apt.getAddress() == null || apt.getApartment() == null) {
                log.warn("Пропущена квартира с отсутствующими данными: address={}, apartment={}",
                        apt.getAddress(), apt.getApartment());
                continue;
            }

            result.computeIfAbsent(apt.getAddress(), k -> new ArrayList<>())
                    .add(apt);
        }

        for (List<Apartment> aptList : result.values()) {
            aptList.sort(Comparator.comparing(apt -> {
                try {
                    return Integer.parseInt(apt.getApartment());
                } catch (NumberFormatException e) {
                    log.warn("Неверный формат номера квартиры: {}", apt.getApartment());
                    return Integer.MAX_VALUE;
                }
            }));
        }

        return result;
    }
}
