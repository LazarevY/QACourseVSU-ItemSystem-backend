package app;

import app.core.data.jpa.persistance.Item;
import app.core.data.jpa.persistance.ItemCategory;
import app.core.data.jpa.persistance.ItemOwnership;
import app.core.data.jpa.persistance.UserProfile;
import app.core.data.jpa.repository.ItemCategoryRepository;
import app.core.data.jpa.repository.ItemOwnershipRepository;
import app.core.data.jpa.repository.ItemRepository;
import app.core.data.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class StartApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Autowired
    ItemRepository ir;

    @Autowired
    ItemCategoryRepository icr;

    @Autowired
    UserRepository ur;

    @Autowired
    ItemOwnershipRepository ior;

    private void fillBase(){
        LocalDateTime dateMin = LocalDateTime.now();
        LocalDateTime dateMax = LocalDateTime.now();

        ItemCategory c1 = icr.save(new ItemCategory(null, "NAME1", "D1"));
        ItemCategory c2 = icr.save(new ItemCategory(c1, null, "NAMwwwwE2", "D1"));
        ItemCategory c3 = icr.save(new ItemCategory(null, "VeryCoolNAme", "D1"));
        ItemCategory c4 = icr.save(new ItemCategory(c3, null, "coOlname", "D1"));
        ItemCategory c5 = icr.save(new ItemCategory(c3, null, "NameNAme", "D1"));
        ItemCategory c6 = icr.save(new ItemCategory(c4, null, "ADAD", "D1"));
        ItemCategory c7 = icr.save(new ItemCategory(c5, null, "AmeN", "D1"));


        Item i1 = ir.save(new Item(null, "Item1", "D1"));
        Item i2 = ir.save(new Item(null, "Item2", "D1"));
        Item i3 = ir.save(new Item(c1, null, "Splash", "D1"));
        Item i4 = ir.save(new Item(c1, null, "Play", "D1"));
        Item i5 = ir.save(new Item(c2, null, "Split", "D1"));
        Item i6 = ir.save(new Item(c3, null, "Moooo", "D1"));
        Item i7 = ir.save(new Item(c4, null, "Moa", "D1"));
        Item i8 = ir.save(new Item(c5, null, "oao", "D1"));
        Item i9 = ir.save(new Item(c6, null, "ei", "D1"));
        Item i10 = ir.save(new Item(c7, null, "neie", "D1"));

        UserProfile u1 = ur.save(new UserProfile("Петров Андрей Владимирович", "+7-(952)-99-92-15"));
        UserProfile u2 = ur.save(new UserProfile("Фамилия Артем Пиванович", "+7-(952)-96-98-55"));
        UserProfile u3 = ur.save(new UserProfile("Гвоздь Иван Васильевич", "+7-(904)-77-64-25"));

        ItemOwnership io1 = ior.save(new ItemOwnership(u1, i3, dateMin, null));
        ItemOwnership io2 = ior.save(new ItemOwnership(u1, i4, dateMin, dateMax));
        ItemOwnership io3 = ior.save(new ItemOwnership(u2, i4, dateMin, null));
        ItemOwnership io4 = ior.save(new ItemOwnership(u2, i5, dateMin, null));
        ItemOwnership io5 = ior.save(new ItemOwnership(u1, i6, dateMin, null));
        ItemOwnership io6 = ior.save(new ItemOwnership(u3, i7, dateMin, null));
        ItemOwnership io7 = ior.save(new ItemOwnership(u3, i8, dateMin, dateMax));
        ItemOwnership io8 = ior.save(new ItemOwnership(u3, i9, dateMin, dateMax));
        ItemOwnership io9 = ior.save(new ItemOwnership(u1, i8, dateMin, dateMax));
        ItemOwnership io10 = ior.save(new ItemOwnership(u2, i9, dateMin, dateMax));

        i3.setBusy(true);
        i4.setBusy(true);
        i5.setBusy(true);
        i6.setBusy(true);
        i7.setBusy(true);
    }

    @Override
    public void run(String... args) {
        //fillBase();
    }

}