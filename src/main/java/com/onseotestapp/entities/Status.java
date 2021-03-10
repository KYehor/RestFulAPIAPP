package com.onseotestapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class of status with properties <b>id</b>,<b>name</b>.
 *
 * @author Yehor Kachur
 * @version 1.0
 */
@Entity
@Table
@Setter
@Getter
public class Status {

    /** Field id is unique with auto increment in Database.This is status id. */
    @Id @Column(name = "id")
    private Long id;
    /** Field name. */
    @Column(length = 50,unique = true,nullable = false)
    private String name;

    /**
     * Constructor - creating a new object of Status class
     * @see Status
     */
    public Status(){}

    /**
     * Constructor - creating a new object with name value
     * @param name - Status name
     *
     * @see Status#Status()
     */
    public Status(String name){
        this.name = name;
    }

    /**
     * Constructor - creating a new object with specific values
     * @param id - Status id
     * @param name - Status name
     *
     * @see Status#Status()
     */
    public Status(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
