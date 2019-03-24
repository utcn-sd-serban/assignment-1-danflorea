package ro.utcn.danf.a1.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.danf.a1.model.SiteUser;
import ro.utcn.danf.a1.persistence.api.RepositoryFactory;
import ro.utcn.danf.a1.persistence.api.UserRepository;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserSeed implements CommandLineRunner {

    private final RepositoryFactory factory;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        UserRepository repository = factory.createUserRepository();
        if (repository.findAll().isEmpty())
        {
            repository.save(new SiteUser(1, "u1","p1", "A@example.com",false, "yes"));
            repository.save(new SiteUser(2, "u2","p2", "B@example.com",false, "yes"));
            repository.save(new SiteUser(3, "u3","p3", "C@example.com",false, "yes"));
        }
    }

}
