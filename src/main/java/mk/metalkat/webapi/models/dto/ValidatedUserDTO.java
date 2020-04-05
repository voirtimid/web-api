package mk.metalkat.webapi.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ValidatedUserDTO {

    private Long userId;

    private String email;

    private String role;

    private UUID uuid;
}
