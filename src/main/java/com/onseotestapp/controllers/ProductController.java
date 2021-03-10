package com.onseotestapp.controllers;

import com.onseotestapp.repositories.StatusRepository;
import com.onseotestapp.entities.Product;
import com.onseotestapp.entities.Status;
import com.onseotestapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

/**
 * Class product controller api with properties <b>uploadPath</b>,<b>productService</b>.
 *
 * @author Yehor Kachur
 * @version 1.0
 */
@Controller
public class ProductController {

    /** Field uploadPath for path to the folder, where we can save an images. */
    @Value("${upload.path}")
    private String uploadPath;

    /** Field statusRepository an instance that allows us to use certain database operations. */
    private final StatusRepository statusRepository;

    /** Field productServer that realize some logic of our application. */
    private final ProductService productService;

    /**
     * Constructor - creating a new object of our controller that contain Product service instance and Status repository.
     * @param service - instance of ProductService
     * @param statusRepository - instance of StatusRepository
     * @see ProductService,StatusRepository
     */
    @Autowired
    public ProductController(ProductService service, StatusRepository statusRepository){
        this.productService = service;
        this.statusRepository = statusRepository;
    }

    /**
     * We use this method to open and show AddProduct page.
     *
     * @param  model - model of page settings
     * @return url of addProduct.html
     * @see String
     */
    @GetMapping("/addProduct")
    public String viewAddPage(Model model){
        List<Status> statuses = statusRepository.findAll();
        model.addAttribute("product", new Product());
        model.addAttribute("statuses",statuses);
        model.addAttribute("add", true);
        return "addProduct";
    }

    /**
     * We use this method to open and show productList page.
     *
     * @param  model - model of page settings
     * @param  keyword - keyword for site search
     * @return url of ProductList.html
     * @see String
     */
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

    /**
     * We use this method to save product and see our product in ProductList.
     *
     * @param  product - instance of Product class
     * @param  file - contain image
     * @return url of ProductList.html
     * @see String
     */
    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("file") MultipartFile file) {
                productService.save(productService.addImage(file,product));
        return "redirect:/product";
    }

    /**
     * We use this method to show editProduct.html
     *
     * @param  id - id of Product object
     * @return model and view instance
     * @see ModelAndView
     */
    @RequestMapping("/product/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("editProduct");
        Product product = productService.findProductById(id);
        List<Status> statuses = statusRepository.findAll();
        mav.addObject("statuses",statuses);
        mav.addObject("product", product);
        return mav;
    }

    /**
     * We use this method to edit our product and save after edited.
     *
     * @param  id - id of Product object
     * @param  product - instance of Product class
     * @return url of ProductList.html
     * @see String
     */
    @PostMapping(value = {"/product/edit/{id}"})
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute("product") Product product) {
            product.setId(id);
            productService.update(product);
            return "redirect:/product";
    }

    /**
     * We use this method to delete our product object by ID.
     *
     * @param  id - id of Product object
     * @return url of ProductList.html
     * @see String
     */
    @GetMapping(value = {"/product/delete/{id}"})
    public String deleteProductById(@PathVariable Long id) {
            productService.deleteProductById(id);
            return "redirect:/product";
    }

}
