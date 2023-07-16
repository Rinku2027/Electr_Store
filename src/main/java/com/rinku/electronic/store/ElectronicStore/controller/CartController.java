package com.rinku.electronic.store.ElectronicStore.controller;

import com.rinku.electronic.store.ElectronicStore.dtos.AddItemTOCartRequest;
import com.rinku.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.rinku.electronic.store.ElectronicStore.dtos.CartDto;
import com.rinku.electronic.store.ElectronicStore.helper.ApiResponse;
import com.rinku.electronic.store.ElectronicStore.service.CartService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
private CartService cartService;

    //add items to cart

    /**
     * @author Rinku Patil
     * @param request
     * @param userId
     * @return
     */
    @PostMapping("/{userId}")
     public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemTOCartRequest request, @PathVariable String userId)
     {
         CartDto cartDto= cartService.addItemToCart(userId,request);
         return new ResponseEntity<>(cartDto, HttpStatus.OK);
     }

    /**
     * @author Rinku Patil
     * @param userId
     * @param itemId
     * @return
     */
    @DeleteMapping("/{userId}/items/{itemId}")
     public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId,@PathVariable int itemId){
        cartService.removeItemfromCart(userId,itemId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Item is removed !!").success(true).status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
     }

    /**
     * @author Rinku Patil
     * @param userId
     * @param itemId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId,@PathVariable int itemId){
        cartService.clearCart(userId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("cart is not blank !!").success(true).status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    /**
     * @author Rinku Patil
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId)
    {
        CartDto cartDto= cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


}
