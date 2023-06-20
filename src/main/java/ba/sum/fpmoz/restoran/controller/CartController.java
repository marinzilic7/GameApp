package ba.sum.fpmoz.restoran.controller;

import ba.sum.fpmoz.restoran.model.Category;
import ba.sum.fpmoz.restoran.model.User;
import ba.sum.fpmoz.restoran.repositories.CartRepository;
import ba.sum.fpmoz.restoran.repositories.CategoryRepository;
import ba.sum.fpmoz.restoran.repositories.UserRepository;
import ba.sum.fpmoz.restoran.services.CartService;
import ba.sum.fpmoz.restoran.services.UserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ba.sum.fpmoz.restoran.model.Cart;
import ba.sum.fpmoz.restoran.model.UserDetails;


import java.security.Principal;
import java.util.List;

@Controller

public class CartController {
    private final CartService cartService;
    private final CategoryRepository categoryRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    public CartController(CartService cartService,CategoryRepository categoryRepository,UserRepository userRepository,CartRepository cartRepository,UserDetailsService userDetailsService ) {
        this.cartService = cartService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.userDetailsService = userDetailsService;


    }

    @GetMapping("/category/add_cart/{userId}/{categoryId}")
    public String addToCart(@PathVariable Long userId, @PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        String Name = category.getName();
        String Body = category.getOpis();
        String Cijena = category.getCijena();
        cartService.dodajIgruUKosaricu(userId, categoryId,Name,Body,Cijena);
        return "redirect:/categories";
    }

//    @GetMapping("/cart")
//    public String getCartPage(Model model) {
//        List<Cart> carts = cartService.getCards();
//        model.addAttribute("cards", carts);
//        return "cart";
//    }

    @GetMapping("/cart")
    public String showCart(Model model, Principal principal) {
        String email = principal.getName(); // Email trenutno prijavljenog korisnika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        // Dohvati UserDetails korisnika preko userDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        Long userId = userDetails.getUser().getId();

        List<Cart> carts = cartService.getAllCartsByUserId(userId);
//        int cartCount = carts.size();
        for (Cart cart : carts) {
            System.out.println("Cart ID: " + cart.getGameName());
            // Ispis ostalih atributa kartice
        }
//        model.addAttribute("cartCount", cartCount);
        model.addAttribute("carts", carts);
        model.addAttribute("user", user);
        return "cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteCart(@PathVariable("id") Long id, Model model) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        cartRepository.delete(cart);
        return "redirect:/categories";
    }

}

