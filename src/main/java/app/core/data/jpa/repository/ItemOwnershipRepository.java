package app.core.data.jpa.repository;

import app.core.data.jpa.persistance.ItemOwnership;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemOwnershipRepository extends CrudRepository<ItemOwnership, Long>{

    Optional<ItemOwnership> getByOwnershipEndDateIsNullAndItemId(Long itemId);

    List<ItemOwnership.ItemsOnly> getItemByUserProfileIdAndOwnershipEndDateIsNull(Long userId);
    List<ItemOwnership> getByUserProfileIdAndOwnershipEndDateIsNull(Long userId);
    List<ItemOwnership> getAllByOwnershipEndDateIsNull();
    List<ItemOwnership> getAllByItemId(Long itemId);

    boolean existsByItemIdAndOwnershipEndDateIsNull(Long itemId);

    default List<ItemOwnership.ItemsOnly> getUserItems(Long userId){
        return getItemByUserProfileIdAndOwnershipEndDateIsNull(userId);
    }
}
