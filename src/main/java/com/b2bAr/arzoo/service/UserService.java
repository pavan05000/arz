package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.CartEntity;
import com.b2bAr.arzoo.entity.OrdersEntity;
import com.b2bAr.arzoo.entity.ProductsEntity;
import com.b2bAr.arzoo.entity.UserEntity;
import com.b2bAr.arzoo.mapper.CartMapper;
import com.b2bAr.arzoo.mapper.OrderMapper;
import com.b2bAr.arzoo.mapper.ProductMapper;
import com.b2bAr.arzoo.mapper.UserMapper;
import com.b2bAr.arzoo.repository.UserRepository;
import com.b2bAr.arzoo.request.CartRequest;
import com.b2bAr.arzoo.request.UserRequest;
import com.b2bAr.arzoo.request.UserUpdateRequest;
import com.b2bAr.arzoo.response.*;
import jakarta.persistence.criteria.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class UserService {

    public final UserRepository userRepository;

    public final UserMapper userMapper;


    public  final OrderMapper orderMapper;

    public final ProductMapper productMapper;

    public final CartMapper cartMapper;


    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, OrderMapper orderMapper, ProductMapper productMapper, CartMapper cartMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.cartMapper = cartMapper;
    }

    public ResponseEntity<?> register(UserRequest userRequest) {
        Optional<UserEntity> userEntity = userRepository.findemail(userRequest.getEmail());
        if (userEntity.isEmpty()) {
            Optional<UserEntity> userEntity1 = userRepository.findUserName(userRequest.getUserName());
            if (userEntity1.isEmpty()) {
                try {
                    userRequest.setRegisterDate(new java.util.Date());
                    UserEntity entity = userMapper.requestToEntity(userRequest);

                    userRepository.save(entity);

                    log.info("Registration is done");
                    ResultResponse response = new ResultResponse();
                    response.setResult("Registration is done");
                    return ResponseEntity.ok(response);

                } catch (Exception e) {
                    log.error(e.getMessage());
                    ResultResponse response = new ResultResponse();
                    response.setResult(e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            }else {
                log.error("The userName is already Exist, give another Name " +userRequest.getUserName());
                ResultResponse response = new ResultResponse();
                response.setResult("The userName is already Exist, give another Name "  +userRequest.getUserName());
                return ResponseEntity.ok(response);
            }
        }else {
            log.error("The email id is already Exist  "+userRequest.getUserName());
            ResultResponse response = new ResultResponse();
            response.setResult("The email id is already Exist  "+userRequest.getUserName());
            return ResponseEntity.ok(response);
        }
    }
    public ResponseEntity<?> login(UserRequest userRequest){
        Optional<UserEntity> entity = userRepository.findemail(userRequest.getEmail());

        if (entity.isPresent()) {
            UserEntity entity1 = entity.get();
            if (entity1.getPassword().equals(userRequest.getPassword())) {
                entity1.setLoginDate(new Date());
                entity1.setActiveStatus(true);
                entity1.setLogOut(null);
                userRepository.save(entity1);
                log.info("Login Successfully");
                ResultLoginResponse response = new ResultLoginResponse();
                response.setResult("Login Successfully" );
                response.setUserId(entity1.getUserId());
                response.setUserName(entity1.getUserName());
                System.out.println(entity1.getUserName());

                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                log.error("Wrong password "+userRequest.getPassword());
                ResultResponse response = new ResultResponse();
                response.setResult("Wrong password");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }else {
            log.error("invalid emailId "+userRequest.getEmail());
            ResultResponse response = new ResultResponse();
            response.setResult("invalid emailId");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    public ResponseEntity<?> logOut(int userId){
        Optional<UserEntity> entity = userRepository.findById(userId);

        if (entity.isPresent()) {
            UserEntity entity1 = entity.get();
            entity1.setLogOut(new Date());

            entity1.setActiveStatus(false);
            userRepository.save(entity1);
            log.info("logOut Successfully");
            ResultResponse response = new ResultResponse();
            response.setResult("logOut Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            log.error("wrong id "+userId);
            ResultResponse response = new ResultResponse();
            response.setResult("wrong id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }


    public ResponseEntity<?> getDetailsById(int userId) {
        try {
            Optional<UserEntity> entity = userRepository.findById(userId);
            if (entity.isPresent()) {
                UserEntity userEntity1 = entity.get();
                UserResponse userResponse = userMapper.entityToResponse(userEntity1);
                List<OrdersEntity> ordersEntity = userEntity1.getOrdersEntity();
                List<OrderResponse2> orderResponses = new ArrayList<>();
                for (OrdersEntity ordersEntity1 : ordersEntity){
                    OrderResponse2 orderResponse = orderMapper.entityToResponse3(ordersEntity1);
                    ProductsEntity productsEntity = ordersEntity1.getProductEntity();
                    ProductResponse2 productResponse2 = productMapper.entityToResponse3(productsEntity);
                    String url = productsEntity.getImage_url();
                    System.out.println(url);
                    if (url != null && !url.isEmpty()) {
                        try {
                            byte[] fileContent = Files.readAllBytes(Paths.get(url));
                            if (fileContent.length > 0) {
                                String base = Base64.getEncoder().encodeToString(fileContent);
                                log.info("Image successfully converted to Base64");
                                productResponse2.setImage_url(base);
                                System.out.println(base);
                            } else {
                                log.error("Image file is empty or cannot be read: " + url);
                            }
                        } catch (IOException e) {
                            log.error("Error reading image file: " + e.getMessage());
                            ResultResponse response = new ResultResponse();
                            response.setResult(e.getMessage());
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                        }
                    } else {
                        log.error("Image URL is null or empty");
                    }

                    orderResponse.setProductEntity(productResponse2);
                    orderResponses.add(orderResponse);
                }
                // Set the list of order responses to user response
                userResponse.setOrdersEntity(orderResponses);
                return ResponseEntity.ok(userResponse);
            } else {
                log.error("User is not exist");
                ResultResponse response = new ResultResponse();
                response.setResult("User is not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    public ResponseEntity<?> getAll() {
        List<UserEntity> entity = userRepository.findAll();
        if (!entity.isEmpty()) {
            List<UserResponse> userResponse = userMapper.entityToResponse2(entity);
            int userCount = userResponse.size();
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("userCount", userCount);
            responseMap.put("users", userResponse);

            return ResponseEntity.ok(responseMap);
        } else {
            log.error("There is no records");
            ResultResponse response = new ResultResponse();
            response.setResult("There is no records");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    public ResponseEntity<?> getCartWithUser(int userId) {
        try {
            Optional<UserEntity> entity = userRepository.findById(userId);
            if (entity.isPresent()) {
                UserEntity userEntity1 = entity.get();
                UserResponse3 userResponse = userMapper.entityToResponse3(userEntity1);
                List<CartEntity> cartEntities = userEntity1.getCartEntity();
                List<CartResponse> cartResponses = new ArrayList<>();
                for (CartEntity cartEntity : cartEntities){
                    CartResponse cartResponse = cartMapper.entityToResponse(cartEntity);
                    ProductsEntity productsEntity = cartEntity.getProductsEntity();
                    ProductResponse2 productResponse2 = productMapper.entityToResponse3(productsEntity);
                    String url = productsEntity.getImage_url();
                    System.out.println(url);
                    if (url != null && !url.isEmpty()) {
                        try {
                            byte[] fileContent = Files.readAllBytes(Paths.get(url));
                            if (fileContent.length > 0) {
                                String base = Base64.getEncoder().encodeToString(fileContent);
                                log.info("Image successfully converted to Base64");
                                productResponse2.setImage_url(base);
                                System.out.println(base);
                            } else {
                                log.error("Image file is empty or cannot be read: " + url);
                            }
                        } catch (IOException e) {
                            log.error("Error reading image file: " + e.getMessage());
                            ResultResponse response = new ResultResponse();
                            response.setResult(e.getMessage());
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                        }
                    } else {
                        log.error("Image URL is null or empty");
                    }

                    cartResponse.setProductsEntity(productResponse2);
                    cartResponses.add(cartResponse);
                }
                // Set the list of order responses to user response
                userResponse.setCartEntity(cartResponses);
                return ResponseEntity.ok(userResponse);
            } else {
                log.error("User is not exist");
                ResultResponse response = new ResultResponse();
                response.setResult("User is not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> deleteById(int userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);// here we are searching the details by id
        try {

            if (userEntity.isPresent()) {// if the details are present
                userRepository.deleteById(userId);
                log.info("Deleted");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted");
                return ResponseEntity.ok(response);
            }else {
                log.error("check id " +userId);
                ResultResponse response = new ResultResponse();
                response.setResult("check id "+userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    public ResponseEntity<?> editDetails(int userId, UserUpdateRequest userUpdateRequest) {
        try {
            Optional<UserEntity> userEntity = userRepository.findById(userId);// we are Searching  the details by userId

            if (userEntity.isPresent()) {
                UserEntity existingDetails = userEntity.get();// we are get them by using get() from userEntity
                UserEntity updateEntity = userMapper.updateRequest(existingDetails, userUpdateRequest);// we are giving the values through the userUpdateRequestclass

                userRepository.save(updateEntity);// here we are saving those values
                log.info("Updated details saved successfully" + userId);
                ResultResponse response = new ResultResponse();// this is response class
                response.setResult("Updated details saved successfully" + userId);// this is the response for make sure that the details are updated are not
                return ResponseEntity.ok(response);// we are returning the response
            }else {
                ResultResponse response = new ResultResponse();
                response.setResult("Details are not Updated check the Id");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("details not saved");
    }
}
