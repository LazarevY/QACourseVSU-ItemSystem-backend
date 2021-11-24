package app.core.service.impl;

import app.core.data.dto.ItemDTO;
import app.core.data.dto.ItemOwnershipDTO;
import app.core.data.jpa.persistance.ItemOwnership;
import app.core.data.jpa.repository.ItemOwnershipRepository;
import app.core.data.jpa.repository.ItemRepository;
import app.core.service.interfaces.ItemManagementService;
import app.core.service.requests.CloseOwnershipReq;
import app.core.service.requests.OpenOwnershipReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemManagementServiceImpl implements ItemManagementService {

    private final ItemRepository itemRepo;
    private final ItemOwnershipRepository itemOwnershipRepo;

    @Autowired
    public ItemManagementServiceImpl(ItemRepository itemRepo, ItemOwnershipRepository itemOwnershipRepo) {
        this.itemRepo = itemRepo;
        this.itemOwnershipRepo = itemOwnershipRepo;
    }

    @Override
    public ItemDTO createItem(ItemDTO createReq) {
        return null;
    }

    @Override
    public ItemDTO getItem(Long itemId) {
        return null;
    }

    @Override
    public ItemDTO updateItem(ItemDTO updateReq) {
        return null;
    }

    @Override
    public void deleteItem(Long itemId) {

    }

    @Override
    public List<ItemDTO> getItemsByFilter(ItemDTO.ItemCriteria criteria, ItemDTO.ItemSort sort) {
        return null;
    }

    @Override
    public ItemOwnershipDTO openOwnership(OpenOwnershipReq req) {
        return null;
    }

    @Override
    public ItemOwnershipDTO closeOwnership(CloseOwnershipReq req) {
        return null;
    }

    @Override
    public List<ItemOwnership> itemOwnershipHistory(Long itemId) {
        return null;
    }
}
