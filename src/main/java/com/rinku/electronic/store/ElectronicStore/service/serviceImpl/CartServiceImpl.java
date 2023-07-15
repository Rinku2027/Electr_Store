package com.rinku.electronic.store.ElectronicStore.service.serviceImpl;

import com.rinku.electronic.store.ElectronicStore.dtos.AddItemTOCartRequest;
import com.rinku.electronic.store.ElectronicStore.dtos.CartDto;
import com.rinku.electronic.store.ElectronicStore.entity.Cart;
import com.rinku.electronic.store.ElectronicStore.entity.CartItem;
import com.rinku.electronic.store.ElectronicStore.entity.Product;
import com.rinku.electronic.store.ElectronicStore.entity.User;
import com.rinku.electronic.store.ElectronicStore.exception.BadApiRequestException;
import com.rinku.electronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.rinku.electronic.store.ElectronicStore.repository.*;
import com.rinku.electronic.store.ElectronicStore.repository.UserRepo;
import com.rinku.electronic.store.ElectronicStore.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public CartDto addItemToCart(String userId, AddItemTOCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if(quantity<=0)
        {
            throw new BadApiRequestException("Requested quantity is not found");
        }
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }
        AtomicReference<Boolean> updated=new AtomicReference<>(false) ;
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems=items.stream().map(item-> {

            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalprice(quantity + product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);

        if(!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalprice(quantity + product.getPrice()).
                     cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updatedcart = cartRepository.save(cart);
        return mapper.map(updatedcart, CartDto.class);
    }

   @Override
    public void removeItemfromCart(String userId, int cartItem) {
       CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found"));
        cartItemRepository.delete(cartItem1);

   }

    @Override
    public void clearCart(String userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
    public CartDto getCartByUser(String userId)
    {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!"));
        return mapper.map(cart,CartDto.class);
    }
}

