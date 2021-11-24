package app.core.data.jpa.repository;

import app.core.data.jpa.persistance.ItemCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCategoryRepository extends CrudRepository<ItemCategory, Long> {
    List<ItemCategory> getByParentItemCategoryId(Long parentCategory);
    Optional<ItemCategory> getById(Long code);
}
