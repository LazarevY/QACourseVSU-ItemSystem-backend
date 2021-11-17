package app.core.data.jpa.persistance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@SequenceGenerator(name = "base_seq_gen", sequenceName = "base_seq", initialValue = 100, allocationSize = 10)
public abstract class Entity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_seq_gen")
    protected Long id;

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public Long uniqueHashSeed() {
        return 1001L;
    }
}
