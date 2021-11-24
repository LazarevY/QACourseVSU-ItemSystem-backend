package app.core.service.requests;

import lombok.Data;

@Data
public class OpenOwnershipReq {
    private final Long userId;
    private final Long itemId;
}
