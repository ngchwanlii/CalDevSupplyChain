package com.caldevsupplychain.account.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false)
    String name;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private User user;

    public Company(String name){
        this.name = name;
    }

}
