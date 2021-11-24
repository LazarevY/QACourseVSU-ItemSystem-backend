package core.service;


import app.StartApplication;
import app.core.data.jpa.repository.ItemOwnershipRepository;
import app.core.data.jpa.repository.ItemRepository;
import app.core.service.impl.ItemManagementServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@ContextConfiguration(classes = {StartApplication.class})
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
public class ItemManagementTDDTest {

    @Mock
    private ItemRepository mockItemRepo;

    @Mock
    private ItemOwnershipRepository mockIORepo;

    @InjectMocks
    private ItemManagementServiceImpl service;


}
