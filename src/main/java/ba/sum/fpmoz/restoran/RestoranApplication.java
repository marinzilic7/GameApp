package ba.sum.fpmoz.restoran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class RestoranApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestoranApplication.class, args);
    }

}