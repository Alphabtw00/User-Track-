package com.example.usertrack.controller;

import com.example.usertrack.entity.User;
import com.example.usertrack.service.UserService;
import com.example.usertrack.usecasecobjects.UpdateUserRequest;
import com.example.usertrack.usecasecobjects.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create_user")
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User createdUser = userService.createUser(createUserRequest);
        return ResponseEntity.ok("User created successfully with ID: " + createdUser.getId());
    }


    @PostMapping("/get_users")
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String mobNum,
            @RequestParam(required = false) UUID managerId) {
        List<User> users = userService.getUsers(userId, mobNum, managerId);
        return ResponseEntity.ok(users);
    }


    @PostMapping("/delete_user")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("user_id")) {
            UUID userId = UUID.fromString(requestBody.get("user_id"));
            userService.deleteUserById(userId);
            return ResponseEntity.ok("User deleted successfully");
        }
        else if (requestBody.containsKey("mob_num")) {
            String mobNum = requestBody.get("mob_num");
            userService.deleteUserByMobNum(mobNum);
            return ResponseEntity.ok("User deleted successfully");
        }
        else {
            return ResponseEntity.badRequest().body("Invalid request body. Please provide either 'user_id' or 'mob_num'");
        }
    }


    @PostMapping("/update_user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.isNormalUpdate()) {
            String resultMessage = userService.updateSingleUser(updateUserRequest);
            return ResponseEntity.ok(resultMessage);
        }
        else {
            List<String> updatedUsers = userService.bulkUpdateUsers(updateUserRequest);
            return ResponseEntity.ok(updatedUsers);
        }
    }
}




