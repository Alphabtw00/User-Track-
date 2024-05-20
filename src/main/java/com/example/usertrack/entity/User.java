package com.example.usertrack.entity;

import com.example.usertrack.customvalidation.ValidMobileNumber;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    private UUID id;

    @NotBlank(message = "Full name must not be empty")
    private String fullName;

    @NotBlank(message = "Mobile number must not be empty")
    @Size(min = 10, max = 10, message = "Mobile number must be a valid 10-digit number")
    @ValidMobileNumber(message = "Invalid Mobile Number") //custom validation
    //@Pattern(regexp = "^(\\+91|0)?\\d{10}$", message = "Invalid mobile number format")
    private String mobNum;

    @NotBlank(message = "PAN number must not be empty")
    @Pattern(regexp = "^[A-Z]{5}\\d{4}[A-Z]$", message = "Invalid PAN number format")
    private String panNum;

    private UUID managerId;

    @CreationTimestamp //whenever entity created hibernate sets the time on this field
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isActive = true;
}
