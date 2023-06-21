package game.shop.controller;

import game.shop.model.Game;
import game.shop.repositories.CartRepository;
import game.shop.repositories.GameRepository;
import game.shop.repositories.UserRepository;
import game.shop.services.CartService;
import game.shop.services.UserDetailsService;
import game.shop.model.Cart;
import game.shop.model.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.security.Principal;
import java.util.List;

@Controller

public class CartController {
    private final CartService cartService;
    private final GameRepository gameRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    public CartController(CartService cartService, GameRepository gameRepository, UserRepository userRepository, CartRepository cartRepository, UserDetailsService userDetailsService ) {
        this.cartService = cartService;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.userDetailsService = userDetailsService;


    }
    public boolean postojiIgraUKosarici(Long gameId) {
        return cartRepository.findByGameId(gameId) != null;
    }
    @GetMapping("/game/add_cart/{userId}/{gameId}")
    public String addToCart(@PathVariable Long userId, @PathVariable Long gameId,RedirectAttributes redirectAttributes) {

        if (postojiIgraUKosarici(gameId)) {
          redirectAttributes.addFlashAttribute("igraDodana", true);
          redirectAttributes.addFlashAttribute("poruka", "Igra vec dodana");

        } else {
            Game game = gameRepository.findById(gameId).orElse(null);
            String Name = game.getName();
            String Body = game.getOpis();
            String Cijena = game.getCijena();
            cartService.dodajIgruUKosaricu(userId, gameId, Name, Body, Cijena);
            System.out.println("Igra je uspješno dodana u košaricu.");
            redirectAttributes.addFlashAttribute("gameAdd", true);
            redirectAttributes.addFlashAttribute("dodanaIgra", "Igra dodana u kosaricu");
        }
        return "redirect:/games";
    }


    @GetMapping("/cart")
    public String showCart(Model model, Principal principal) {
        String email = principal.getName(); // Email trenutno prijavljenog korisnika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        // Dohvati UserDetails korisnika preko userDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        Long userId = userDetails.getUser().getId();

        List<Cart> carts = cartService.getAllCartsByUserId(userId);
//
        for (Cart cart : carts) {
            System.out.println("Cart ID: " + cart.getGameName());
            // Ispis ostalih atributa kartice
        }
//
        model.addAttribute("carts", carts);
        model.addAttribute("user", user);
        int cartCount = carts.size();

        if(carts.size() > 0){
            model.addAttribute("cartCount", cartCount);
            model.addAttribute("prikazi", true);
        }else{
            model.addAttribute("prikazi", false);
        }
        return "cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteCart(@PathVariable("id") Long id, Model model) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        cartRepository.delete(cart);
        return "redirect:/cart";
    }

}

