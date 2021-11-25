package com.github.krishchik.whowithme.controller.dto;

import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    private String login;
    private String password;

}
