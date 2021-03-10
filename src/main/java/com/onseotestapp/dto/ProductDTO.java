package com.onseotestapp.dto;

import com.onseotestapp.entities.Status;
import lombok.Getter;
import lombok.Setter;

/**
 * Class of product DTO with properties <b>id</b>,<b>name</b>.
 *
 * @author Yehor Kachur
 * @version 1.0
 */
@Getter
@Setter
public class ProductDTO {

    /** This is product id. */
    private Long id;
    /** This is old status of Product instance */
    private Status oldStatus;
    /** This is new status of Product instance */
    private Status newStatus;

    /**
     * Constructor - creating a new object of ProductDTO class
     * @see ProductDTO
     */
    public ProductDTO(){}

    /**
     * Constructor - creating a new object with specific values
     * @param id - Product id
     * @param oldStatus - Product old status before changed
     * @param newStatus - Product new status after changed
     * @see ProductDTO#ProductDTO()
     */
    public ProductDTO(Long id,Status oldStatus,Status newStatus){
        this.id = id;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

}
