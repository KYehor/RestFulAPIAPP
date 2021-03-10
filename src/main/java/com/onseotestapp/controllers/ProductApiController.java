package com.onseotestapp.controllers;

import com.onseotestapp.entities.Product;
import com.onseotestapp.entities.Status;
import com.onseotestapp.dto.ProductDTO;
import com.onseotestapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Class product controller api with properties <b>uploadPath</b>,<b>productService</b>.
 *
 * @author Yehor Kachur
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class ProductApiController {

    /** Field uploadPath for path to the folder, where we can save an images. */
    @Value("${upload.path}")
    private String uploadPath;

    /** Field productServer that realize some logic of our application. */
    private final ProductService productService;

    /**
     * Constructor - creating a new object of our controller that contain Product service instance.
     * @param service - instance of ProductService
     * @see ProductService
     */
    @Autowired
    public ProductApiController(ProductService service) {
        this.productService = service;
    }

    /**
     * We use this method to find exist product by id in our DataBase.
     * We realize method findProductById from ProductRepository.
     *
     * @param  id - product unique id
     * @see Product
     */
    @GetMapping("/product/find/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    /**
     * This method check if this product instance from params exist and correct,if yes we save if not we have status Not_Found.
     *
     * @param  product the object of Product class
     * @return      Response is id of product that we saved in DB.
     * @see         ResponseEntity<Long>
     */
    @PostMapping(value ="/product/save",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> saveProduct(@RequestBody @Valid Product product) {
        Product newProduct = productService.findByName(product.getName());
        if (Objects.isNull(newProduct)) {
            return new ResponseEntity<Long>(productService.save(product).getId(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method find product by id and delete from db used logic from ProductService.
     *
     * @param  id the unique number of Product object
     */
    @DeleteMapping("/product/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
    }

    /**
     * Method add image in product.And save this product in DataBase after.
     *
     * @param multipartFile the object that contain image
     * @param  product the object of Product class
     * @return      Response is url our image and status
     * @see         ResponseEntity<String>
     */
    @PostMapping("/product/add/img/{product}")
    public ResponseEntity<String> addImage(@RequestBody @Valid MultipartFile multipartFile, @PathVariable("product") Product product) {
        Product newProduct = productService.addImage(multipartFile,product);
        productService.save(newProduct);
        String url = "localhost:8080/images/" + newProduct.getImage();
        URI location = null;
        try {
            location = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(location);
        httpHeaders.set("Content-Type", "image/png");
        return new ResponseEntity<String>(url,httpHeaders,HttpStatus.CREATED);

    }

    /**
     * Method change status in our Product object,set product fields in ProductDTO fields and return ProductDTO object.
     * ProductDTO object contain 3 fields:<b>Product id</b>,<b>old status</b>,<b>new status</b>.
     *
     * @param id the field from Product class
     * @param  status the object of Status class
     * @return      Response is ProductDTO and status
     * @see         ResponseEntity<ProductDTO>
     */
    @PutMapping(value ="/status/change/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> changeStatus(@PathVariable("id") Long id,@RequestBody @Valid Status status){
        Product product = productService.findProductById(id);
        Status oldStatus = product.getStatus();
        product.setStatus(status);
        productService.update(product);
        ProductDTO productDTO = new ProductDTO(product.getId(),status,oldStatus);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.CREATED);
    }

    /**
     * Method find all products that contain this status and return them ids.
     *
     * @param  status String
     * @return      list id by product status
     * @see         List<Long>
     */
    @GetMapping("/status/{status}")
    public List<Long> findByStatusAllId(@PathVariable String status){
        return productService.findByStatus(status);
    }
}
