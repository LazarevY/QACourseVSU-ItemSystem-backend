package app.core.data.jpa.persistance;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;

@Data
@AllArgsConstructor
public class Role {

    public static final Role ADMIN = new Role(0, "ADMIN");
    @Id
    private long id;
    private String value;

}