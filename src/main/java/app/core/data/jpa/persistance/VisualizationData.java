package app.core.data.jpa.persistance;

import app.core.data.jpa.util.HashMapConverter;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@javax.persistence.Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VisualizationData extends Entity {

    @OneToMany(mappedBy = "visualizationData")
    @ToString.Exclude
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "vdata")
    @ToString.Exclude
    private List<ItemCategory> categories = new ArrayList<>();

    @Convert(converter = HashMapConverter.class)
    @Column(length = 512)
    private Map<String, Object> visualizationData;

    @PreRemove
    public void preRemove() {
        items.forEach(i -> i.setVisualizationData(null));
        categories.forEach(c -> c.setVdata(null));
    }
}
