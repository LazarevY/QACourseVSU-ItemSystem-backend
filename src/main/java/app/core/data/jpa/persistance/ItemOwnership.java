package app.core.data.jpa.persistance;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemOwnership extends app.core.data.jpa.persistance.Entity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private LocalDateTime ownershipStartDate;

    private LocalDateTime ownershipEndDate;

    public interface ItemsOnly {
        Item getItem();
    }
}
