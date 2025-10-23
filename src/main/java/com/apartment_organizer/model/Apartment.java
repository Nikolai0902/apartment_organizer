package com.apartment_organizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Apartment {
    private String address;
    private String apartment;
    private String price;

    @JsonProperty("living_area")
    private String livingArea;

    private String rooms;
    private String floor;
    private String entrance;
}
