package com.onseotestapp.service;

import com.onseotestapp.domain.Product;
import com.onseotestapp.dao.ProductRepository;
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

@Service
public class ProductService {
    @Value("${upload.path}")
    private String uploadPath;

    private final ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository repo){
        this.productRepo = repo;

    }

    public Product save(Product product){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        product.setDate(dateFormat.format(date));
            return productRepo.save(product);
    }
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


    public Product update(Product product){
        return productRepo.save(product);
    }
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

    public void deleteProductById(Long id)
    {
        productRepo.deleteById(id);
    }

    public Product findProductById(Long id){
        return productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    public List<Product> getAllProduct()
    {
        return productRepo.findAll();
    }

    public List<Product> findByKeyword(String keyword){
        return productRepo.findByKeyword(keyword);
    }

    public Product findByName(String name){
        return productRepo.findByName(name);
    }

    public List<Long> findByStatus(String status){
        return productRepo.findByStatus(status);
    }

}
