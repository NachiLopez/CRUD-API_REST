package com.nazareno.crud.Services;

import com.nazareno.crud.Models.ProductModel;
import com.nazareno.crud.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    HashMap<String, Object> data;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    public List<ProductModel> getProducts(){

        return productRepository.findAll();

    }

    public ResponseEntity<Object> newProduct(ProductModel productModel) {
        Optional<ProductModel> res = productRepository.findProductById(productModel.getId());
        data = new HashMap<>();

        if(res.isPresent() && productModel.getId() == null){
            data.put("error", true);
            data.put("message", "Ya existe este producto");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
        data.put("data productModel", productModel);
        data.put("message", "Se registro el producto con exito");
        productRepository.save(productModel);
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateProduct(ProductModel productModel){
        data = new HashMap<>();
        if(productModel.getId()!=null && productRepository.findProductById(productModel.getId()).isPresent()){
            data.put("Data productModel", productModel);
            data.put("message", "Se actualizo con exito");
            productRepository.save(productModel);
            return new ResponseEntity(
                    data,
                    HttpStatus.ACCEPTED
            );
        }
        data.put("message", "Producto no encontrado");
        if(productModel.getId()==null){
            data.put("message", "Producto no encontrado, indique un ID");
        }
        return new ResponseEntity<>(
                data,
                HttpStatus.CONFLICT
        );
    }

    public ResponseEntity<Object> deleteProduct(Long id){
        data = new HashMap<>();
        boolean existe = productRepository.existsById(id);
        if(existe){
            productRepository.deleteById(id);
            data.put("message", "Producto eliminado");
            return new ResponseEntity(
                    data,
                    HttpStatus.ACCEPTED
            );
        }
        data.put("message", "Producto no encontrado");
        return new ResponseEntity<>(
                data,
                HttpStatus.CONFLICT
        );
    }
}
