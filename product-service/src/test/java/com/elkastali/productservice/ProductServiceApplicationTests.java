package com.elkastali.productservice;

import com.elkastali.productservice.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @LocalServerPort
    private int port;

    private String baseUrl="http://localhost";

    @Autowired
    private TestH2Repository h2Repository;

    static private RestTemplate restTemplate;


    @BeforeAll
    public static void init(){
        restTemplate=new RestTemplate();
    }


    @BeforeEach
    public void setUp(){
        baseUrl=baseUrl.concat(":").concat(port+"").concat("/products");
    }

    @Test
    void contextLoads() {
    }


    @Test
    public void testAddProduct(){
        Product product=Product.builder()
                .name("headset")
                .quantity(2)
                .price(79999)
                .build();

        Product response=restTemplate.postForObject(baseUrl,product,Product.class);
        assertEquals("headset",response.getName());
        assertEquals(1,h2Repository.findAll().size());
    }


    @Test
    @Sql(statements = "INSERT INTO Product (id,name,quantity,price) VALUES(6,'AC',1,34000)",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Product WHERE name='AC'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetProducts(){

        List<Product>products= restTemplate.getForObject(baseUrl, List.class);
        assertEquals(1,products.size());
        assertEquals(1,h2Repository.findAll().size());


    }


    @Test
    @Sql(statements = "INSERT INTO Product (id,name,quantity,price) VALUES(6,'CAR',1,334000)",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Product WHERE name='CAR'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindProductById(){

     Product product=   restTemplate.getForObject(baseUrl+"/{id}",Product.class,6);
        assertAll(
                () -> assertNotNull(product),
                () -> assertEquals(6,product.getId()),
                () -> assertEquals("CAR",product.getName())
        );

    }


    @Test
    @Sql(statements = "INSERT INTO Product (id,name,quantity,price) VALUES(2,'shoes',1,999)",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Product WHERE name='shoes'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    public void testUpdateProduct(){

        Product product=Product.builder()
                .name("shoes")
                .quantity(2)
                .price(79999)
                .build();

         restTemplate.put(baseUrl+"/update/{id}",product,2);
        Product productFromDb= h2Repository.findById(2L).get();


        assertAll(
                () -> assertNotNull(productFromDb),
                () -> assertEquals(79999,product.getPrice())

        );
    }
    @Test
    @Sql(statements = "INSERT INTO Product (id,name,quantity,price) VALUES(1,'shoes',1,999)",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteProduct(){
        int recordsCount=h2Repository.findAll().size();
        assertEquals(1,recordsCount);
        restTemplate.delete(baseUrl+"/delete/{id}",1);
        assertEquals(0,h2Repository.findAll().size());



    }

}
