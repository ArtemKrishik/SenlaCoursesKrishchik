package com.github.krishchik.whowithme.controller.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto extends AbstractDto{


    @NotNull(message = "название не может быть пустым")
    private String placeName;
    @NotNull(message = "вместимость не может быть пустой")
    @Min(value = 1, message = "вместимость не может быть меньше нуля")
    private Integer capacity;
    @NotNull
    @Min(value = 0)
    private Integer price;

    @Builder
    public PlaceDto(Long id, String placeName, Integer price) {
        super(id);
        this.placeName = placeName;
        this.price = price;
    }

}
