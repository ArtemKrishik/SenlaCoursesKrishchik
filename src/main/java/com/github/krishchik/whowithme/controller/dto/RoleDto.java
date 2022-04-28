package com.github.krishchik.whowithme.controller.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDto {

    @NotNull
    private Long id;
    @NotNull
    private String name;

}
