package ba.sum.fpmoz.restoran.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ba.sum.fpmoz.restoran.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Dodatne metode za dohvat, ažuriranje ili brisanje podataka iz tablice Cart
}

