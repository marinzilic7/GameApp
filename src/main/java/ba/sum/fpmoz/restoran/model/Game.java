package ba.sum.fpmoz.restoran.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name="games")
public class Game {
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
    @NotBlank(message = "Unesite cijenu  igre.")
    String cijena;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = true)
    Game parent;

    @OneToMany(mappedBy = "parent")
    List<Game> games;


    @OneToMany(mappedBy = "game")
    private List<Cart> carts;


    public Game(Long id, String name, String opis, String cijena) {
        this.id = id;
        this.name = name;
        this.opis = opis;
        this.cijena = cijena;
    }

    public Game() {
    }

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

    public String getCijena() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena = cijena;
    }


    public Game getParent() {
        return parent;
    }

    public void setParent(Game parent) {
        this.parent = parent;
    }

    public List<Game> getCategories() {
        return games;
    }

    public void setCategories(List<Game> categories) {
        this.games = categories;
    }
}
