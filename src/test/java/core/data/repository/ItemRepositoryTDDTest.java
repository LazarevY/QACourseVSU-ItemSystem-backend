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

    @AfterEach
    public void cleanEntityManager(){
        em.clear();
    }

    @BeforeEach
    public void addData(){

        val dateMin = LocalDateTime.now();
        val dateMax = LocalDateTime.now();

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

        i3.setBusy(true);
        i4.setBusy(true);
        i5.setBusy(true);
        i6.setBusy(true);
        i7.setBusy(true);

    }

    @Test
    @DisplayName("When no any criteria - should return all items")
    public void test_filter_001(){
        List<Item> items = itemRepo.getAllByCriteriaAndSort(
                new ItemDTO.ItemCriteria("", "", false, new ArrayList<>(), ItemDTO.ItemCriteria.ItemType.BOTH),
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
                    new ItemDTO.ItemCriteria("", "+7-(952)", true, new ArrayList<>(), ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(4, items.size());
        }

        @Test
        @DisplayName("'+7-(952)-99-92-15' - should return only matches")
        public void test_filter_003(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "+7-(952)-99-92-15", true, new ArrayList<>(), ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(2, items.size());
        }

        @Test
        @DisplayName("'64-25' - should return only matches")
        public void test_filter_004(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "64-25", true, new ArrayList<>(), ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(1, items.size());
        }

        @Test
        @DisplayName("'+8' - should return only matches")
        public void test_filter_005(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "+8", true, new ArrayList<>(), ItemDTO.ItemCriteria.ItemType.BUSY),
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
                    new ItemDTO.ItemCriteria("", "Андрей", false, new ArrayList<>(), ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(2, items.size());
        }

        @Test
        @DisplayName("matches for several users - should return only them items")
        public void test_filter_002(){
            List<Item> items = itemRepo.getAllByCriteriaAndSort(
                    new ItemDTO.ItemCriteria("", "Иван", false, new ArrayList<>(), ItemDTO.ItemCriteria.ItemType.BUSY),
                    new ItemDTO.ItemSort(ItemDTO.ItemSort.Criteria.NO));
            Assertions.assertEquals(3, items.size());
        }
    }

}
