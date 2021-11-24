package app.core.data.jpa.repository;

import app.core.data.dto.ItemDTO;
import app.core.data.jpa.persistance.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepositoryCustom {
    List<Item> getAllByCriteriaAndSort(ItemDTO.ItemCriteria criteria, ItemDTO.ItemSort sort);
}
