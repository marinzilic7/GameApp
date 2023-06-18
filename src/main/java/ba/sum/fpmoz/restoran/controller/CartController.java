package ba.sum.fpmoz.restoran.controller;

import ba.sum.fpmoz.restoran.model.Category;
import ba.sum.fpmoz.restoran.model.User;
import ba.sum.fpmoz.restoran.repositories.CategoryRepository;
import ba.sum.fpmoz.restoran.repositories.UserRepository;
import ba.sum.fpmoz.restoran.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
    private final CartService cartService;
    private final CategoryRepository categoryRepository;
    public CartController(CartService cartService,CategoryRepository categoryRepository) {
        this.cartService = cartService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category/add_cart/{userId}/{categoryId}")
    public String addToCart(@PathVariable Long userId, @PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        String Name = category.getName();
        String Body = category.getOpis();
        Integer Cijena = category.getCijena();
        cartService.dodajIgruUKosaricu(userId, categoryId,Name,Body,Cijena);
        return "redirect:/categories";
    }
}

