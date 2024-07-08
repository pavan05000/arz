package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.CartEntity;
import com.b2bAr.arzoo.entity.ProductsEntity;
import com.b2bAr.arzoo.entity.UserEntity;
import com.b2bAr.arzoo.mapper.CartMapper;
import com.b2bAr.arzoo.mapper.ProductMapper;
import com.b2bAr.arzoo.repository.CartRepository;
import com.b2bAr.arzoo.repository.ProductRepository;
import com.b2bAr.arzoo.repository.UserRepository;
import com.b2bAr.arzoo.request.CartRequest;
import com.b2bAr.arzoo.response.CartResponse;
import com.b2bAr.arzoo.response.ProductResponse2;
import com.b2bAr.arzoo.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    private final UserRepository userRepository;

    private  final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    public CartService(CartRepository cartRepository, CartMapper cartMapper, UserRepository userRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ResponseEntity<?> cart(CartRequest cartRequest, int userId, int productId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            Optional<ProductsEntity> productsEntity = productRepository.findById(productId);
            UserEntity entity1 = userEntity.get();
            if (productsEntity.isPresent()) {

                ProductsEntity products = productsEntity.get();
                int count = cartRequest.getCartCount();
                int productCount = products.getStock();

                if (productCount>=count) {

                    CartEntity entity = cartMapper.requestToEntity(cartRequest);

                    if (entity != null) {

                        entity.setUserEntity(entity1);
                        entity.setProductsEntity(products);
                        cartRepository.save(entity);
                        log.info("Product saved in cart");
                        ResultResponse response = new ResultResponse();
                        response.setResult("Product saved in cart");
                        return ResponseEntity.ok(response);
                    } else {
                        log.error("Empty cart");
                        ResultResponse response = new ResultResponse();
                        response.setResult("Empty");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }

                }  else{
                    ResultResponse response = new ResultResponse();
                    log.error("According to the quantity, The products are not available");
                    response.setResult("According to the quantity, The products are not available");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            } else {
                log.error("there is no product According to the productId " + productId);
                ResultResponse response = new ResultResponse();
                response.setResult("There is no product According to the productId");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } else {
            log.error("User is not exist");
            ResultResponse response = new ResultResponse();
            response.setResult("User is not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    public ResponseEntity<?> getCartById( int cartId){
        Optional<CartEntity> entity = cartRepository.findById(cartId);

        if (entity.isPresent()){
            CartEntity cartEntity = entity.get();

            ProductsEntity productsEntity = entity.get().getProductsEntity();
            ProductResponse2 productResponse2 = productMapper.entityToResponse3(productsEntity);
            CartResponse cartResponse = cartMapper.entityToResponse(cartEntity);

            String url = productsEntity.getImage_url();
            if (!url.isEmpty() && url != null){
                try {
                    byte[] bytes = Files.readAllBytes(Paths.get(url));
                    String base = Base64.getEncoder().encodeToString(bytes);
                    productResponse2.setImage_url(base);
                }catch (Exception e){
                    e.printStackTrace();
                    ResultResponse response = new ResultResponse();
                    response.setResult(e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            }
            cartResponse.setProductsEntity(productResponse2);
            return ResponseEntity.ok(cartResponse);
        }else {
            log.error("Empty cart");
            ResultResponse response = new ResultResponse();
            response.setResult("Empty cart");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    public ResponseEntity<?> getAllCart() {
        List<CartEntity> entityList = cartRepository.findAll();
        try {
            if (!entityList.isEmpty()) {
                List<CartResponse> cartResponse = new ArrayList<>();
                for (CartEntity entity : entityList) {
                    CartResponse cartResponse1 = cartMapper.entityToResponse(entity);
                    ProductsEntity entities = entity.getProductsEntity();
                    ProductResponse2 productResponse2 = productMapper.entityToResponse3(entities);
                    String url = entities.getImage_url();
                    if (!url.isEmpty() && url != null) {
                        try {
                            File file = new File(url);
                            byte[] fileContent = Files.readAllBytes(Paths.get(url));
                            String base = Base64.getEncoder().encodeToString(fileContent);
                            productResponse2.setImage_url(base);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            e.printStackTrace();
                            ResultResponse response = new ResultResponse();
                            response.setResult(e.getMessage());
                            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                        }
                        cartResponse1.setProductsEntity(productResponse2);
                    }
                    cartResponse.add(cartResponse1);
                }

                // Get the count of carts
                int cartCount = entityList.size();

                // Create a map to hold both count and cart details
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("cartCount", cartCount);
                responseMap.put("cartDetails", cartResponse);

                return ResponseEntity.ok(responseMap);
            } else {
                log.error("Empty cart");
                ResultResponse response = new ResultResponse();
                response.setResult("Empty cart");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    public ResponseEntity<?> deleteById(int cartId) {
        Optional<CartEntity> entity = cartRepository.findById(cartId);// here we are searching the details by id
        try {

            if (entity.isPresent()) {// if the details are present
                cartRepository.deleteById(cartId);
                log.info("Deleted Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted Successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Please check Id "+cartId);
                ResultResponse response = new ResultResponse();
                response.setResult("Please check id "+cartId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    public ResponseEntity<?> editDetails(CartRequest cartRequest,int cartId) {
        try {
            Optional<CartEntity> entity = cartRepository.findById(cartId);
            if (entity.isPresent()) {
                CartEntity existingDetails = entity.get();
                int productId = existingDetails.getProductsEntity().getProductId();
                Optional<ProductsEntity> entity1 = productRepository.findById(productId);
                ProductsEntity entities = entity1.get();
                int count = cartRequest.getCartCount();
                int productCount = entities.getStock();
                if (productCount >= count) {


                    CartEntity updateEntity = cartMapper.updateRequest(cartRequest, existingDetails);

                    cartRepository.save(updateEntity);
                    log.info("Updated Successfully");
                    ResultResponse response = new ResultResponse();
                    response.setResult("Updated successfully");
                    return ResponseEntity.ok(response);
                } else {
                    log.error("there is no product According to the productId " + productId);
                    ResultResponse response = new ResultResponse();
                    response.setResult("There is no product According to the productId");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }

            }else {
                log.error("Update is failed, Check Id "+cartId);
                ResultResponse response = new ResultResponse();
                response.setResult("Update is failed, Check Id "+cartId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

