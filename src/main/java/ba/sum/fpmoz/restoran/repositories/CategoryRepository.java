package ba.sum.fpmoz.restoran.repositories;

import ba.sum.fpmoz.restoran.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Long> {}