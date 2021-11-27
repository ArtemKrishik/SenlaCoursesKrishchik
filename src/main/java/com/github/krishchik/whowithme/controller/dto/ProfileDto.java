package com.github.krishchik.whowithme.controller.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileDto {

    private Long id;
    private String name;
    private Integer age;
    private Long phoneNumber;

}
