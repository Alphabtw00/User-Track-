package com.example.usertrack.usecasecobjects;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class UpdateUserRequest {
    private UUID userId;
    private List<UUID> userIds;
    private Map<String, String> updateData;

    public boolean isNormalUpdate() {
        return userId != null;
    }
}
