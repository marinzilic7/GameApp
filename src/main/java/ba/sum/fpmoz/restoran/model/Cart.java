package ba.sum.fpmoz.restoran.model;

import jakarta.persistence.*;

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
    @Column(name = "game_name")
    private String gameName;

    @Column(name = "body")
    private String body;

    @Column(name = "price")
    private Integer price;
    public void setUser(User user) {
        this.user = user;
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

    public void setPrice(Integer price) {
        this.price= price;
    }
}
