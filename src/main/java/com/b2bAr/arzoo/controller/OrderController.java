package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.request.OrderRequest;
import com.b2bAr.arzoo.response.ResultResponse;
import com.b2bAr.arzoo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {

    public final OrderService orderService;


    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/{userId}/{productId}")
    public ResponseEntity<?> order(@RequestBody OrderRequest orderRequest, @PathVariable int userId, @PathVariable int productId) {
        return orderService.order(orderRequest, userId, productId);
    }
    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest,
                                        @PathVariable int userId,
                                        @RequestParam List<Integer> productId,
                                        @RequestParam List<Integer> quantity) {
        // Ensure the number of product IDs and quantities match
        if (productId.size() != quantity.size()) {
            ResultResponse response = new ResultResponse();
            response.setResult("Number of product IDs does not match the number of quantities.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Create a map of product IDs to quantities
        Map<Integer, Integer> productQuantities = new HashMap<>();
        for (int i = 0; i < productId.size(); i++) {
            productQuantities.put(productId.get(i), quantity.get(i));
        }

        // Call the service method to place the order
        return orderService.orders(orderRequest, userId, productQuantities);
    }


    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable int orderId){
        return orderService.getOrdersById(orderId);
    }

    @GetMapping("/getAllOrder")
    public ResponseEntity<?> getOrderById(){
        return orderService.getAllOrders();
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<?> delete(@PathVariable int orderId){
        return orderService.deleteById(orderId);
    }

    @PutMapping("/edit/{orderId}")
    public ResponseEntity<?> editDetails(@RequestBody OrderRequest orderRequest, @PathVariable int orderId){
        return orderService.editOrderDetails(orderRequest, orderId );
    }

}
