package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.request.CartRequest;
import com.b2bAr.arzoo.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class CartController {

    public final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/cart/{userId}/{productId}")
    public ResponseEntity<?> cart(@RequestBody CartRequest cartRequest, @PathVariable int userId, @PathVariable int productId) {
        return cartService.cart(cartRequest, userId, productId);
    }
    @GetMapping("/getCart/{cartId}")
    public ResponseEntity<?> getCartById(@PathVariable int cartId){
        return cartService.getCartById(cartId);
    }

    @GetMapping("/getAllCart")
    public ResponseEntity<?> getCart(){
        return cartService.getAllCart();
    }

    @DeleteMapping("/deleteCart/{cartId}")
    public ResponseEntity<?> delete(@PathVariable int cartId){
        return cartService.deleteById(cartId);
    }

    @PutMapping("/edit/{cartId}")
    public ResponseEntity<?> editDetails(@RequestBody CartRequest cartRequest, @PathVariable int cartId){
        return cartService.editDetails(cartRequest, cartId );
    }

}


