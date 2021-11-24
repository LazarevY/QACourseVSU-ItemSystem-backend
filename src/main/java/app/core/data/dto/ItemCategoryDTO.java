package app.core.data.dto;


import app.core.data.validation.groups.Create;
import app.core.data.validation.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategoryDTO {
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;
    @NotNull
    private Long code;
    private ItemCategoryDTO parentItemCategory;
    private VisualizationDataDTO vdata;
    @NotNull
    private String name;
    @NotNull
    private String description;
}
