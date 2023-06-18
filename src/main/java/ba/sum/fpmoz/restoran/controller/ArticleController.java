package ba.sum.fpmoz.restoran.controller;

import ba.sum.fpmoz.restoran.model.Article;
import ba.sum.fpmoz.restoran.model.Category;
import ba.sum.fpmoz.restoran.model.UserDetails;
import ba.sum.fpmoz.restoran.repositories.ArticleRepository;
import ba.sum.fpmoz.restoran.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class ArticleController {

    @Autowired
    ArticleRepository articleRepo;

    @Autowired
    CategoryRepository categoryRepo;

    private static String UPLOADED_FOLDER = "./uploads/";


    @GetMapping("/articles")
    public String showArticles (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("article", new Article());
        model.addAttribute("articles", articleRepo.findAll());
        model.addAttribute("added", false);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("activeLink", "Proizvodi");
        return "articles";
    }

    @GetMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id, Model model) throws IOException {
        Article article = articleRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        articleRepo.delete(article);
        Files.delete(Path.of(article.getImage()));
        return "redirect:/articles";
    }

    @GetMapping("/article/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Article article = articleRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("activeLink", "Proizvodi");
        return "article_edit";
    }

    @PostMapping("/article/edit/{id}")
    public String editArticle (@PathVariable("id") Long id, @Valid Article article, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            result.addError(new FieldError("article", "image", "Molimo odaberite sliku."));
        } else if (!file.getContentType().startsWith("image/")) {
            result.addError(new FieldError("article", "image", "Molimo slika nije ispravnog formata."));
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                article.setImage(path.toString());
            } catch (IOException e) {
                result.addError(new FieldError("article", "image", "Problem s učitavanjem slike na poslužitelj."));
            }
        }

        if (result.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = (UserDetails) auth.getPrincipal();
            model.addAttribute("user", user);
            model.addAttribute("article", article);
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("activeLink", "Proizvodi");
            return "article_edit";
        }
        articleRepo.save(article);
        return "redirect:/articles";
    }


    @PostMapping("/article/add")
    public String addArticle (@Valid Article article, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("activeLink", "Proizvodi");

        if (file.isEmpty()) {
            result.addError(new FieldError("article", "image", "Molimo odaberite sliku."));
        } else if (!file.getContentType().startsWith("image/")) {
            result.addError(new FieldError("article", "image", "Molimo slika nije ispravnog formata."));
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                article.setImage(path.toString());
            } catch (IOException e) {
                result.addError(new FieldError("article", "image", "Problem s učitavanjem slike na poslužitelj."));
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("added", true);
            model.addAttribute("articles", articleRepo.findAll());
            model.addAttribute("categories", categoryRepo.findAll());
            return "articles";
        }
        articleRepo.save(article);
        return "redirect:/articles";
    }
}
