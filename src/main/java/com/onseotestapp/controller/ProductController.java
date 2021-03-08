package com.onseotestapp.controller;

import com.onseotestapp.dao.StatusRepository;
import com.onseotestapp.domain.Product;
import com.onseotestapp.domain.Status;
import com.onseotestapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private StatusRepository statusRepository;

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService service){
        this.productService = service;
    }

    @GetMapping("/addProduct")
    public String viewAddPage(Model model){
        List<Status> statuses = statusRepository.findAll();
        model.addAttribute("product", new Product());
        model.addAttribute("statuses",statuses);
        model.addAttribute("add", true);
        return "addProduct";
    }

    @GetMapping("/product")
    public String productList(Model model,String keyword){
        List<Product> productList = productService.getAllProduct();
        model.addAttribute("product", new Product());
        model.addAttribute("allowDelete", true);
        if(keyword != null){
            model.addAttribute("productList", productService.findByKeyword(keyword));
        }else{
            model.addAttribute("productList", productList);
        }
        return "productList";
    }

    @PostMapping("/product/save")

    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("file") MultipartFile file) {
                productService.save(productService.addImage(file,product));
        return "redirect:/product";
    }

    @RequestMapping("/product/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("editProduct");
        Product product = productService.findProductById(id);
        List<Status> statuses = statusRepository.findAll();
        mav.addObject("statuses",statuses);
        mav.addObject("product", product);
        return mav;
    }

    @PostMapping(value = {"/product/edit/{id}"})
    public String updateProduct(Model model,
                                @PathVariable("id") Long id,
                                @ModelAttribute("product") Product product) {
            product.setId(id);
            productService.update(product);
            return "redirect:/product";
    }

    @GetMapping(value = {"/product/delete/{id}"})
    public String deleteProductById(@PathVariable Long id) {
            productService.deleteProductById(id);
            return "redirect:/product";
    }


}
