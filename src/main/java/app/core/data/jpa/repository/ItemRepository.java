package app.core.data.jpa.repository;

import app.core.data.jpa.persistance.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long>, ItemRepositoryCustom {
    List<Item> getByItemCategoryIdIn(List<Long> categoriesCodes);
    List<Item> getByItemCategoryId(Long categoryCodes);
}
