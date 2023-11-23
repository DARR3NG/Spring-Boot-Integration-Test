package com.elkastali.productservice.controller;


import com.elkastali.productservice.entity.Product;
import com.elkastali.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProdcutController {


    private ProductService productService;


    @PostMapping
    public Product create(@RequestBody Product product){
        return productService.create(product);
    }


    @PutMapping("update/{id}")
    public Product update(@PathVariable Long id,@RequestBody Product product){
        Product p=findById(id);
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setQuantity(product.getQuantity());

        return productService.update(p);
    }


    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id){
        return productService.findByid(id);
    }


    @GetMapping
    public List<Product> findAll(){
        return productService.findAll();
    }

    @DeleteMapping("delete/{id}")
    public Boolean delete(@PathVariable Long id){
        return productService.delete(id);
    }
}
