package game.shop.services;

import game.shop.model.Game;
import game.shop.model.User;
import game.shop.repositories.UserRepository;
import game.shop.repositories.CartRepository;
import org.springframework.stereotype.Service;
import game.shop.model.Cart;
import game.shop.repositories.GameRepository;

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