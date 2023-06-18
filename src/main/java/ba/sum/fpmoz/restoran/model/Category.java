package ba.sum.fpmoz.restoran.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotBlank(message = "Unesite naziv igre.")
    String name;

    @Column(nullable = false)
    @NotBlank(message = "Unesite opis  igre.")
    String opis;

    @Column(nullable = false)
    @NotNull(message = "Unesite cijenu  igre.")
    Integer cijena;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = true)
    Category parent;

    @OneToMany(mappedBy = "parent")
    List<Category> categories;

    @OneToMany(mappedBy = "category")
    List<Article> articles;

    @OneToMany(mappedBy = "category")
    private List<Cart> carts;


    public Category(Long id, String name, String opis, Integer cijena) {
        this.id = id;
        this.name = name;
        this.opis = opis;
        this.cijena = cijena;
    }

    public Category() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    public Integer getCijena() {
        return cijena;
    }

    public void setCijena(Integer cijena) {
        this.cijena = cijena;
    }


    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
