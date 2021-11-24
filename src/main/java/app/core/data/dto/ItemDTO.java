package app.core.data.dto;

import app.core.data.validation.groups.Create;
import app.core.data.validation.groups.Update;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class ItemDTO {
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Long code;
    @NotNull
    private String description;

    @Null(groups = Create.class)
    private boolean busy;
    private ItemCategoryDTO itemCategory;
    private VisualizationDataDTO visualizationData;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemCriteria {

        public enum ItemType {
            FREE, BUSY, BOTH
        }

        private String namePattern;
        private String ownerPattern;
        private boolean useOwnerPatternAsPhone;
        private List<Long> categories = new ArrayList<>();
        private ItemType itemType = ItemType.BOTH;
    }   

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemSort {
        public enum Criteria {
            NAME, OWNER, NO
        }

        private Criteria criteria = Criteria.NO;
        private Boolean isDesc = false;

        public ItemSort(Criteria criteria) {
            this.criteria = criteria;
            this.isDesc = false;
        }
    }
}
