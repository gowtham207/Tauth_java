package com.gowtham.project01.models;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gowtham.project01.enums.UserStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JsonIgnore
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDateTime DOB;

    @Column(name = "is_verified", nullable = false)
    @JsonIgnore
    @Builder.Default
    private Boolean isVerified = false; // default false

    @Column(name = "last_login_time")
    @JsonIgnore
    private LocalDateTime lastLoginTime;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "country")
    private String country;

    @Column(name = "role_id")
    @JsonIgnore
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "id"))
    private String role_id;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "mfa_enabled", nullable = false)
    @Builder.Default
    @JsonIgnore
    private Boolean mfaEnabled = false;

    @Column(name = "mfa_secret")
    @JsonIgnore
    private String mfaSecret;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    @Column(name = "status", nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE; // default ACTIVE

    @CreatedDate
    @JsonIgnore
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    @JsonIgnore
    private LocalDateTime deletedAt;
}
