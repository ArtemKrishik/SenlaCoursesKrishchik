package com.github.krishchik.whowithme.controller.dto;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractDto{

    @NonNull
    private String login;
    private String password;
    private Long profileId;
    private Long roleId;

    @Builder
    public UserDto(Long id, String login, String password) {
        super(id);
        this.login = login;
        this.password = password;

    }

}
