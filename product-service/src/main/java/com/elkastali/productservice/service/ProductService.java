package com.elkastali.productservice.service;


import com.elkastali.productservice.entity.Product;
import com.elkastali.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;



    public Product create(Product product){
        return productRepository.save(product);

    }


    public Product update(Product product){
        return productRepository.save(product);
    }


    public Product findByid(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Boolean delete(Long id){
        try{
            productRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
