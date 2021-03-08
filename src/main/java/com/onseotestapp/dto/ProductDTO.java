package com.onseotestapp.dto;

import com.onseotestapp.domain.Status;
import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private Status oldStatus;
    private Status newStatus;

    public ProductDTO(){}

    public ProductDTO(Long id,Status oldStatus,Status newStatus){
        this.id = id;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

}
