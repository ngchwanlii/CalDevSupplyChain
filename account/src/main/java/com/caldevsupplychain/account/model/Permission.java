package com.caldevsupplychain.account.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_name", nullable = false)
    String name;

    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL)
    Collection<Role> roles;
}
