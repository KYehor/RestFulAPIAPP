package com.onseotestapp.domain;

import lombok.Data;
import javax.persistence.*;

/**
 * Class of production with properties <b>id</b>,<b>name</b>,<b>price</b>,<b>count</b>,<b>type</b>,<b>date</b>,
 * <b>shelf life</b>,<b>image</b>,<b>status</b>.
 *
 * @author Yehor Kachur
 * @version 1.0
 */
@Data
@Entity
@Table(name = "product")
public class Product {

    /** Field id is unique with auto increment in Database. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Field name. */
    @Column(length = 50,unique = true,nullable = false)
    private String name;
    /** Field price. */
    private float price;
    /** Field count. */
    private int count;
    /** Field type. */
    private String type;
    /** Field date,show time when was created instance product. */
    private String date;
    /** Field shelf life */
    private float shelfLife;
    /** Field image */
    @Column(nullable = true, length = 64)
    private String image;
    /** Field status */
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    /**
     * Constructor - creating a new object
     * @see Product
     */
    public Product(){}

    /**
     * Constructor - creating a new object with specific values
     * @param name - Product name
     * @param price - Product price
     * @param count - Number of products
     * @param type - Product type for example(Food,electronics,furniture)
     * @param shelfLife - Product shelf life
     * @see Product#Product()
     */
    public Product(String name, float price, int count, String type, float shelfLife) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.type = type;
        this.shelfLife = shelfLife;
    }
}
