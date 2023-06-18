package ba.sum.fpmoz.restoran.services;

import ba.sum.fpmoz.restoran.model.Category;
import ba.sum.fpmoz.restoran.model.User;
import ba.sum.fpmoz.restoran.repositories.UserRepository;
import org.springframework.stereotype.Service;
import ba.sum.fpmoz.restoran.repositories.CartRepository;
import ba.sum.fpmoz.restoran.model.Cart;
import ba.sum.fpmoz.restoran.repositories.CategoryRepository;


@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public void dodajIgruUKosaricu(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Igra nije pronađena"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCategory(category);

        // Dodatne operacije

        cartRepository.save(cart);
    }

    // Dodatne metode ako su potrebne
}