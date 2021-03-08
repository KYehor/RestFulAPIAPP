package com.onseotestapp.controller;

import com.onseotestapp.domain.Product;
import com.onseotestapp.domain.Status;
import com.onseotestapp.dto.ProductDTO;
import com.onseotestapp.service.ProductService;
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

@RestController
@RequestMapping("/api")
public class ProductApiController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ProductService productService;

    @Autowired
    public ProductApiController(ProductService service) {
        this.productService = service;
    }

    @GetMapping("/product/find/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

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

    @DeleteMapping("/product/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
    }

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
    @PutMapping(value ="/status/change/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> changeStatus(@PathVariable("id") Long id,@RequestBody @Valid Status status){
        Product product = productService.findProductById(id);
        Status oldStatus = product.getStatus();
        product.setStatus(status);
        Status newStatus = status;
        productService.update(product);
        ProductDTO productDTO = new ProductDTO(product.getId(),status,oldStatus);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.CREATED);
    }

    @GetMapping("/status/{status}")
    public List<Long> findByStatusAllId(@PathVariable String status){
        return productService.findByStatus(status);
    }
}
