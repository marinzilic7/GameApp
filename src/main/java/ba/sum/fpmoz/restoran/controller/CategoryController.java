package ba.sum.fpmoz.restoran.controller;
import ba.sum.fpmoz.restoran.model.Cart;
import ba.sum.fpmoz.restoran.model.User;
import ba.sum.fpmoz.restoran.repositories.UserRepository;
import ba.sum.fpmoz.restoran.model.Category;
import ba.sum.fpmoz.restoran.model.UserDetails;
import ba.sum.fpmoz.restoran.repositories.CartRepository;
import ba.sum.fpmoz.restoran.repositories.CategoryRepository;
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

import java.security.Principal;
import java.util.List;


@Controller
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;






    @GetMapping("/categories")
    public String showCategories (Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        Long userId = userDetails.getUserId(); // ili koristite metodu kojom dobavljate ID korisnika
        model.addAttribute("userId", userId);
        model.addAttribute("user", user);
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("added", false);
        model.addAttribute("activeLink", "Kategorije");
        List<Cart> carts = cartService.getAllCartsByUserId(userId);
        int cartCount = carts.size();

        if(carts.size() > 0){
            model.addAttribute("cartCount", cartCount);
        }

        return "categories";
    }

    @PostMapping("/category/add")
    public String addCategory (@Valid Category category, BindingResult result, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            model.addAttribute("category", category);
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("added", true);
            model.addAttribute("activeLink", "Kategorije");
            return "categories";
        }
        categoryRepo.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/category/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Category category = categoryRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("activeLink", "Kategorije");
        return "category_edit";
    }

    @PostMapping("category/edit/{id}")
    public String editCategory (@PathVariable("id") Long id, @Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", category);
            model.addAttribute("activeLink", "Kategorije");
            return "category_edit";
        }
        categoryRepo.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, Model model) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        categoryRepo.delete(category);
        return "redirect:/categories";
    }

//    @PostMapping("/add_cart/add/{id}")
//    public String AddCart(@PathVariable("id") Long id, Model model,Principal principal) {
//        Category category = categoryRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
//        User user = userRepository.findByUsername(principal.getName())
//                .orElseThrow(() -> new IllegalArgumentException("Pogrešno korisničko ime"));
//        model.addAttribute("category", category);;
//
//        Cart cart = new Cart();
//        cart.setCategory(category);
//        cart.setUser(user);
//
//        // Spremanje objekta Cart u bazu podataka
//        cartRepo.save(cart);
//
//        return "redirect:/categories";
//    }
}
