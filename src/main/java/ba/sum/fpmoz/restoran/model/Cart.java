package ba.sum.fpmoz.restoran.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "gameName")
    private String gameName;

    @Column(name = "body")
    private String body;

    @Column(name = "price")
    private String price;
    @ManyToOne
    @JoinColumn(name="cart_id", nullable = true)
    Cart parent;



    @OneToMany(mappedBy = "parent")
    List<Cart> carts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPrice(String price) {
        this.price= price;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }


}
