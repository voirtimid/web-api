package mk.metalkat.webapi.models.dto;

import lombok.Data;
import mk.metalkat.webapi.models.User;

@Data
public class UserDTO {

    private User user;

    private Long roleId;
}
