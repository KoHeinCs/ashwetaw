package com.ashwetaw.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Setter
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    @Column(unique = true)
    private String rollNo;
    private String year;
    private char section;
    private String address;
    @Column(unique = true)
    private String email;
}
