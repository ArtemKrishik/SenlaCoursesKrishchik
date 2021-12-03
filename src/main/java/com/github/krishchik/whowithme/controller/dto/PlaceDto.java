package com.github.krishchik.whowithme.controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlaceDto {

    private Long id;
    private String placeName;
    private Integer capacity;
    private Integer price;


}
