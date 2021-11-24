package app.core.data.jpa.repository.impl;

import app.core.data.dto.ItemDTO;
import app.core.data.jpa.persistance.Item;
import app.core.data.jpa.repository.ItemRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Item> getAllByCriteriaAndSort(ItemDTO.ItemCriteria criteria, ItemDTO.ItemSort sort) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> item = cb.createTupleQuery();
        Root<Item> itemRoot = item.from(Item.class);
        ItemDTO.ItemCriteria.ItemType itemType = Objects.requireNonNull(criteria.getItemType());

        List<Predicate> predicates = new ArrayList<>();

        if (itemType != ItemDTO.ItemCriteria.ItemType.BOTH) {
            predicates.add(cb.equal(itemRoot.get("busy"), criteria.getItemType() == ItemDTO.ItemCriteria.ItemType.BUSY));
        }
        Join<Object, Object> joinOwnership = null;
        Join<Object, Object> joinUserProfile = null;

        if (criteria.getItemType() == ItemDTO.ItemCriteria.ItemType.BUSY && criteria.getOwnerPattern() != null) {
            joinOwnership = itemRoot.join("ownerships", JoinType.LEFT);
            joinUserProfile = joinOwnership.join("userProfile", JoinType.LEFT);
            predicates.add(cb.like(joinUserProfile.get("phone"), String.format("%%%s%%", criteria.getOwnerPattern())));
        }
        if (criteria.getNamePattern() != null) {
            predicates.add(cb.like(cb.lower(itemRoot.get("name")), String.format("%%%s%%", criteria.getNamePattern().toLowerCase(Locale.ROOT))));
        }

        if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
            CriteriaBuilder.In<Long> id = cb.in(itemRoot.get("itemCategory").get("id"));
            for (Long cId : criteria.getCategories()) {
                id.value(cId);
            }
            predicates.add(id);
        }
        List<Order> orders = new ArrayList<>();
        if (sort.getCriteria() != ItemDTO.ItemSort.Criteria.NO) {
            if (sort.getCriteria() == ItemDTO.ItemSort.Criteria.OWNER) {
                joinOwnership =
                        Optional.ofNullable(joinOwnership).orElse(itemRoot.join("ownerships", JoinType.LEFT));
                joinUserProfile =
                        Optional.ofNullable(joinUserProfile).orElse(joinOwnership.join("userProfile", JoinType.LEFT));
                addUserSortCriteria(cb, joinUserProfile, sort, orders);
            } else {
                addItemSortCriteria(cb, itemRoot, sort, orders);
            }
        }
        CriteriaQuery<Tuple> query1 = sort.getCriteria() == ItemDTO.ItemSort.Criteria.OWNER ?
                item.multiselect(itemRoot, joinUserProfile) :
                item.multiselect(itemRoot);
        query1.where(predicates.toArray(new Predicate[0])).orderBy(orders).distinct(true);

        TypedQuery<Tuple> query = entityManager.createQuery(query1);
        return query.getResultList().stream().map(tuple -> tuple.get(itemRoot)).collect(Collectors.toList());

    }

    private void addUserSortCriteria(CriteriaBuilder cb, From<?, ?> query, ItemDTO.ItemSort sort, List<Order> orders) {
        if (sort.getIsDesc()) {
            orders.add(cb.desc(query.get("surname")));
            orders.add(cb.desc(query.get("name")));
            orders.add(cb.desc(query.get("patronymic")));
        } else {
            orders.add(cb.asc(query.get("surname")));
            orders.add(cb.asc(query.get("name")));
            orders.add(cb.asc(query.get("patronymic")));
        }
    }

    private void addItemSortCriteria(CriteriaBuilder cb, From<?, ?> query, ItemDTO.ItemSort sort, List<Order> orders){
        orders.add(sort.getIsDesc() ? cb.desc(query.get("name")) : cb.asc(query.get("name")));
    }
}
