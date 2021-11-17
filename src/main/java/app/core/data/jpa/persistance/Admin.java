package app.core.data.jpa.persistance;

import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends app.core.data.jpa.persistance.Entity {

    private String name;
    private String surname;
    private String patronymic;
    private String login;
    private String passwordHash;

    public List<Role> getRoles() {
        return List.of(Role.ADMIN);
    }
}
