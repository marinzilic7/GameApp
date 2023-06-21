package ba.sum.fpmoz.restoran.repositories;

import ba.sum.fpmoz.restoran.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository <Game, Long> {}