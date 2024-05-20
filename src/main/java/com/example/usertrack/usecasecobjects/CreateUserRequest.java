package com.example.usertrack.usecasecobjects;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserRequest {
    private String fullName;
    private String mobNum;
    private String panNum;
    private UUID managerId;

}