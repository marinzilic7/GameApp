package ba.sum.fpmoz.restoran.repositories;

import ba.sum.fpmoz.restoran.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {}