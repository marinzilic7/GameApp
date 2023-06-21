package ba.sum.fpmoz.restoran.controller;
import ba.sum.fpmoz.restoran.model.Cart;
import ba.sum.fpmoz.restoran.model.Game;
import ba.sum.fpmoz.restoran.model.UserDetails;
import ba.sum.fpmoz.restoran.repositories.CartRepository;
import ba.sum.fpmoz.restoran.repositories.GameRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ba.sum.fpmoz.restoran.services.CartService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class GameController {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;






    @GetMapping("/games")
    public String showGames (Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        Long userId = userDetails.getUserId(); // ili koristite metodu kojom dobavljate ID korisnika
        model.addAttribute("userId", userId);
        model.addAttribute("user", user);
        model.addAttribute("game", new Game());
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("added", false);
        model.addAttribute("activeLink", "Igre");
        List<Cart> carts = cartService.getAllCartsByUserId(userId);
        int cartCount = carts.size();

        if(carts.size() > 0){
            model.addAttribute("cartCount", cartCount);
            model.addAttribute("prikazi", true);
        }else{
            model.addAttribute("prikazi", false);
        }

        return "games";
    }

    @PostMapping("/game/add")
    public String addGame (@Valid Game game, BindingResult result, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            model.addAttribute("game", game);
            model.addAttribute("games", gameRepository.findAll());
            model.addAttribute("added", true);
            model.addAttribute("activeLink", "Igre");
            return "games";
        }
        gameRepository.save(game);
        return "redirect:/games";
    }

    @GetMapping("/game/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Game game = gameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("game", game);
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("activeLink", "Kategorije");
        return "game_edit";
    }

    @PostMapping("game/edit/{id}")
    public String editCategory (@PathVariable("id") Long id, @Valid Game game, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("game", game);
            model.addAttribute("activeLink", "Igre");
            return "game_edit";
        }
        gameRepository.save(game);
        return "redirect:/games";
    }

    public boolean postojiIgra(Long gameId) {
        return cartRepository.findByGameId(gameId) != null;
    }
    @GetMapping("/game/delete/{id}")
    public String deleteGame(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        if (postojiIgra(id)) {
            redirectAttributes.addFlashAttribute("noDelete", true);
            redirectAttributes.addFlashAttribute("deleteNo", "Ne mozes izbrisati igru ako je dodana u kosaricu! ");

        }else{
            Game game = gameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
            gameRepository.delete(game);
        }

        return "redirect:/games";
    }

}
