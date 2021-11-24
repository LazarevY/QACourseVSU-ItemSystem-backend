package app.core.data.dto;

import app.core.data.validation.groups.Create;
import app.core.data.validation.groups.Update;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class ItemOwnershipDTO {
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;
    @NotNull
    private UserDTO userProfile;
    @NotNull
    private ItemDTO item;
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private LocalDateTime ownershipStartDate;
    @Null(groups = Create.class)
    private LocalDateTime ownershipEndDate;
}
