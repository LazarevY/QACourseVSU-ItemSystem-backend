package app.core.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminLoginDTO {
    private String name;
    private String surname;
    private String patronymic;
    private String login;
    private String passwordHash;
}
