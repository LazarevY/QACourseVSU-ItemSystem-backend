package app.core.data.dto;

import app.core.data.jpa.persistance.Role;
import app.core.data.validation.groups.Create;
import app.core.data.validation.groups.Login;
import app.core.data.validation.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotBlank(groups = {Create.class, Update.class})
    @Null(groups = Login.class)
    private String name;

    @NotBlank(groups = {Create.class, Update.class})
    @Null(groups = Login.class)
    private String surname;

    @NotBlank(groups = {Create.class, Update.class})
    @Null(groups = Login.class)
    private String patronymic;

    @NotBlank
    @Size(min = 5)
    private String login;

    @NotBlank
    @Size(min = 8)
    private String password;

    public List<Role> getRoles() {
        return List.of(Role.ADMIN);
    }
}
