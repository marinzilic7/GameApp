package ba.sum.fpmoz.restoran.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ba.sum.fpmoz.restoran.model.Cart;
import ba.sum.fpmoz.restoran.model.User;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    Cart findByGameId(Long gameId);

}

