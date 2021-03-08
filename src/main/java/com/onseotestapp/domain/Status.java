package com.onseotestapp.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Status {

    @Id @Column(name = "id")
    private Long id;
    @Column(length = 50,unique = true,nullable = false)
    private String name;

    public Status(){}

    public Status(String name){
        this.name = name;
    }

    public Status(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
