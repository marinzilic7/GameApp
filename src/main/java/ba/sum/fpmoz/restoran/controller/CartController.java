package ba.sum.fpmoz.restoran.controller;

import ba.sum.fpmoz.restoran.model.Category;
import ba.sum.fpmoz.restoran.model.User;
import ba.sum.fpmoz.restoran.repositories.CategoryRepository;
import ba.sum.fpmoz.restoran.repositories.UserRepository;
import ba.sum.fpmoz.restoran.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public CartController(CartService cartService,UserRepository userRepository,CategoryRepository categoryRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }



    @PostMapping("/add_cart/add/{id}")
    public ResponseEntity<String> dodajIgruUKosaricu(@RequestParam("userId") Long userId, @RequestParam("categoryId") Long gameId) {
        // Dohvaćanje korisnika i igre prema ID-u iz baze podataka

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));
        Category category = categoryRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Igra nije pronađena"));

        // Dodavanje igre u košaricu

        cartService.dodajIgruUKosaricu(user, category);

        return ResponseEntity.ok("Igra je dodana u košaricu.");
    }
}
