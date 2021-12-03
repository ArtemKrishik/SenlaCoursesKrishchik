package com.github.krishchik.whowithme.controller.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private Long id;
    private String login;
    private String password;

}
