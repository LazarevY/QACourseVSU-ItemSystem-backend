package app.core.data.jpa.persistance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@javax.persistence.Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "item_cat_seq_gen", sequenceName = "item_cat_seq", initialValue = 1, allocationSize = 10)
public class ItemCategory extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_cat_seq_gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private ItemCategory parentItemCategory;

    @OneToMany(mappedBy = "parentItemCategory")
    @ToString.Exclude
    private List<ItemCategory> daughterCategories = new ArrayList<>();

    @OneToMany(mappedBy = "itemCategory")
    @ToString.Exclude
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "visualisation_data_id")
    private VisualizationData vdata;

    private String name;

    private String description;

    public ItemCategory(ItemCategory parentItemCategory, VisualizationData vdata, String name, String description) {
        this.parentItemCategory = parentItemCategory;
        this.vdata = vdata;
        this.name = name;
        this.description = description;
        if (parentItemCategory != null)
            parentItemCategory.getDaughterCategories().add(this);
    }

    public ItemCategory(VisualizationData vdata, String name, String description) {
        this.vdata = vdata;
        this.name = name;
        this.description = description;
    }

    private ItemCategory(Long id, ItemCategory parentItemCategory, VisualizationData vdata, String name, String description) {
        this(parentItemCategory, vdata, name, description);
        this.id = id;
    }

    public Long getCategoryCode(){
        return id;
    }

    @PreRemove
    private void preRemove() {
        daughterCategories.forEach(itemCategory -> itemCategory.setParentItemCategory(null));
        items.forEach(itemCategory -> itemCategory.setItemCategory(null));
        Optional.ofNullable(parentItemCategory).ifPresent(itemCategory ->
                itemCategory.daughterCategories.remove(this));
    }

    public void setParentItemCategory(ItemCategory parentItemCategory) {
        this.parentItemCategory = parentItemCategory;
        if (parentItemCategory != null)
            parentItemCategory.getDaughterCategories().add(this);
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public Long uniqueHashSeed() {
        return 7821L;
    }
}
