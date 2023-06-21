package ba.sum.fpmoz.restoran.services;

import ba.sum.fpmoz.restoran.model.Game;
import ba.sum.fpmoz.restoran.model.User;
import ba.sum.fpmoz.restoran.repositories.UserRepository;
import org.springframework.stereotype.Service;
import ba.sum.fpmoz.restoran.repositories.CartRepository;
import ba.sum.fpmoz.restoran.model.Cart;
import ba.sum.fpmoz.restoran.repositories.GameRepository;

import java.util.List;


@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }



    public void dodajIgruUKosaricu(Long userId, Long gameId, String gameName, String body, String cijena) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Igra nije pronađena"));


        Cart cart = new Cart();
        cart.setUser(user);
        cart.setGame(game);
        cart.setGameName(gameName);
        cart.setBody(body);
        cart.setPrice(cijena);
        cartRepository.save(cart);
    }

    public List<Cart> getAllCartsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }



}