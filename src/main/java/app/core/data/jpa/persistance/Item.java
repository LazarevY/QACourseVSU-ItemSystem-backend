package app.core.data.jpa.persistance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "item_seq_gen", sequenceName = "item_seq", initialValue = 1, allocationSize = 10)
public class Item extends BaseEntity {

    @Id
    @Column(unique = true)
    @SequenceGenerator(name = "item_seq_gen", sequenceName = "item_seq", initialValue = 100, allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_gen")
    private Long code;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory itemCategory;

    @ManyToOne
    @JoinColumn(name = "visualisation_data_id")
    private VisualizationData visualizationData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<ItemOwnership> ownerships = new ArrayList<>();

    private String name;

    private String description;

    public Item(ItemCategory itemCategory, VisualizationData visualizationData, String name, String description) {
        this.itemCategory = itemCategory;
        this.visualizationData = visualizationData;
        this.name = name;
        this.description = description;
    }

    @Override
    public Long getID() {
        return code;
    }

    @Override
    public Long uniqueHashSeed() {
        return 99L;
    }
}
