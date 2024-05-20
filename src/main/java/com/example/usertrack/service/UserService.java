package com.example.usertrack.service;

import com.example.usertrack.entity.Manager;
import com.example.usertrack.entity.User;
import com.example.usertrack.exception.BulkUpdateNotAllowedException;
import com.example.usertrack.exception.InvalidIdException;
import com.example.usertrack.exception.InvalidManagerException;
import com.example.usertrack.exception.InvalidUpdateFieldsException;
import com.example.usertrack.repository.ManagerRepository;
import com.example.usertrack.repository.UserRepository;
import com.example.usertrack.usecasecobjects.UpdateUserRequest;
import com.example.usertrack.usecasecobjects.CreateUserRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;

    public UserService(UserRepository userRepository, ManagerRepository managerRepository) {
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
    }

    public User createUser(CreateUserRequest createUserRequest) {
        Optional<Manager> manager = managerRepository.findById(createUserRequest.getManagerId());
        if (manager.isEmpty()) {
            throw new InvalidManagerException("Invalid manager ID");
        }

        if(!manager.get().isActive()){
            throw new InvalidManagerException("Manager isn't active");
        }

        //if validation error occurs then spring throws exception automatically
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFullName(createUserRequest.getFullName());
        user.setMobNum(createUserRequest.getMobNum());
        user.setPanNum(createUserRequest.getPanNum().toUpperCase());
        user.setManagerId(createUserRequest.getManagerId());

        return userRepository.save(user);
    }


    public List<User> getUsers(UUID userId, String mobNum, UUID managerId) {
        List<User> users = new ArrayList<>();

        if (userId != null) {
            Optional<User> user = userRepository.findById(userId);
            if(!(user.isPresent())){
                throw new InvalidIdException("Invalid User Id");
            }
            else {
                users.add(user.get());
            }
        }
        else if (mobNum != null) {
            List<User> userList = userRepository.findByMobNumEquals(mobNum);
            if(userList.isEmpty()){
                throw new InvalidIdException("Invalid Mobile Number");
            }
            else {
                userList.forEach(users::add);
            }
        }
        else if (managerId != null) {
            List<User> userList = userRepository.findByManagerIdEquals(managerId);
            if (userList.isEmpty()) {
                throw new InvalidIdException("Invalid Manager Id");
            }
            else {
                userList.forEach(users::add);
            }
        }
        else {
            List<User> userList = userRepository.findAll();
            userList.forEach(users::add);
        }

        return users;
    }


    public void deleteUserById(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new InvalidIdException("Invalid User Id");
        }
    }

    public void deleteUserByMobNum(String mobNum) {
        List<User> users = userRepository.findByMobNumEquals(mobNum);
        if (!users.isEmpty()) {
            userRepository.deleteAll(users);
        } else {
            throw new InvalidIdException("Invalid Mobible Number");
        }
    }

    public String updateSingleUser(UpdateUserRequest updateUserRequest) {
        UUID userId = updateUserRequest.getUserId();
        Map<String, String> updateData = updateUserRequest.getUpdateData();

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new InvalidIdException("Invalid User ID");
        }

        if (updateData.isEmpty()) {
            throw new InvalidUpdateFieldsException("At least one field must be provided for update");
        }

        Set<String> allowedFields = new HashSet<>(Arrays.asList("full_name", "mob_num", "pan_num", "manager_id"));
        Set<String> invalidFields = updateData.keySet().stream()
                .filter(field -> !allowedFields.contains(field))
                .collect(Collectors.toSet());

        if (!invalidFields.isEmpty()) {
            throw new InvalidUpdateFieldsException("Invalid fields: " + String.join(", ", invalidFields));
        }



        User user = optionalUser.get();

        if (updateData.containsKey("full_name")) {
            user.setFullName(updateData.get("full_name"));
        }
        if (updateData.containsKey("mob_num")) {
            user.setMobNum(updateData.get("mob_num"));
        }
        if (updateData.containsKey("pan_num")) {
            user.setPanNum(updateData.get("pan_num").toUpperCase());
        }

        if (updateData.containsKey("manager_id")) {
            UUID managerId = UUID.fromString(updateData.get("manager_id"));
            Optional<Manager> optionalManager = managerRepository.findById(managerId);

            if (optionalManager.isEmpty()) {
                throw new InvalidIdException("Invalid Manager ID");
            }
            if (!optionalManager.get().isActive()) {
                throw new InvalidManagerException("Manager is inactive");
            }

            if (user.getManagerId() == null) {
                user.setManagerId(managerId);
                userRepository.save(user);
                return "User with id: " + userId + " successfully saved.";
            }
            else {
                user.setActive(false);

                User newActiveUser = new User();
                UUID newId = UUID.randomUUID();
                newActiveUser.setId(newId);
                newActiveUser.setFullName(user.getFullName());
                newActiveUser.setMobNum(user.getMobNum());
                newActiveUser.setPanNum(user.getPanNum());
                newActiveUser.setManagerId(managerId);

                userRepository.save(newActiveUser);
                userRepository.save(user);

                return "User with ID " + userId + " set inactive and new User with id: " + newId + " created with new manager.";
            }
        }
        else {
            return "User with id: " + userId + " successfully saved.";

        }
    }


    public List<String> bulkUpdateUsers(UpdateUserRequest updateUserRequest) {
        List<UUID> userIds = updateUserRequest.getUserIds();
        Map<String, String> updateData = updateUserRequest.getUpdateData();

        if (!updateData.containsKey("manager_id")) {
            throw new InvalidManagerException("manager_id field is required for bulk update");
        }

        Set<String> allowedKeys = Set.of("manager_id");
        Set<String> extraKeys = updateData.keySet().stream()
                .filter(key -> !allowedKeys.contains(key))
                .collect(Collectors.toSet());

        if (!extraKeys.isEmpty()) {
            throw new BulkUpdateNotAllowedException("Bulk update is allowed only for manager_id field", extraKeys);
        }

        UUID managerId = UUID.fromString(updateData.get("manager_id"));
        Optional<Manager> optionalManager = managerRepository.findById(managerId);

        if (optionalManager.isEmpty()) {
            throw new InvalidIdException("Invalid Manager Id for bulk update");
        }
        if (!(optionalManager.get().isActive())) {
            throw new InvalidManagerException("Manager is inactive for bulk update");
        }

        List<String> successResponseMessages = new ArrayList<>();

        for (UUID userId : userIds) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {

                User user = optionalUser.get();

                if (user.getManagerId() == null) {
                    user.setManagerId(managerId);
                    userRepository.save(user);
                    successResponseMessages.add("User with ID " + userId + " updated with new manager");
                }
                else {
                    user.setActive(false);

                    User newActiveUser = new User();
                    UUID newId = UUID.randomUUID();
                    newActiveUser.setId(newId);
                    newActiveUser.setFullName(user.getFullName());
                    newActiveUser.setMobNum(user.getMobNum());
                    newActiveUser.setPanNum(user.getPanNum());
                    newActiveUser.setManagerId(managerId);

                    userRepository.save(newActiveUser);

                    userRepository.save(user);

                    successResponseMessages.add("User with ID " + userId + " set inactive and new User with id: " + newId + " created with new manager.");
                }
            }
            else {
                successResponseMessages.add("User with id: " + userId + " does not exist. Updating Failed");
            }
        }

        return successResponseMessages;
    }

}

