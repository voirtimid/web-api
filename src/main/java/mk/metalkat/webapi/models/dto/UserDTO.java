package mk.metalkat.webapi.models.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String email;

    private String password;

    private String role;
}
