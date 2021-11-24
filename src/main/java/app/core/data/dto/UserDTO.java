package app.core.data.dto;

import app.core.data.validation.groups.Create;
import app.core.data.validation.groups.Update;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class UserDTO {
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String patronymic;
    @NotNull
    @Pattern(regexp = "((\\+7)|(8))\\d{10}")
    private String phone;
}
