package com.example.usertrack.repository;

import com.example.usertrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByMobNumEquals(String mobNum); //used equals at end as without it, it sends all users even if mobNum is a substring( currently it matches exactly)

    List<User> findByManagerIdEquals(UUID managerId);
}
