package app.core.service.interfaces;

import app.core.data.dto.ItemDTO;
import app.core.data.dto.ItemOwnershipDTO;
import app.core.data.jpa.persistance.ItemOwnership;
import app.core.service.requests.CloseOwnershipReq;
import app.core.service.requests.OpenOwnershipReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemManagementService {

    ItemDTO createItem(ItemDTO createReq);
    ItemDTO getItem(Long itemId);
    ItemDTO updateItem(ItemDTO updateReq);
    void deleteItem(Long itemId);

    List<ItemDTO> getItemsByFilter(ItemDTO.ItemCriteria criteria, ItemDTO.ItemSort sort);
    default List<ItemDTO> getItemsByFilter(ItemDTO.ItemCriteria criteria){
        return getItemsByFilter(criteria, new ItemDTO.ItemSort());
    }

    ItemOwnershipDTO openOwnership(OpenOwnershipReq req);
    ItemOwnershipDTO closeOwnership(CloseOwnershipReq req);
    List<ItemOwnership> itemOwnershipHistory(Long itemId);


}
