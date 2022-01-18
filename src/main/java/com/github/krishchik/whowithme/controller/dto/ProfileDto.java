package com.github.krishchik.whowithme.controller.dto;

import lombok.*;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileDto extends AbstractDto{

    private String name;
    private Integer age;
    private Long phoneNumber;

}
