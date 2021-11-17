package app.core.data.jpa.persistance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    public abstract Long getID();

    public abstract Long uniqueHashSeed();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;

        return Objects.equals(getID(), that.getID());
    }

    @Override
    public int hashCode() {
        return 1001 * Optional.ofNullable(getID()).map(Long::intValue).orElse(0);
    }
}
