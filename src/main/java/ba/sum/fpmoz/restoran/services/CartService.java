package ba.sum.fpmoz.restoran.services;

import ba.sum.fpmoz.restoran.model.Category;
import ba.sum.fpmoz.restoran.model.User;
import org.springframework.stereotype.Service;
import ba.sum.fpmoz.restoran.repositories.CartRepository;
import ba.sum.fpmoz.restoran.model.Cart;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void dodajIgruUKosaricu(User user, Category category) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setGame(category);

        // Dodatne operacije

        cartRepository.save(cart);
    }

    // Dodatne metode ako su potrebne
}
