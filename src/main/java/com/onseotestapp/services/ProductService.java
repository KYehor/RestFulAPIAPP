package com.onseotestapp.services;

import com.onseotestapp.entities.Product;
import com.onseotestapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Class product service with properties <b>uploadPath</b>,<b>productRepo</b>.
 *
 * @author Yehor Kachur
 * @version 1.0
 */
@Service
public class ProductService {

    /** Field uploadPath for path to the folder, where we can save an images. */
    @Value("${upload.path}")
    private String uploadPath;

    /** Field productRepo for operations with DB. */
    private final ProductRepository productRepo;

    /**
     * Constructor - creating a new object
     * @param repo - instance of ProductRepository
     * @see ProductService
     */
    @Autowired
    public ProductService(ProductRepository repo){
        this.productRepo = repo;

    }

    /**
     * Returns a Product object that can then be used.
     * <p>
     * This method save our Product object in DataBase.And set a date field in this object.
     *
     * @param  product the object of Product class
     * @return      new Product
     * @see         Product
     */
    public Product save(Product product){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        product.setDate(dateFormat.format(date));
            return productRepo.save(product);
    }

    /**
     * Returns a Product object that can then be used.
     * <p>
     * This method add an image into some product.
     *
     * @param  multipartFile the object that contain image
     * @param  product the object of Product class
     * @return      existing Product
     * @see         Product
     */
    public Product addImage(MultipartFile multipartFile, Product product) {
            if(Objects.isNull(product.getImage())) {
                product.setImage(createNewImage(multipartFile));
            }else{
                File file = new File(uploadPath + "/" + product.getImage());
                file.delete();
                product.setImage(createNewImage(multipartFile));
            }
            return product;
    }

    /**
     * Returns an updated Product object that can then be used.
     *
     * @param  product the object of Product class
     * @return      updated Product
     * @see         Product
     */
    public Product update(Product product){
        return productRepo.save(product);
    }

    /**
     * We use this method to create new image if that doesn't exist.
     *
     * @param multipartFile - the object that contain image
     * @return      image name
     * @see         String
     */
    public String createNewImage(MultipartFile multipartFile){
        String fileName = UUID.randomUUID().toString() + "." + multipartFile.getOriginalFilename();
        String resultFileName = uploadPath + "/" + fileName;
        try {
             multipartFile.transferTo(new File(resultFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * We use this method to delete exist product by id in our DataBase.
     *
     * @param  id - product unique id
     */
    public void deleteProductById(Long id)
    {
        productRepo.deleteById(id);
    }

    /**
     * We use this method to find exist product by id in our DataBase.
     *
     * @param  id - product unique id
     * @see Product
     */
    public Product findProductById(Long id){
        return productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
    }

    /**
     * We use this method to get all exist products in our DataBase.
     *
     * @return  list products
     * @see List<Product>
     */
    public List<Product> getAllProduct()
    {
        return productRepo.findAll();
    }

    /**
     * We use this method to get all products that contain this keyword.
     * @param keyword - String type,help to us find every product that contain this keyword.
     * @return  list products
     * @see List<Product>
     */
    public List<Product> findByKeyword(String keyword){
        return productRepo.findByKeyword(keyword);
    }

    /**
     * We use this method to get product that contain this name.
     * @param name - String type,help to us find every product that contain this name.
     * @return  product object
     * @see Product
     */
    public Product findByName(String name){
        return productRepo.findByName(name);
    }

    /**
     * We use this method to find products that contain this status and return these products id.
     * @param status - String type,help to us find every product that contain this status.
     * @return  list id of products
     * @see List<Long>
     */
    public List<Long> findByStatus(String status){
        return productRepo.findByStatus(status);
    }

}
