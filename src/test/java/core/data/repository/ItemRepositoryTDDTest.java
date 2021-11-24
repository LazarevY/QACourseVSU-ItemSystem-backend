package core.data.repository;


import app.StartApplication;
import app.core.data.dto.ItemDTO;
import app.core.data.jpa.persistance.Item;
import app.core.data.jpa.persistance.ItemCategory;
import app.core.data.jpa.persistance.ItemOwnership;
import app.core.data.jpa.persistance.UserProfile;
import app.core.data.jpa.repository.ItemCategoryRepository;
import app.core.data.jpa.repository.ItemRepository;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ContextConfiguration(classes = {StartApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class ItemRepositoryTDDTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private ItemCategoryRepository itemCategoryRepo;

    private TestData data;


    class TestData{

        LocalDateTime dateMin = LocalDateTime.now();
        LocalDateTime dateMax = LocalDateTime.now();

        ItemCategory c1 = em.persist(new ItemCategory(null, "NAME1", "D1"));
        ItemCategory c2 = em.persist(new ItemCategory(c1, null, "NAMwwwwE2", "D1"));
        ItemCategory c3 = em.persist(new ItemCategory(null, "VeryCoolNAme", "D1"));
        ItemCategory c4 = em.persist(new ItemCategory(c3,null, "coOlname", "D1"));
        ItemCategory c5 = em.persist(new ItemCategory(c3,null, "NameNAme", "D1"));
        ItemCategory c6 = em.persist(new ItemCategory(c4,null, "ADAD", "D1"));
        ItemCategory c7 = em.persist(new ItemCategory(c5,null, "AmeN", "D1"));


        Item i1 = em.persist(new Item(null, "Item1", "D1"));
        Item i2 = em.persist(new Item(null, "Item2", "D1"));
        Item i3 = em.persist(new Item(c1,null, "Splash", "D1"));
        Item i4 = em.persist(new Item(c1,null, "Play", "D1"));
        Item i5 = em.persist(new Item(c2,null, "Split", "D1"));
        Item i6 = em.persist(new Item(c3,null, "Moooo", "D1"));
        Item i7 = em.persist(new Item(c4,null, "Moa", "D1"));
        Item i8 = em.persist(new Item(c5,null, "oao", "D1"));
        Item i9 = em.persist(new Item(c6,null, "ei", "D1"));
        Item i10 = em.persist(new Item(c7,null, "neie", "D1"));

        UserProfile u1 = em.persist(new UserProfile("Петров Андрей Владимирович", "+7-(952)-99-92-15"));
        UserProfile u2 = em.persist(new UserProfile("Фамилия Артем Пиванович", "+7-(952)-96-98-55"));
        UserProfile u3 = em.persist(new UserProfile("Гвоздь Иван Васильевич", "+7-(904)-77-64-25"));

        ItemOwnership io1 = em.persist(new ItemOwnership(u1, i3, dateMin, null));
        ItemOwnership io2 = em.persist(new ItemOwnership(u1, i4, dateMin, dateMax));
        ItemOwnership io3 = em.persist(new ItemOwnership(u2, i4, dateMin, null));
        ItemOwnership io4 = em.persist(new ItemOwnership(u2, i5, dateMin, null));
        ItemOwnership io5 = em.persist(new ItemOwnership(u1, i6, dateMin, null));
        ItemOwnership io6 = em.persist(new ItemOwnership(u3, i7, dateMin, null));
        ItemOwnership io7 = em.persist(new ItemOwnership(u3, i8, dateMin, dateMax));
        ItemOwnership io8 = em.persist(new ItemOwnership(u3, i9, dateMin, dateMax));
        ItemOwnership io9 = em.persist(new ItemOwnership(u1, i8, dateMin, dateMax));
        ItemOwnership io10 = em.persist(new ItemOwnership(u2, i9, dateMin, dateMax));

        TestData(){
            i3.setBusy(true);
            i4.setBusy(true);
            i5.setBusy(true);
            i6.setBusy(true);
            i7.setBusy(true);
        }
    }

    @BeforeEach
    public void initData(){
        data = new TestData();
    }

    @AfterEach
    public void clearData(){
        em.clear();
    }

    @Test
    @DisplayName("When no any criteria - should return all items")
    public void test_filter_001(){
        List<Item> items = itemRepo.getAllByCriteriaAndSort(
                new ItemDTO.ItemCriteria("", "", false, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BOTH),
                new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
        Assertions.assertEquals(10, items.size());
    }

    @Nested
    @DisplayName("When use phone pattern")
    public class WhenUsePhonePattern{
        @Test
        @DisplayName("'+7-(952)' - should return only matches")
        public void test_filter_002(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "+7-(952)", true, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(4, items.size());
            Assertions.assertTrue(items.stream().noneMatch(i -> i.getName().equals("Moa")));
        }

        @Test
        @DisplayName("'+7-(952)-99-92-15' - should return only matches")
        public void test_filter_003(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "+7-(952)-99-92-15", true, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(2, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i -> i.getName().equals("Splash")));
            Assertions.assertTrue(items.stream().anyMatch(i -> i.getName().equals("Moooo")));
        }

        @Test
        @DisplayName("'64-25' - should return only matches")
        public void test_filter_004(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "64-25", true, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(1, items.size());
            Assertions.assertTrue(items.stream().allMatch(i -> i.getName().equals("Moa")));
        }

        @Test
        @DisplayName("'+8' - should return only matches")
        public void test_filter_005(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "+8", true, new ArrayList<>(),false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(0, items.size());
        }
    }

    @Nested
    @DisplayName("When use name pattern")
    public class WhenUseNamePattern {
        @Test
        @DisplayName("matches for only one user - should return only his items")
        public void test_filter_001(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "Андрей", false, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(2, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i -> i.getName().equals("Splash")));
            Assertions.assertTrue(items.stream().anyMatch(i -> i.getName().equals("Moooo")));
        }

        @Test
        @DisplayName("matches for several users - should return only them items")
        public void test_filter_002(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "Иван", false, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(3, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i -> i.getName().equals("Play")));
            Assertions.assertTrue(items.stream().anyMatch(i -> i.getName().equals("Split")));
            Assertions.assertTrue(items.stream().anyMatch(i -> i.getName().equals("Moa")));
        }

        @Test
        @DisplayName("matches for all users - should return all busy items")
        public void test_filter_003(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "", false, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(5, items.size());
        }

        @Test
        @DisplayName("matches for another one user - should return only his items")
        public void test_filter_004(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "Фамилия", false, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(2, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i -> i.getName().equals("Play")));
            Assertions.assertTrue(items.stream().anyMatch(i -> i.getName().equals("Split")));
        }

        @Test
        @DisplayName("matches for no one - should return empty")
        public void test_filter_005(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "Имя которого нет", false, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(0, items.size());
        }

        @Test
        @DisplayName("matches for only one full name - should return empty")
        public void test_filter_006(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "Гвоздь Иван Васильевич", false, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(1, items.size());
            Assertions.assertTrue(items.stream().allMatch(i -> i.getName().equals("Moa")));
        }

    }

    @Nested
    @DisplayName("When use categories pattern")
    public class WhenUseCategoriesPattern{

        @Test
        @DisplayName("matches for only one category - should return related items")
        public void test_category_001(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "", false, List.of(data.c7.getID()), false, ItemDTO.ItemCriteria.ItemType.BOTH),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(1, items.size());
            Assertions.assertTrue(items.stream().allMatch(i -> i.equals(data.i10)));
        }

        @Test
        @DisplayName("matches for several bottom-level category - should return related items")
        public void test_category_002(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "", false, List.of(data.c7.getID(), data.c6.getID()), false, ItemDTO.ItemCriteria.ItemType.BOTH),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(2, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i -> i.equals(data.i10)));
            Assertions.assertTrue(items.stream().anyMatch(i -> i.equals(data.i9)));
        }

        @Test
        @DisplayName("matches for several and for no category - should return related items")
        public void test_category_003(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "", false, List.of(data.c7.getID(), data.c6.getID()), true, ItemDTO.ItemCriteria.ItemType.BOTH),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(4, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i -> i.equals(data.i10)));
            Assertions.assertTrue(items.stream().anyMatch(i -> i.equals(data.i9)));
            Assertions.assertTrue(items.stream().anyMatch(i -> i.equals(data.i1)));
            Assertions.assertTrue(items.stream().anyMatch(i -> i.equals(data.i2)));
        }

        @Test
        @DisplayName("matches for all created categoties - should return related items")
        public void test_category_004(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "", false,
                            List.of(
                                    data.c1.getID(),
                                    data.c2.getID(),
                                    data.c3.getID(),
                                    data.c4.getID(),
                                    data.c5.getID(),
                                    data.c6.getID(),
                                    data.c7.getID()
                            ),
                            false, ItemDTO.ItemCriteria.ItemType.BOTH),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(8, items.size());
            Assertions.assertTrue(items.stream().noneMatch(i -> i.equals(data.i1) || i.equals(data.i2)));
        }

        @Test
        @DisplayName("matches for all items(empty list) - should return all")
        public void test_category_005(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "", false, new ArrayList<>(), false, ItemDTO.ItemCriteria.ItemType.BOTH),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(10, items.size());
        }

        @Test
        @DisplayName("matches for all items(all categories chosen) - should return all")
        public void test_category_006(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "", false, new ArrayList<>(), true, ItemDTO.ItemCriteria.ItemType.BOTH),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(10, items.size());
        }

    }

    @Nested
    @DisplayName("When use item name pattern")
    public class WhenUseItemNamePattern{

        @Test
        @DisplayName("matches ony one item")
        public void test_item_name_pattern_001(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria(
                            "item1",
                            "",
                            false,
                            new ArrayList<>(),
                            false,
                            ItemDTO.ItemCriteria.ItemType. BOTH),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(1, items.size());
            Assertions.assertTrue(items.stream().allMatch(i ->  i.equals(data.i1)));
        }

        @Test
        @DisplayName("matches several item")
        public void test_item_name_pattern_002(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria(
                            "E",
                            "",
                            false,
                            new ArrayList<>(),
                            false,
                            ItemDTO.ItemCriteria.ItemType. BOTH),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(4, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i1)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i2)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i9)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i10)));
        }

    }

    @Nested
    @DisplayName("When use free/busy pattern")

    public class WhenUseFreeBusyPattern{

        @Test
        @DisplayName("matches all free items")
        public void test_free_busy_pattern_001(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria(
                            "",
                            "",
                            false,
                            new ArrayList<>(),
                            false,
                            ItemDTO.ItemCriteria.ItemType.FREE),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(5, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i1)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i2)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i8)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i9)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i10)));
        }

        @Test
        @DisplayName("matches all busy items")
        public void test_free_busy_pattern_002(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria(
                            "",
                            "",
                            false,
                            new ArrayList<>(),
                            false,
                            ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(5, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i3)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i4)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i5)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i6)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i7)));
        }

        @Test
        @DisplayName("matches all items")
        public void test_free_busy_pattern_003(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria(
                            "",
                            "",
                            false,
                            new ArrayList<>(),
                            false,
                            ItemDTO.ItemCriteria.ItemType.BOTH),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(10, items.size());
        }

    }

    @Nested
    @DisplayName("When use mixed pattern")
    public class WhenUseMixedPattern{

        @Test
        @DisplayName("matching by categories and owner name")
        public void test_mixed_filter_001(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria(
                            "",
                            "Фамилия Артем",
                            false,
                            List.of(data.c2.getID()),
                            false,
                            ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(1, items.size());
            Assertions.assertTrue(items.stream().allMatch(i ->  i.equals(data.i5)));
        }

        @Test
        @DisplayName("matching by categories and owner phone")
        public void test_mixed_filter_002(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria(
                            "",
                            "+7-(952)",
                            true,
                            List.of(data.c1.getID()),
                            false,
                            ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(2, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i3)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i4)));
        }

        @Test
        @DisplayName("matching by categories, owner phone and name")
        public void test_mixed_filter_003(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria(
                            "pl",
                            "+7-(9",
                            true,
                            List.of(data.c1.getID()),
                            false,
                            ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(2, items.size());
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i3)));
            Assertions.assertTrue(items.stream().anyMatch(i ->  i.equals(data.i4)));
        }
    }

}
