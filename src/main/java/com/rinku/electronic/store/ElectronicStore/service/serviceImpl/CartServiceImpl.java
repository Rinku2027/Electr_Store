package com.rinku.electronic.store.ElectronicStore.service.serviceImpl;

import com.rinku.electronic.store.ElectronicStore.dtos.AddItemTOCartRequest;
import com.rinku.electronic.store.ElectronicStore.dtos.CartDto;
import com.rinku.electronic.store.ElectronicStore.entity.Cart;
import com.rinku.electronic.store.ElectronicStore.entity.CartItem;
import com.rinku.electronic.store.ElectronicStore.entity.Product;
import com.rinku.electronic.store.ElectronicStore.entity.User;
import com.rinku.electronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.rinku.electronic.store.ElectronicStore.repository.CartRepo;
import com.rinku.electronic.store.ElectronicStore.repository.ProductRepo;
import com.rinku.electronic.store.ElectronicStore.repository.UserRepo;
import com.rinku.electronic.store.ElectronicStore.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemTOCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCreatedAt(new Date());
        }
        List<CartItem> items = cart.getItems();
        CartItem.builder().quantity(quantity).totalprice(quantity+product.getPrice()).
                cart(cart).product(product).build();
        cart.getItems().add(cartItem);

    }

    @Override
    public void removeItemfromCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
