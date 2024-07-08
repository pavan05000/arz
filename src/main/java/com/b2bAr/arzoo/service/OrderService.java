package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.*;
import com.b2bAr.arzoo.mapper.OrderMapper;
import com.b2bAr.arzoo.mapper.ProductMapper;
import com.b2bAr.arzoo.mapper.UserMapper;
import com.b2bAr.arzoo.repository.OrderRepository;
import com.b2bAr.arzoo.repository.ProductRepository;
import com.b2bAr.arzoo.repository.UserRepository;
import com.b2bAr.arzoo.request.OrderRequest;
import com.b2bAr.arzoo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Service
@Slf4j
public class OrderService {


    public final OrderMapper orderMapper;

    public final OrderRepository orderRepository;

    public final UserMapper userMapper;
    public final ProductMapper productMapper;
    public final UserRepository userRepository;

    public final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository, UserMapper userMapper, ProductMapper productMapper, UserRepository userRepository, ProductRepository productRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> order(OrderRequest orderRequest, int userId, int productId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            Optional<ProductsEntity> productsEntity = productRepository.findById(productId);
            UserEntity entity1 = userEntity.get();
            if (productsEntity.isPresent()) {

                ProductsEntity products = productsEntity.get();
                int count = orderRequest.getQuantity().get(0); // Assuming only one quantity for a single product order
                int productCount = products.getStock();

                if (productCount >= count) {
                    int newCount = productCount - count;
                    if (newCount <= 0) {
                        products.setStock(newCount);
                        products.setAvailability_status(false);
                        productRepository.save(products);

                    }
                    OrdersEntity entity = orderMapper.requestToEntity(orderRequest);

                    if (entity != null) {
                        entity.setOrderDate(new Date());
                        entity.setStatus("Order Placed");
                        entity.setUserEntity(entity1);
                        entity.setProductEntity(products);
                        entity.setQuantity(orderRequest.getQuantity());
                        orderRepository.save(entity);
                        log.info("Order Successfully");
                        ResultResponse response = new ResultResponse();
                        response.setResult("Order Successfully");
                        return ResponseEntity.ok(response);
                    } else {
                        log.error("no order");
                        ResultResponse response = new ResultResponse();
                        response.setResult("Empty");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }

                } else {
                    ResultResponse response = new ResultResponse();
                    log.error("According to the Order, The products are not available");
                    response.setResult("According to the Order, The products are not available");
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

    public ResponseEntity<?> orders(OrderRequest orderRequest, int userId, Map<Integer, Integer> productQuantities) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            List<OrdersEntity> orders = new ArrayList<>();

            for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                Optional<ProductsEntity> productsEntityOptional = productRepository.findById(productId);
                if (productsEntityOptional.isPresent()) {
                    ProductsEntity product = productsEntityOptional.get();
                    int availableStock = product.getStock();

                    if (availableStock >= quantity) {
                        int newCount = availableStock - quantity;
                        product.setStock(newCount);

                        if (newCount <= 0) {
                            product.setAvailability_status(false);
                            productRepository.save(product);
                        }

                        OrdersEntity order = orderMapper.requestToEntity(orderRequest);
                        order.setOrderDate(new Date());
                        order.setStatus("Order Placed");
                        order.setUserEntity(userEntity);
                        order.setProductEntity(product);
                        order.setQuantity(Collections.singletonList(quantity)); // Set the quantity for the product
                        orderRepository.save(order);
                        orders.add(order);
                    } else {
                        ResultResponse response = new ResultResponse();
                        log.error("Product with ID " + productId + " does not have enough stock.");
                        response.setResult("Product with ID " + productId + " does not have enough stock.");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                } else {
                    ResultResponse response = new ResultResponse();
                    log.error("Product with ID " + productId + " not found.");
                    response.setResult("Product with ID " + productId + " not found.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            }

            log.info("Orders Successfully");
            ResultResponse response = new ResultResponse();
            response.setResult("Orders Successfully");
            return ResponseEntity.ok(response);
        } else {
            log.error("User not found");
            ResultResponse response = new ResultResponse();
            response.setResult("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    public ResponseEntity<?> getOrdersById(int orderId) {
        Optional<OrdersEntity> entity = orderRepository.findById(orderId);

        if (entity.isPresent()) {
            OrdersEntity ordersEntity = entity.get();

//            UserEntity entity1 = entity.get().getUserEntity();
//            UserResponse userResponse = userMapper.entityToResponse(entity1);

            ProductsEntity productsEntity = entity.get().getProductEntity();
            ProductResponse2 productResponse2 = productMapper.entityToResponse3(productsEntity);
            OrderResponse orderResponse = orderMapper.entityToResponse(ordersEntity);

            String url = productsEntity.getImage_url();
            if (!url.isEmpty() && url != null) {
                try {
                    byte[] bytes = Files.readAllBytes(Paths.get(url));
                    String base = Base64.getEncoder().encodeToString(bytes);
                    productResponse2.setImage_url(base);
                } catch (Exception e) {
                    e.printStackTrace();
                    ResultResponse response = new ResultResponse();
                    response.setResult(e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            }
            orderResponse.setProductEntity(productResponse2);
            return ResponseEntity.ok(orderResponse);
        } else {
            log.error("Zero Orders");
            ResultResponse response = new ResultResponse();
            response.setResult("Zero Orders");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    public ResponseEntity<?> getAllOrders() {
        List<OrdersEntity> entityList = orderRepository.findAll();
        try {
            if (!entityList.isEmpty()) {
                List<OrderResponse> orderResponses = new ArrayList<>();
                for (OrdersEntity entity : entityList) {
                    OrderResponse orderResponse = orderMapper.entityToResponse(entity);
                    ProductsEntity entities = entity.getProductEntity();
                    ProductResponse2 productResponse2 = productMapper.entityToResponse3(entities);
                    String url = entities.getImage_url();
                    if (!url.isEmpty() && url != null) {
                        try {
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

                        orderResponse.setProductEntity(productResponse2);
                    }
                    orderResponses.add(orderResponse);
                }

                // Counting the number of orders
                int orderCount = orderResponses.size();

                // Creating a response object containing order count and list of orders
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("orderCount", orderCount);
                responseMap.put("orders", orderResponses);

                return ResponseEntity.ok(responseMap);

            } else {
                log.error("Zero Orders");
                ResultResponse response = new ResultResponse();
                response.setResult("No Orders");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> deleteById(int orderId) {
        Optional<OrdersEntity> entity = orderRepository.findById(orderId);// here we are searching the details by id
        try {

            if (entity.isPresent()) {// if the details are present
                OrdersEntity ordersEntity = entity.get();
                ProductsEntity productsEntity = ordersEntity.getProductEntity();
                int count = productsEntity.getStock();
                int orderCount = ordersEntity.getQuantity().get(productsEntity.getProductId());
                count += orderCount;

                productsEntity.setStock(count);
                productsEntity.setAvailability_status(true);
                productRepository.save(productsEntity);

                orderRepository.deleteById(orderId);
                log.info("Deleted Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted Successfully");
                return ResponseEntity.ok(response);
            } else {
                log.error("Please check Id " + orderId);
                ResultResponse response = new ResultResponse();
                response.setResult("Please check id " + orderId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    public ResponseEntity<?> editOrderDetails(OrderRequest orderRequest, int orderId) {
        try {
            Optional<OrdersEntity> entityOptional = orderRepository.findById(orderId);
            if (entityOptional.isPresent()) {
                OrdersEntity existingDetails = entityOptional.get();
                int productId = existingDetails.getProductEntity().getProductId();
                Optional<ProductsEntity> productOptional = productRepository.findById(productId);
                if (productOptional.isPresent()) {
                    ProductsEntity productEntity = productOptional.get();
                    int count = orderRequest.getQuantity().get(0); // Assuming only one quantity for the edited product
                    int productCount = productEntity.getStock();
                    if (productCount >= count) {
                        int newCount = productCount - count;
                        productEntity.setStock(newCount);
                        productRepository.save(productEntity);

                        // Update the order count
                        existingDetails.getQuantity().set(0, count);

                        OrdersEntity updatedEntity = orderMapper.updateRequest(orderRequest, existingDetails);
                        orderRepository.save(updatedEntity);

                        log.info("Updated Successfully");
                        ResultResponse response = new ResultResponse();
                        response.setResult("Updated successfully");
                        return ResponseEntity.ok(response);
                    } else {
                        log.error("There is not enough stock for the product with ID " + productId);
                        ResultResponse response = new ResultResponse();
                        response.setResult("There is not enough stock for the product with ID " + productId);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                } else {
                    log.error("Product with ID " + productId + " not found.");
                    ResultResponse response = new ResultResponse();
                    response.setResult("Product with ID " + productId + " not found.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                log.error("Order with ID " + orderId + " not found.");
                ResultResponse response = new ResultResponse();
                response.setResult("Order with ID " + orderId + " not found.");
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
