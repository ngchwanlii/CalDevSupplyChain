package com.caldevsupplychain.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime createdOn;

    @Column(updatable = true)
    private LocalDateTime lastModified;

    @PrePersist
    protected void onCreate() {
        setCreatedOn(LocalDateTime.now());
    }

    @PreUpdate
    protected void onPersist() {
        setLastModified(LocalDateTime.now());
    }

}