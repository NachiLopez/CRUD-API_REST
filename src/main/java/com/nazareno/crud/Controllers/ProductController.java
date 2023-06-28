package com.nazareno.crud.Controllers;

import com.nazareno.crud.Models.ProductModel;
import com.nazareno.crud.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductModel> getProducts(){
        return productService.getProducts();
    }

    @PostMapping
    public ResponseEntity<Object> registrarProducto(@RequestBody ProductModel productModel){
        return this.productService.newProduct(productModel);
    }

    @PutMapping
    public ResponseEntity<Object> actualizarProducto(@RequestBody ProductModel productModel){
        return this.productService.updateProduct(productModel);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> eliminarProducto(@PathVariable("id") Long id){
        return this.productService.deleteProduct(id);
    }

}
