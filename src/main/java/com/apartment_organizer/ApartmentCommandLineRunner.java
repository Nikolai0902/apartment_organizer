package com.apartment_organizer;

import com.apartment_organizer.service.ApartmentProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApartmentCommandLineRunner implements CommandLineRunner {

    private final ApartmentProcessingService apartmentProcessingService;

    public ApartmentCommandLineRunner(ApartmentProcessingService apartmentProcessingService) {
        this.apartmentProcessingService = apartmentProcessingService;
    }

    @Override
    public void run(String... args) {
        String inputFile = "apartments.json";
        String outputFile = "sorted_apartments.json";

        if (args.length >= 2) {
            inputFile = args[0];
            outputFile = args[1];
        }
        apartmentProcessingService.processApartments(inputFile, outputFile);
    }
}
